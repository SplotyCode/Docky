package io.github.splotycode.guilib.layout.relativ;

import io.github.splotycode.guilib.component.UIComponent;
import io.github.splotycode.guilib.layout.contrains.AbstractConstrains;
import io.github.splotycode.guilib.render.RenderContext;

public class ParentCenterConstrains extends AbstractConstrains {

    @Override
    public void calculate(UIComponent component, RenderContext ctx) {
        width = ctx.getParentConstrains().width() / 2 - ctx.width();
        height = ctx.getParentConstrains().height() / 2 - ctx.heigth();
    }

    @Override
    public boolean allowDirect() {
        return false;
    }

    @Override
    public boolean calculatesPosition() {
        return false;
    }
}
