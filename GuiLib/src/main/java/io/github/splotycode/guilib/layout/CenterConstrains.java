package io.github.splotycode.guilib.layout;

import io.github.splotycode.guilib.component.UIComponent;
import io.github.splotycode.guilib.component.UIMaster;
import io.github.splotycode.guilib.render.RenderContext;

public class CenterConstrains extends AbstractConstrains {

    @Override
    public void calculate(UIComponent component, RenderContext ctx) {
        width = master.windowWidth() / 2 - component.getWidth();
        height = master.windowHeight() / 2 - component.getHeight();
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
