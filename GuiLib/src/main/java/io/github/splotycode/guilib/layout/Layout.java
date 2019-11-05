package io.github.splotycode.guilib.layout;

import io.github.splotycode.guilib.component.UIComponent;
import io.github.splotycode.guilib.render.RenderContext;

public interface Layout {

    void add(UIComponent component);
    void remove(UIComponent component);

    void calculate(Constrains constrains, RenderContext ctx);

}
