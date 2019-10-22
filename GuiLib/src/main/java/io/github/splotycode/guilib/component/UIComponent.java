package io.github.splotycode.guilib.component;

import io.github.splotycode.guilib.layout.Constrains;
import io.github.splotycode.guilib.render.ConstrainsRenderer;
import io.github.splotycode.guilib.render.Renderer;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL11.*;

@Getter
public class UIComponent {

    static final ConstrainsRenderer STATIC_RENDERER = new ConstrainsRenderer();

    private float width;
    private float height;
    private HashMap<UIComponent, Constrains> childs = new HashMap<>();

    public void add(UIComponent component, Constrains constrains) {
        if (!constrains.allowDirect()) {
            throw new IllegalStateException(constrains.getClass().getSimpleName() + " can not be used as DirectConstrains");
        }
        childs.put(component, constrains);
    }

    public void render(UIMaster master, Constrains constrains) {
        for (Map.Entry<UIComponent, Constrains> entry : childs.entrySet()) {
            UIComponent child = entry.getKey();
            Constrains cons = entry.getValue();

            cons.calculate(child, master, constrains);
            child.resize(cons.width(), cons.height());

            double right = master.windowWidth() - cons.x();
            double bottom = master.windowHeight() - cons.y();
            glMatrixMode(GL_MODELVIEW);
            glPushMatrix();
            glLoadIdentity();
            glOrtho(-cons.x(), right, bottom, -cons.y(), 1, -1);
            child.renderSelf(STATIC_RENDERER.setConstrains(cons));
            glPopMatrix();
            child.render(master, cons);
        }
    }

    public void resize(float width, float height) {
        this.width = width;
        this.height = height;
    }

    protected void renderSelf(Renderer renderer) {}

}
