package io.github.splotycode.guilib.layout.relativ;

import io.github.splotycode.guilib.component.UIComponent;
import io.github.splotycode.guilib.component.UIMaster;
import io.github.splotycode.guilib.layout.AbstractConstrains;
import io.github.splotycode.guilib.layout.Constrains;
import io.github.splotycode.guilib.render.RenderContext;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RelativeScalingConstrains extends AbstractConstrains {

    public static RelativeScalingConstrains position(float scaleX, float scaleY) {
        return new RelativeScalingConstrains(scaleX, scaleY, 0, 0);
    }

    public static RelativeScalingConstrains size(float scaleWidth, float scaleHeight) {
        return new RelativeScalingConstrains(0, 0, scaleWidth, scaleHeight);
    }

    private float scaleX;
    private float scaleY;
    private float scaleWidth;
    private float scaleHeight;

    public RelativeScalingConstrains(float scaling) {
        scaleX = scaleY = scaleWidth = scaleHeight = scaling;
    }

    @Override
    public void calculate(UIComponent component, RenderContext ctx) {
        Constrains parent = ctx.getParentConstrains();
        x = parent.x() + scaleX * parent.width();
        y = parent.y() + scaleY * parent.height();
        width = scaleWidth * parent.width();
        height = scaleHeight * parent.height();
    }

    public void setPosition(float scaleX, float scaleY) {
        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }

    public void setSize(float scaleWidth, float scaleHeight) {
        this.scaleWidth = scaleWidth;
        this.scaleHeight = scaleHeight;
    }
}
