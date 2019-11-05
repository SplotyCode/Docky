package io.github.splotycode.guilib.control;

import io.github.splotycode.guilib.component.UIComponent;
import io.github.splotycode.guilib.render.RenderContext;
import io.github.splotycode.guilib.render.Renderer;
import lombok.Getter;
import lombok.Setter;

public class HorizontalSlider extends UIComponent {

    public HorizontalSlider(float position, float height, float yStart, int sliderColor){

        this.position = position;
        this.height = height;
        this.yStart = yStart;
        this.sliderColor = sliderColor;
    }

    @Getter @Setter
    private float position;
    @Getter @Setter
    private float percentage;
    private float height;
    private float yStart;
    private int sliderColor;


    @Override
    protected void renderSelf(RenderContext ctx, Renderer renderer) {
        super.renderSelf(ctx, renderer);
        renderer.drawRect(position,yStart,1,height,sliderColor);
        float newPosition = getPercentage() * renderer.width();
    }

    @Override
    protected void updateSelf(RenderContext ctx) {
        if(ctx.lockClick()) {
            percentage = ctx.relativeMouseX() / ctx.width();
        }
        super.updateSelf(ctx);
    }
}
