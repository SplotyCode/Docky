package io.github.splotycode.guilib.layout;

import io.github.splotycode.guilib.render.RenderContext;

public class HAlignLayout implements Layout {

    private float currentX;
    private float y;

    @Override
    public void preCalculate(RenderContext ctx) {
        currentX = ctx.getConstrains().x();
        y = ctx.getConstrains().y();
    }

    @Override
    public void calculate(Constrains constrains, RenderContext ctx) {
        constrains.setRawPosition(currentX, y);
        currentX += constrains.width();
    }
}
