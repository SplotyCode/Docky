package io.github.splotycode.guilib.layout;

import io.github.splotycode.guilib.render.RenderContext;

public class VAlignLayout implements Layout {

    private float x;
    private float currentY;

    @Override
    public void preCalculate(RenderContext ctx) {
        x = ctx.getConstrains().x();
        currentY = ctx.getConstrains().y();
    }

    @Override
    public void calculate(Constrains constrains, RenderContext ctx) {
        constrains.setRawPosition(x, currentY);
        currentY += constrains.height();
    }
}
