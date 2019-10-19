package io.github.splotycode.guilib.component;

import io.github.splotycode.guilib.window.Window;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UIMaster extends UIComponent {

    private Window window;

    public void render() {
        render(this, null);
        window.normalize();
    }

    public float windowWidth() {
        return window.getWidth();
    }

    public float windowHeight() {
        return window.getHeight();
    }

}
