package io.github.splotycode.guilib.render;

import io.github.splotycode.guilib.GuiEngine;
import io.github.splotycode.guilib.component.UIComponent;
import io.github.splotycode.guilib.component.UIMaster;
import io.github.splotycode.guilib.input.InputController;
import io.github.splotycode.guilib.input.InputUser;
import io.github.splotycode.guilib.layout.Constrains;
import io.github.splotycode.guilib.window.Window;
import lombok.Getter;
import lombok.Setter;
import org.lwjgl.glfw.GLFW;

@Getter
public class RenderContext {

    static final ConstrainsRenderer STATIC_RENDERER = new ConstrainsRenderer();

    protected UIComponent component;
    protected UIComponent parent;
    protected Constrains constrains;
    @Setter protected UIMaster master;

    protected InputUser inputUser = InputUser.create();

    public RenderContext(Constrains constrains, UIComponent parent, UIComponent component, UIMaster master) {
        this.constrains = constrains;
        this.parent = parent;
        this.component = component;
        this.master = master;
    }

    public boolean leftClicked() {
        return getInputController().isLeftClicked() && mouseOver();
    }

    public boolean mouseOver() {
        InputController controller =  getInputController();
        float mouseX = controller.getMouseX();
        float mouseY = controller.getMouseY();
        float x = constrains.x(), y = constrains.y();
        return mouseX > x && mouseY > y && mouseX < x + constrains.width() && mouseY < y + constrains.height();
    }

    /**
     * Returns true if:
     * 1) This Component is locked regardless if it is not mouseover
     * 2) The mouse button is clicked on this element and can be locked
     */
    public boolean lockClick(int mouseButton) {
        if (getInputController().isMouseLocked(inputUser)) {
            return true;
        }
        return mouseOver() && getInputController().isClicked(mouseButton) && getInputController().tryLockMouse(inputUser, mouseButton);
    }

    public boolean lockClick() {
        return lockClick(GLFW.GLFW_MOUSE_BUTTON_LEFT);
    }

    public float relativeMouseX() {
        return getInputController().getMouseX() - constrains.x();
    }

    public float relativeMouseY() {
        return getInputController().getMouseY() - constrains.y();
    }

    public InputController getInputController() {
        return getWindow().getInputController();
    }

    public ConstrainsRenderer getRenderer() {
        return STATIC_RENDERER.setConstrains(constrains);
    }

    public float getWindowAspectRatio() {
        return getWindow().acpectRatio();
    }

    public Constrains getWindowConstrains() {
        return master.getConstrains();
    }

    public Window getWindow() {
        return master.getWindow();
    }

    public GuiEngine getEngine() {
        return getWindow().getEngine();
    }

    public RenderLoader getLoader() {
        return getEngine().getLoader();
    }

    public float width() {
        return getConstrains().width();
    }

    public float heigth() {
        return getConstrains().height();
    }

}
