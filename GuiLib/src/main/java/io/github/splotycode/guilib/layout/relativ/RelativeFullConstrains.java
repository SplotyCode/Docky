package io.github.splotycode.guilib.layout.relativ;

import io.github.splotycode.guilib.component.UIComponent;
import io.github.splotycode.guilib.layout.Constrains;
import io.github.splotycode.guilib.layout.contrains.AbstractConstrains;
import io.github.splotycode.guilib.render.RenderContext;

public class RelativeFullConstrains extends AbstractConstrains {
    @Override
    public void calculate(UIComponent component, RenderContext ctx) {
        Constrains parent = ctx.getParentConstrains();
        x = parent.x();
        y = parent.y();
        width = parent.width();
        height = parent.height();
    }
}
