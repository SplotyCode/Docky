package io.github.splotycode.guilib.layout.relativ;

import io.github.splotycode.guilib.component.UIComponent;
import io.github.splotycode.guilib.component.UIMaster;
import io.github.splotycode.guilib.layout.AbstractConstrains;
import io.github.splotycode.guilib.layout.Constrains;
import io.github.splotycode.guilib.render.RenderContext;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RelativeConstrains extends AbstractConstrains {

    public static RelativeConstrains position(float relativeX, float relativeY) {
        return new RelativeConstrains(relativeX, relativeY, 0, 0);
    }

    public static RelativeConstrains size(float relativeWidth, float relativeHeight) {
        return new RelativeConstrains(0, 0, relativeWidth, relativeHeight);
    }

    private float relativeX;
    private float relativeY;
    private float relativeWidth;
    private float relativeHeight;

    @Override
    public void calculate(UIComponent component, RenderContext ctx) {
        Constrains parent = ctx.getParentConstrains();
        x = parent.x() + relativeX;
        y = parent.y() + relativeY;
        relativeWidth = parent.width() + relativeWidth;
        relativeHeight = parent.height() + relativeHeight;
    }
}
