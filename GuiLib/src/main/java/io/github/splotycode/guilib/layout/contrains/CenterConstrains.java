package io.github.splotycode.guilib.layout.contrains;

import io.github.splotycode.guilib.component.UIComponent;
import io.github.splotycode.guilib.render.RenderContext;

public class CenterConstrains extends AbstractConstrains {

    @Override
    public void calculate(UIComponent component, RenderContext ctx) {
        width = ctx.windowWidth() / 2 - ctx.width();
        height = ctx.windowHeigth() / 2 - ctx.heigth();
    }

    @Override
    public boolean allowDirect() {
        return false;
    }

    @Override
    public boolean calculatesSize() {
        return false;
    }

}
