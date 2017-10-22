package aunmag.nightingale.utilities;

import aunmag.nightingale.Application;
import aunmag.nightingale.Input;
import org.lwjgl.glfw.GLFW;

/**
 * This utils simply allows to store a float value and change it quickly with some rules.
 */

public class TestValue {

    public static float defaultMin = 0;
    public static float defaultMax = 1;

    public float min;
    public float max;
    public float step;
    public float current;

    public int keyIncrease = GLFW.GLFW_KEY_KP_8;
    public int keyDecrease = GLFW.GLFW_KEY_KP_2;
    public int keySetMin = GLFW.GLFW_KEY_KP_4;
    public int keySetMax = GLFW.GLFW_KEY_KP_6;
    public int keySwitch = GLFW.GLFW_KEY_KP_5;

    public TestValue() {
        this(defaultMin, defaultMax, defaultMax, defaultMin);
    }

    public TestValue(float min, float max) {
        this(min, max, max, min);
    }

    public TestValue(float min, float max, float step) {
        this(min, max, step, min);
    }

    public TestValue(float min, float max, float step, float current) {
        this.min = min;
        this.max = max;
        this.step = step;
        this.current = current;
    }

    public void update() {
        Input input = Application.getInput();

        if (input.isKeyPressed(keyIncrease)) {
            current += step;
        }

        if (input.isKeyPressed(keyDecrease)) {
            current -= step;
        }

        if (input.isKeyPressed(keySetMin)) {
            current = min;
        }

        if (input.isKeyPressed(keySetMax)) {
            current = max;
        }

        correct();

        if (input.isKeyPressed(keySwitch)) {
            float mid = (min + max) / 2f;

            if (current < mid) {
                current = max;
            } else {
                current = min;
            }
        }
    }

    public void correct() {
        if (current < min) {
            current = min;
        } else if (max < current) {
            current = max;
        }
    }

}
