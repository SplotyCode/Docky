package io.github.splotycode.guilib.layout;

import io.github.splotycode.guilib.component.UIComponent;
import io.github.splotycode.guilib.component.UIMaster;
import io.github.splotycode.guilib.render.RenderContext;

public interface Constrains {

    void calculate(UIComponent component, RenderContext ctx);

    float x();
    float y();

    float width();
    float height();

    boolean allowMixed();
    boolean allowDirect();

    boolean calculatesPosition();
    boolean calculatesSize();

    default void setRawPosition(float x, float y) {
        throw new UnsupportedOperationException();
    }

}
