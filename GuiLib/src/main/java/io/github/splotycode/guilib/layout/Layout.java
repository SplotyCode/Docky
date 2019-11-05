package io.github.splotycode.guilib.layout;

import io.github.splotycode.guilib.component.UIComponent;
import io.github.splotycode.guilib.render.RenderContext;

public interface Layout {

    default void add(UIComponent component) {}
    default void remove(UIComponent component) {}

    void preCalculate(RenderContext ctx);

    void calculate(Constrains constrains, RenderContext ctx);

}
