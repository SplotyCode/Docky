package io.github.splotycode.guilib.control;

import io.github.splotycode.guilib.component.UIComponent;
import io.github.splotycode.guilib.component.UiBlock;
import io.github.splotycode.guilib.layout.Constrains;
import io.github.splotycode.guilib.layout.HAlignLayout;
import io.github.splotycode.guilib.layout.relativ.RelativeScalingConstrains;
import io.github.splotycode.guilib.render.RenderContext;
import io.github.splotycode.guilib.render.Renderer;
import io.github.splotycode.mosaik.util.math.MathUtil;
import lombok.Getter;
import lombok.Setter;

public class HorizontalSlider extends UIComponent {

    private static final float SLIDER_WIDTH = 0.05f;

    private RelativeScalingConstrains left = RelativeScalingConstrains.position(0, 1),
            rigth = RelativeScalingConstrains.position(0, 1),
            middle = RelativeScalingConstrains.position(SLIDER_WIDTH, 1);

    @Getter @Setter
    private float position;

    public HorizontalSlider(UIComponent left, UIComponent right, float position, int sliderColor) {
        setLayout(new HAlignLayout());
        this.position = position;

        add(left, this.left);
        add(new UiBlock(sliderColor), middle);
        add(right, this.rigth);

        updateConstrains();
    }

    private void updateConstrains() {
        left.setScaleWidth(position);
        rigth.setScaleWidth(1 - SLIDER_WIDTH - position);
    }


    @Override
    protected void updateSelf(RenderContext ctx) {
        if (ctx.lockClick(middle)) {
            position = MathUtil.clamp(ctx.relativeMouseX() / ctx.width() - SLIDER_WIDTH / 2, 0, 1 - SLIDER_WIDTH);
            updateConstrains();
        }
        super.updateSelf(ctx);
    }
}
