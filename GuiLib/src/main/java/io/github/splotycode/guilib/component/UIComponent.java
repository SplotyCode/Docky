package io.github.splotycode.guilib.component;

import io.github.splotycode.guilib.layout.Constrains;
import io.github.splotycode.guilib.render.ConstrainsRenderer;
import io.github.splotycode.guilib.render.Renderer;
import lombok.Getter;
import org.lwjgl.opengl.GL11;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL11.*;

@Getter
public class UIComponent {

    private static final ConstrainsRenderer staticRenderer = new ConstrainsRenderer();

    private float width;
    private float height;
    private HashMap<UIComponent, Constrains> childs = new HashMap<>();

    public void add(UIComponent component, Constrains constrains) {
        childs.put(component, constrains);
    }

    public void render(UIMaster master, Constrains constrains) {
        renderSelf(staticRenderer.setConstrains(constrains));
        for (Map.Entry<UIComponent, Constrains> entry : childs.entrySet()) {
            UIComponent child = entry.getKey();
            Constrains cons = entry.getValue();

            cons.calculate(child, master);
            child.resize(cons.width(), cons.height());

            double right = master.windowWidth() - (cons.x() + cons.width()) + master.windowWidth();
            double bottom = master.windowHeight() - (cons.y() + cons.height()) + master.windowHeight();
            glLoadIdentity();
            GL11.glOrtho(-cons.x(), right, bottom, -cons.y(), 1, -1);

            child.render(master, cons);
        }
    }

    public void resize(float width, float height) {
        this.width = width;
        this.height = height;
    }

    protected void renderSelf(Renderer renderer) {}

}
