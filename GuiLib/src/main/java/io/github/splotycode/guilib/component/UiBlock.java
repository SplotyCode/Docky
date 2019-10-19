package io.github.splotycode.guilib.component;

import io.github.splotycode.guilib.render.Renderer;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class UiBlock extends UIComponent {

    private int color;

    @Override
    protected void renderSelf(Renderer renderer) {
        super.renderSelf(renderer);
        renderer.fill(color);
    }
}
