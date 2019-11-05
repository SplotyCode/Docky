package io.github.splotycode.guilib;

import io.github.splotycode.guilib.font.FontFile;
import io.github.splotycode.guilib.render.RenderLoader;
import io.github.splotycode.guilib.window.Window;
import lombok.Getter;
import org.lwjgl.glfw.GLFW;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

@Getter
public class GuiEngine {

    private static volatile boolean initialized;

    private RenderLoader loader;

    private HashMap<String, FontFile> fonts = new HashMap<>();

    private ArrayList<Window> windows = new ArrayList<>();

    private volatile boolean shuttingdown;
    private Thread mainThread;

    public FontFile getFont(String name) {
        return fonts.get(name.toLowerCase());
    }

    public FontFile loadFont(String name, String path) throws IOException {
        int textureAtlas = loader.loadTexture(path + ".png");
        FontFile file = new FontFile(textureAtlas, path + ".fnt");
        fonts.put(name.toLowerCase(), file);
        return file;
    }

    public void initialize() {
        if (!initialized) {
            boolean create = false;
            synchronized (GuiEngine.class) {
                if (!initialized) {
                    initialized = create = true;
                }
            }
            if (create) {
                Window.initializeGLFW();
            }
        }
        loader = new RenderLoader();
    }

    public Window firstWindow() {
        return windows.get(0);
    }

    public void shutdown() {
        shuttingdown = true;
    }

    protected void doShutdown() {
        loader.cleanUp();
        for (Window window : windows) {
            window.destroy();
        }
        Window.shutdownGLFW();
    }

    public void loop() {
        mainThread = Thread.currentThread();
        while (!shuttingdown) {
            for (Window window : windows) {
                window.useThis();
                window.fullRender();
            }
            handleInputs();
        }
        doShutdown();
    }

    protected void handleInputs() {
        windows.forEach(window -> window.getInputController().beforeUpdate());
        GLFW.glfwPollEvents();
        windows.forEach(window -> window.getInputController().update());
    }

}
