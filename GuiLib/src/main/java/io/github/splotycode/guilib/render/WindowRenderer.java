package io.github.splotycode.guilib.render;

import io.github.splotycode.guilib.window.Window;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class WindowRenderer extends Renderer {

    private Window window;

    @Override
    public float width() {
        return window.getWidth();
    }

    @Override
    public float height() {
        return window.getHeight();
    }

    @Override
    public void preRender() {
        //window.useThis();
    }
}
