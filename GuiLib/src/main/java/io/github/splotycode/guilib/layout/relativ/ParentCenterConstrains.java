package io.github.splotycode.guilib.layout.relativ;

import io.github.splotycode.guilib.component.UIComponent;
import io.github.splotycode.guilib.component.UIMaster;
import io.github.splotycode.guilib.layout.AbstractConstrains;
import io.github.splotycode.guilib.layout.Constrains;

public class ParentCenterConstrains extends AbstractConstrains {

    @Override
    public void calculate(UIComponent component, UIMaster master, Constrains parent) {
        width = parent.width() / 2 - component.getWidth();
        height = parent.height() / 2 - component.getHeight();
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
