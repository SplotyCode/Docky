package io.github.splotycode.guilib.layout;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class StaticConstrains implements Constrains {

    private float x, y, width, height;

    @Override
    public float x() {
        return x;
    }

    @Override
    public float y() {
        return y;
    }

    @Override
    public float width() {
        return width;
    }

    @Override
    public float height() {
        return height;
    }

}
