package io.github.splotycode.guilib.component;

import io.github.splotycode.guilib.layout.Constrains;
import io.github.splotycode.guilib.layout.Layout;
import io.github.splotycode.guilib.render.ConstrainsRenderer;
import io.github.splotycode.guilib.render.RenderContext;
import io.github.splotycode.guilib.render.Renderer;
import io.github.splotycode.guilib.window.Window;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL11.*;

@Getter
public class UIComponent {

    @Setter private Layout layout;
    private HashMap<UIComponent, RenderContext> childs = new LinkedHashMap<>();

    public void add(UIComponent component, Constrains constrains) {
        if (!constrains.allowDirect()) {
            throw new IllegalStateException(constrains.getClass().getSimpleName() + " can not be used as DirectConstrains");
        }
        if (layout != null) {
            layout.add(component);
        }
        childs.put(component, new RenderContext(constrains, this, component, null));
    }

    public void render(UIMaster master) {
        for (Map.Entry<UIComponent, RenderContext> entry : childs.entrySet()) {
            UIComponent child = entry.getKey();
            RenderContext ctx = entry.getValue();
            Constrains cons = ctx.getConstrains();

            double right = master.windowWidth() - cons.x();
            double bottom = master.windowHeight() - cons.y();

            glMatrixMode(GL_MODELVIEW);
            glPushMatrix();
            glLoadIdentity();

            glOrtho(-cons.x(), right, bottom, -cons.y(), 1, -1);
            child.renderSelf(ctx, ctx.getRenderer());

            glPopMatrix();

            child.render(master);
        }
    }

    public void update(RenderContext ctx) {
        updateSelf(ctx);
        if (layout != null) {
            layout.preCalculate(ctx);
        }
        //System.out.println(getClass().getSimpleName() + " " + ctx.getConstrains());
        for (Map.Entry<UIComponent, RenderContext> entry : childs.entrySet()) {
            UIComponent child = entry.getKey();
            RenderContext childCtx = entry.getValue();

            boolean init = childCtx.getMaster() == null;
            childCtx.setMaster(ctx.getMaster());
            childCtx.setParentConstrains(ctx.getConstrains());

            Constrains cons = childCtx.getConstrains();

            cons.calculate(child, childCtx);
            if (layout != null) {
                layout.calculate(cons, childCtx);
            }
            if (init) {
                init(childCtx);
            }
            //child.resize(cons.width(), cons.height());
            child.update(childCtx);
        }
    }

    protected void renderSelf(RenderContext ctx, Renderer renderer) {}
    protected void updateSelf(RenderContext ctx) {}
    protected void init(RenderContext ctx) {}

}
