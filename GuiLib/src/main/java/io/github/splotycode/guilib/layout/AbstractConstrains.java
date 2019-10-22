package io.github.splotycode.guilib.layout;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public abstract class AbstractConstrains implements Constrains {

    protected float x, y, width, height;

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

    @Override
    public boolean allowDirect() {
        return true;
    }

    @Override
    public boolean allowMixed() {
        return true;
    }

    @Override
    public boolean calculatesPosition() {
        return true;
    }

    @Override
    public boolean calculatesSize() {
        return true;
    }
}
