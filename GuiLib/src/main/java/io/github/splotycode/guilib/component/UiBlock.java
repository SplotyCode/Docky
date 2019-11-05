package io.github.splotycode.guilib.component;

import io.github.splotycode.guilib.render.RenderContext;
import io.github.splotycode.guilib.render.Renderer;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UiBlock extends UIComponent {

    private int color;

    public UiBlock(int color) {
        this.color = color;
    }

    @Override
    protected void renderSelf(RenderContext ctx, Renderer renderer) {
        super.renderSelf(ctx, renderer);
        renderer.fill(color);
    }
}
