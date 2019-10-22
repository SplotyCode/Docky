package io.github.splotycode.guilib.layout;

import io.github.splotycode.guilib.component.UIComponent;
import io.github.splotycode.guilib.component.UIMaster;

public class MixedConstrains extends AbstractConstrains {

    protected Constrains x, y, width, height;

    public MixedConstrains(Constrains constrains) {
        setConstrains(constrains);
    }

    public MixedConstrains(Constrains position, Constrains size) {
        setPosition(position);
        setSize(size);
    }

    public MixedConstrains(Constrains x, Constrains y, Constrains width, Constrains height) {
        setX(x);
        setY(y);
        setWidth(width);
        setHeight(height);
    }

    @Override
    public void calculate(UIComponent component, UIMaster master, Constrains parent) {
        x.calculate(component, master, parent);
        y.calculate(component, master, parent);
        super.x = x.x();
        super.y = y.y();

        width.calculate(component, master, parent);
        height.calculate(component, master, parent);
        super.width = width.width();
        super.height = height.height();
    }

    private void checkConstrains(Constrains constrains) {
        if (!constrains.allowMixed()) {
            throw new IllegalStateException(constrains.getClass().getSimpleName() + " can not be used as MixedConstrains");
        }
    }

    public void setWidth(Constrains width) {
        checkConstrains(width);
        this.width = width;
    }

    public void setHeight(Constrains height) {
        checkConstrains(width);
        this.height = height;
    }

    public void setX(Constrains x) {
        checkConstrains(width);
        this.x = x;
    }

    public void setY(Constrains y) {
        checkConstrains(width);
        this.y = y;
    }

    public void setSize(Constrains constrains) {
        checkConstrains(constrains);
        width = height = constrains;
    }

    public void setPosition(Constrains constrains) {
        checkConstrains(constrains);
        x = y = constrains;
    }

    public void setConstrains(Constrains constrains) {
        checkConstrains(constrains);
        x = y = width = height = constrains;
    }
}
