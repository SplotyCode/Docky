package io.github.splotycode.docky;

import io.github.splotycode.guilib.GuiEngine;
import io.github.splotycode.guilib.component.UIColor;
import io.github.splotycode.guilib.component.UILabel;
import io.github.splotycode.guilib.component.UIMaster;
import io.github.splotycode.guilib.control.Slider;
import io.github.splotycode.guilib.font.FontFile;
import io.github.splotycode.guilib.layout.relativ.RelativeScalingConstrains;
import io.github.splotycode.guilib.window.Window;
import org.lwjgl.system.Configuration;

import java.io.IOException;

public class Docky {

    private GuiEngine engine = new GuiEngine();

    public void run() throws IOException {
        Configuration.DEBUG.set(true);
        engine.initialize();
        UIMaster master = new Window(engine).createFullScreen("Docky").getMaster();
        engine.loadFont("arial", "io/github/splotycode/guilib/font/arial");

        master.add(new Slider(50, 100, UIColor.BLUE, UIColor.BLACK, 20), RelativeScalingConstrains.size(0.5f, 0.5f));
        master.add(new UILabel("$Hje{l}log!_-", "arial", UIColor.BLUE), new RelativeScalingConstrains(0.5f));
        engine.loop();
    }

    public static void main(String[] args) throws IOException {
        new Docky().run();
    }

}
