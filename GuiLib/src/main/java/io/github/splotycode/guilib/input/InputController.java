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

    private double mouseX, mouseY;
    private Window window;
    private HashMap<Integer, PressData> inputs = new HashMap<>();

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
        try (MemoryStack stack = stackPush()) {
            DoubleBuffer x = stack.mallocDouble(1);
            DoubleBuffer y = stack.mallocDouble(1);
            GLFW.glfwGetCursorPos(window.getWindowID(), x, y);
            mouseX = x.get(0);
            mouseY = y.get(0);
        }

        /*System.out.println("start");
        for (Map.Entry<Integer, KeyData> key : window.getInputController().getKeys().entrySet()) {
            System.out.println(key.getKey() + " " + key.getValue().getState() + " " +  key.getValue().getPressedTicks() + " " + key.getValue().getRepressed());
        }
        System.out.println("end");*/
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
