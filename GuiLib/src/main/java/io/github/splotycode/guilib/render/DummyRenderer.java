package io.github.splotycode.guilib.render;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DummyRenderer extends Renderer {

    public static final DummyRenderer INSTANCE = new DummyRenderer();

    @Override
    public float width() {
        return 0;
    }

    @Override
    public float height() {
        return 0;
    }

    @Override
    public void preRender() {

    }
}
