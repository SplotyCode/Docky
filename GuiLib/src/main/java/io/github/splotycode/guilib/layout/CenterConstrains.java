package io.github.splotycode.guilib.layout;

import io.github.splotycode.guilib.component.UIComponent;
import io.github.splotycode.guilib.component.UIMaster;

public class CenterConstrains extends AbstractConstrains {

    @Override
    public void calculate(UIComponent component, UIMaster master, Constrains parent) {
        width = master.getWidth() / 2 - component.getWidth();
        height = master.getHeight() / 2 - component.getHeight();
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
