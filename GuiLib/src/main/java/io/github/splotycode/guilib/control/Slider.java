package io.github.splotycode.guilib.control;

import io.github.splotycode.guilib.component.UIComponent;
import io.github.splotycode.guilib.render.RenderContext;
import io.github.splotycode.guilib.render.Renderer;
import io.github.splotycode.mosaik.util.math.MathUtil;

public class Slider extends UIComponent {

    public Slider(double min, double max, int sliderColor, int barColor, float sliderWidth) {
        value = (max - min) / 2 + min;//TODO
        this.min = min;
        this.max = max;
        this.sliderColor = sliderColor;
        this.barColor = barColor;
        this.sliderWidth = sliderWidth;
    }

    private double value;
    private double min, max;

    private int sliderColor;
    private int barColor;

    private float sliderWidth;

    public float getPercent() {
        return (float) ((value - min) / (max - min));
    }

    @Override
    protected void renderSelf(RenderContext ctx, Renderer renderer) {
        renderer.drawRect(0, renderer.height() / 4f, renderer.width(), renderer.height() / 2f, barColor);
        float baseX = getPercent() * renderer.width();
        renderer.drawRect(baseX - sliderWidth / 2f, 0, sliderWidth, renderer.height(), sliderColor);

    }

    @Override
    protected void updateSelf(RenderContext ctx) {
        if (ctx.lockClick()) {
            value = MathUtil.clamp(ctx.relativeMouseX() / ctx.width() * (max - min) + min, min, max);
        }
    }
}
