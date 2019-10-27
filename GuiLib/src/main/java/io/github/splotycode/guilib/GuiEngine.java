package io.github.splotycode.guilib;

import io.github.splotycode.guilib.font.FontFile;
import io.github.splotycode.guilib.font.FontRenderer;
import io.github.splotycode.guilib.render.RenderLoader;
import io.github.splotycode.guilib.window.Window;
import lombok.Getter;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.HashMap;

@Getter
public class GuiEngine {

    public static final GuiEngine INSTANCE = new GuiEngine();

    private RenderLoader loader;
    private FontRenderer renderer;

    private HashMap<String, FontFile> fonts = new HashMap<>();

    public void loadFont(String name, String path) {
        //TODO

    }

    private ArrayList<Window> windows = new ArrayList<>();

    private Window createWindow0() {
        Window window = new Window();
        windows.add(window);
        return window;
    }

    public Window createWindow(String title) {
        Window window = createWindow0();
        window.createFullScreen(title);
        GLFW.glfwSetWindowCloseCallback(window.getWindowID(), windowID -> windows.remove(window));
        return window;
    }

    public Window createWindow(String title, int width, int height) {
        Window window = createWindow0();
        window.create(width, height, title);
        GLFW.glfwSetWindowCloseCallback(window.getWindowID(), windowID -> windows.remove(window));
        return window;
    }

    public void initialize() {
        Window.initializeGLFW();
    }

    public void start() {
        loader = new RenderLoader();
        renderer = new FontRenderer();
    }

    public void shutdown() {
        renderer.shutdown();
        loader.cleanUp();
        for (Window window : windows) {
            window.destroy();
        }
        Window.shutdownGLFW();
    }

}
