package io.github.splotycode.guilib.component;

import io.github.splotycode.guilib.layout.StaticConstrains;
import io.github.splotycode.guilib.window.Window;
import lombok.Getter;

import static org.lwjgl.opengl.GL11.*;

@Getter
public class UIMaster extends UIComponent {

    private Window window;
    private StaticConstrains constrains = new StaticConstrains(0, 0, 0, 0);

    public UIMaster(Window window) {
        this.window = window;
    }

    public void render() {
        resize(windowWidth(), windowHeight());
        constrains.setSize(windowWidth(), windowHeight());

        glMatrixMode(GL_PROJECTION);
        glPushMatrix();
        glLoadIdentity();

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glDisable(GL_DEPTH_TEST);

        renderSelf(UIComponent.STATIC_RENDERER.setConstrains(constrains));
        render(this, constrains);

        glDisable(GL_BLEND);
        glEnable(GL_DEPTH_TEST);

        glMatrixMode(GL_PROJECTION);
        glPopMatrix();
        glMatrixMode(GL_MODELVIEW);
    }

    public float windowWidth() {
        return window.getWidth();
    }

    public float windowHeight() {
        return window.getHeight();
    }

}
