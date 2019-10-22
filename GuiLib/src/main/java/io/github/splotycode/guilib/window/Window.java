package io.github.splotycode.guilib.window;

import io.github.splotycode.guilib.component.UIMaster;
import io.github.splotycode.guilib.input.InputController;
import io.github.splotycode.guilib.render.WindowRenderer;
import lombok.Getter;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

@Getter
public class Window {

    public static void initializeGLFW() {
        GLFWErrorCallback.createPrint(System.err).set();
        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }
    }

    public static GLFWVidMode getScreen() {
        GLFWVidMode maxSize = glfwGetVideoMode(glfwGetPrimaryMonitor());
        if (maxSize == null) {
            throw new RuntimeException("Could not get monitor size");
        }
        return maxSize;
    }

    public static void shutdownGLFW() {
        glfwTerminate();
        GLFWErrorCallback callback = glfwSetErrorCallback(null);
        if (callback != null) {
            callback.free();
        }
    }

    private WindowRenderer renderer = new WindowRenderer(this);
    private UIMaster master = new UIMaster(this);
    private InputController inputController = new InputController(this);

    private long windowID;
    private int width;
    private int height;

    public void createFullScreen(String title) {
        GLFWVidMode screen = getScreen();
        create(screen.width(), screen.height(), title);
    }

    public void create(int width, int height, String title) {
        resize0(width, height);

        windowID = glfwCreateWindow(width, height, title, NULL, NULL);
        if (windowID == NULL) {
            throw new RuntimeException("Failed to create window");
        }

        useThis();

        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR,3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR,3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

        /* Enable v-sync */
        glfwSwapInterval(1);
        glfwShowWindow(windowID);
        GL.createCapabilities();
        glfwSetWindowSizeCallback(windowID, (window, w, h) -> {
            resize0(w, h);
            normalize();
        });
        normalize();
        inputController.setup();
    }

    public void resize(int width, int height) {
        resize0(width, height);
        glfwSetWindowSize(windowID, width, height);
    }

    public void resize0(int width, int height) {
        this.width = width;
        this.height = height;
        System.out.println("Window size: " + width + "x" + height);
    }

    public void normalize() {
        glViewport(0, 0, width, height);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, width, height, 0, 1, -1);
    }

    public void fullRender() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        master.render();
        render();
    }

    public void render() {
        glfwSwapBuffers(windowID);
    }

    public boolean isClosing() {
        return glfwWindowShouldClose(windowID);
    }

    public void center() {
        try (MemoryStack stack = stackPush()) {
            IntBuffer width = stack.mallocInt(1);
            IntBuffer height = stack.mallocInt(1);
            glfwGetWindowSize(windowID, width, height);

            GLFWVidMode maxSize = getScreen();
            glfwSetWindowPos(windowID,
                    (maxSize.width() - width.get(0)) / 2,
                    (maxSize.height() - height.get(0)) / 2
            );
        }
    }

    public void fullscreen() {
        glfwSetWindowPos(windowID, 0, 0);

        GLFWVidMode maxSize = getScreen();
        glfwSetWindowSize(windowID, maxSize.width(), maxSize.height());
    }

    public void handleInput() {
        inputController.beforeUpdate();
        glfwPollEvents();
        inputController.update();
    }

    public void destroy() {
        glfwFreeCallbacks(windowID);
        glfwDestroyWindow(windowID);
    }

    public void useThis() {
        glfwMakeContextCurrent(windowID);
    }

}
