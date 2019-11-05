package io.github.splotycode.guilib.input;

import io.github.splotycode.guilib.window.Window;
import lombok.Getter;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallbackI;
import org.lwjgl.system.MemoryStack;

import java.nio.DoubleBuffer;
import java.util.HashMap;

import static org.lwjgl.system.MemoryStack.stackPush;

@Getter
public class InputController implements GLFWKeyCallbackI {

    private Window window;

    private float mouseX, mouseY;
    private float lastMouseX, lastMouseY;

    private HashMap<Integer, PressData> inputs = new HashMap<>();

    private InputUser lockedMouseUser;
    private InputUser lockedKeyboardUser;
    private int releaseLockedButton = -1;

    public InputController(Window window) {
        this.window = window;
    }

    public void setup() {
        GLFW.glfwSetKeyCallback(window.getWindowID(), this);
        GLFW.glfwSetMouseButtonCallback(window.getWindowID(), (window, button, action, mods) -> InputController.this.invoke(window, button, -1, action, mods));
    }

    public void beforeUpdate() {
        for (PressData button : inputs.values()) {
            button.pressedTicks++;
            button.state = PressState.DOWN;
        }
    }

    public void update() {
        lastMouseX = mouseX;
        lastMouseY = mouseY;
        try (MemoryStack stack = stackPush()) {
            DoubleBuffer x = stack.mallocDouble(1);
            DoubleBuffer y = stack.mallocDouble(1);
            GLFW.glfwGetCursorPos(window.getWindowID(), x, y);
            mouseX = (float) x.get(0);
            mouseY = (float) y.get(0);
        }

        /*System.out.println("start");
        for (Map.Entry<Integer, KeyData> key : window.getInputController().getKeys().entrySet()) {
            System.out.println(key.getKey() + " " + key.getValue().getState() + " " +  key.getValue().getPressedTicks() + " " + key.getValue().getRepressed());
        }
        System.out.println("end");*/
        if (lockedMouseUser != null && releaseLockedButton != -1 && !inputs.containsKey(releaseLockedButton)) {
            unlockMouse(lockedMouseUser);
        }
    }

    public boolean isMouseLocked(InputUser user) {
        return lockedMouseUser != null && lockedMouseUser == user;
    }

    public boolean isKeyboardLocked(InputUser user) {
        return lockedMouseUser != null && lockedMouseUser == user;
    }

    public boolean isMouseLocked() {
        return lockedMouseUser != null;
    }

    public boolean isKeyboardLocked() {
        return lockedMouseUser != null;
    }

    public boolean tryLockMouse(InputUser user, int releaseLockedButton) {
        if (tryLockMouse(user)) {
            this.releaseLockedButton = releaseLockedButton;
            return true;
        }
        return false;
    }

    public boolean tryLockMouse(InputUser user) {
        if (lockedMouseUser == null || lockedMouseUser == user) {
            if (lockedMouseUser != user) {
                user.onLock(this);
                lockedMouseUser = user;
            }
            return true;
        }
        return false;
    }

    public boolean unlockMouse(InputUser user) {
        if (lockedMouseUser != user) {
            return false;
        }
        user.onUnlock(this);
        lockedMouseUser = null;
        releaseLockedButton = -1;
        return true;
    }

    public boolean tryLockKeyboard(InputUser user) {
        if (lockedKeyboardUser == null || lockedKeyboardUser == user) {
            if (lockedKeyboardUser != user) {
                user.onLock(this);
                lockedKeyboardUser = user;
            }
            return true;
        }
        return false;
    }

    public boolean unlockKeyboard(InputUser user) {
        if (lockedKeyboardUser != user) {
            return false;
        }
        user.onUnlock(this);
        lockedKeyboardUser = null;
        return true;
    }

    public boolean isClicked(int button) {
        return inputs.containsKey(button);
    }

    public boolean isLeftClicked() {
        return leftMouseData() != null;
    }

    public boolean isRigthClicked() {
        return rightMouseData() != null;
    }

    public PressData leftMouseData() {
        return inputs.get(GLFW.GLFW_MOUSE_BUTTON_LEFT);
    }

    public PressData rightMouseData() {
        return inputs.get(GLFW.GLFW_MOUSE_BUTTON_LEFT);
    }

    @Override
    public void invoke(long window, int key, int scancode, int action, int mods) {
        if (action == GLFW.GLFW_RELEASE) {
            inputs.remove(key);
            return;
        }
        PressData keyData = inputs.computeIfAbsent(key, PressData::new);
        keyData.mods = mods;
        if (action == GLFW.GLFW_PRESS) {
            keyData.state = PressState.PRESSED;
        } else { /* GLFW_REPEAT */
            keyData.state = PressState.REPRESSED;
            keyData.repressed++;
        }
    }

    public void resetInputs() {
        inputs.clear();
    }

}
