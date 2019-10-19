package io.github.splotycode.guilib.render;

import io.github.splotycode.guilib.layout.Constrains;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ConstrainsRenderer extends Renderer {

    private Constrains constrains;

    public ConstrainsRenderer setConstrains(Constrains constrains) {
        this.constrains = constrains;
        return this;
    }

    @Override
    public float width() {
        return constrains.width();
    }

    @Override
    public float height() {
        return constrains.height();
    }

    @Override
    public void preRender() {

    }
}
