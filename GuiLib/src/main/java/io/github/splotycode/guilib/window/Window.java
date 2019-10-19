package io.github.splotycode.guilib.window;

import io.github.splotycode.guilib.component.UIMaster;
import io.github.splotycode.guilib.render.WindowRenderer;
import lombok.Getter;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
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

    public static void shutdownGLFW() {
        glfwTerminate();
        GLFWErrorCallback callback = glfwSetErrorCallback(null);
        if (callback != null) {
            callback.free();
        }
    }

    private WindowRenderer renderer = new WindowRenderer(this);
    private UIMaster master = new UIMaster(this);

    private long windowID;
    private int width;
    private int height;

    public void create(int width, int height, String title) {
        this.width = width;
        this.height = height;

        glfwDefaultWindowHints();
        windowID = glfwCreateWindow(width, height, title, NULL, NULL);
        if (windowID == NULL) {
            throw new RuntimeException("Failed to create window");
        }

        useThis();
        /* Enable v-sync */
        glfwSwapInterval(1);
        glfwShowWindow(windowID);
        GL.createCapabilities();
        normalize();
    }

    public void normalize() {
        glLoadIdentity();
        GL11.glOrtho(0, width, height, 0, 1, -1);
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

            GLFWVidMode maxSize = glfwGetVideoMode(glfwGetPrimaryMonitor());
            glfwSetWindowPos(windowID,
                    (maxSize.width() - width.get(0)) / 2,
                    (maxSize.height() - height.get(0)) / 2
            );
        }
    }

    public void destroy() {
        glfwFreeCallbacks(windowID);
        glfwDestroyWindow(windowID);
    }

    public void useThis() {
        glfwMakeContextCurrent(windowID);
    }

}
