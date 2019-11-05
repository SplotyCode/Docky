package io.github.splotycode.guilib.layout.contrains;

import io.github.splotycode.guilib.component.UIComponent;
import io.github.splotycode.guilib.render.RenderContext;

public class StaticConstrains extends AbstractConstrains {

    public StaticConstrains(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void calculate(UIComponent component, RenderContext ctx) {}

    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
    }

}
