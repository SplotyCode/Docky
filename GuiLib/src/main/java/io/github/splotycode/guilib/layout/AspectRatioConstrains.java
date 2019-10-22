package io.github.splotycode.guilib.layout;

import io.github.splotycode.guilib.component.UIComponent;
import io.github.splotycode.guilib.component.UIMaster;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper = true)
@Getter
@Data
@AllArgsConstructor
public class AspectRatioConstrains extends AbstractConstrains {

    private float aspectRatio;

    @Override
    public void calculate(UIComponent component, UIMaster master, Constrains parent) {
        width = component.getHeight() * aspectRatio;
        height = component.getWidth() * aspectRatio;
    }

    @Override
    public boolean allowDirect() {
        return false;
    }

    @Override
    public boolean calculatesPosition() {
        return false;
    }
}
