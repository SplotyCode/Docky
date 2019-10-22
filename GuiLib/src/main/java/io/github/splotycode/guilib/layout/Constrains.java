package io.github.splotycode.guilib.layout;

import io.github.splotycode.guilib.component.UIComponent;
import io.github.splotycode.guilib.component.UIMaster;

public interface Constrains {

    void calculate(UIComponent component, UIMaster master, Constrains parent);

    float x();
    float y();

    float width();
    float height();

    boolean allowMixed();
    boolean allowDirect();

    boolean calculatesPosition();
    boolean calculatesSize();

}
