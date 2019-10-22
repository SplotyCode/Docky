package io.github.splotycode.docky;

import io.github.splotycode.guilib.GuiEngine;
import io.github.splotycode.guilib.component.UIColor;
import io.github.splotycode.guilib.component.UiBlock;
import io.github.splotycode.guilib.input.KeyData;
import io.github.splotycode.guilib.layout.relativ.RelativeScalingConstrains;
import io.github.splotycode.guilib.window.Window;
import io.github.splotycode.mosaik.util.ThreadUtil;
import org.lwjgl.system.Configuration;

import java.util.Map;

import static org.lwjgl.opengl.GL11.glClearColor;

public class Docky {

    private Window window;

    public void run() {
        Configuration.DEBUG.set(true);
        GuiEngine.INSTANCE.initialize();
        window = GuiEngine.INSTANCE.createWindow("Docky");
        GuiEngine.INSTANCE.start();
        loop();

        GuiEngine.INSTANCE.shutdown();
    }

    private void loop() {
        UiBlock block = new UiBlock(UIColor.BLACK);
        block.add(new UiBlock(UIColor.BLUE), RelativeScalingConstrains.size(0.5f, 1));
        window.getMaster().add(block, RelativeScalingConstrains.size(0.5f, 0.5f));

        glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        while (!window.isClosing()) {
            window.handleInput();
            window.fullRender();
        }
    }

    public static void main(String[] args) {
        new Docky().run();
    }

}
