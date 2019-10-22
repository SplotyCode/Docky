package io.github.splotycode.guilib.input;

import lombok.Getter;
import org.lwjgl.glfw.GLFW;

@Getter
public class PressData {

    private final int button;

    public PressData(int button) {
        this.button = button;
    }

    PressState state;
    int pressedTicks;
    int repressed;
    int mods;

    public boolean isMouse() {
        return button <= 7;
    }

    public boolean isKey() {
        return !isMouse();
    }

    public boolean isShift() {
        return (mods & GLFW.GLFW_MOD_SHIFT) != 0;
    }

    public boolean isCtrl() {
        return (mods & GLFW.GLFW_MOD_CONTROL) != 0;
    }

    public boolean isAlt() {
        return (mods & GLFW.GLFW_MOD_ALT) != 0;
    }

    /* Super or windows key */
    public boolean isSuper() {
        return (mods & GLFW.GLFW_MOD_SUPER) != 0;
    }

}
