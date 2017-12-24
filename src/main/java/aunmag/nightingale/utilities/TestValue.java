package aunmag.nightingale.utilities;

import aunmag.nightingale.input.Input;
import org.lwjgl.glfw.GLFW;

/**
 * This util simply allows to store a float value and change it quickly with some rules.
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
        if (Input.keyboard.isKeyPressed(keyIncrease)) {
            current += step;
        }

        if (Input.keyboard.isKeyPressed(keyDecrease)) {
            current -= step;
        }

        if (Input.keyboard.isKeyPressed(keySetMin)) {
            current = min;
        }

        if (Input.keyboard.isKeyPressed(keySetMax)) {
            current = max;
        }

        correct();

        if (Input.keyboard.isKeyPressed(keySwitch)) {
            float mid = (min + max) / 2f;

            if (current < mid) {
                current = max;
            } else {
                current = min;
            }
        }
    }

    public void correct() {
        current = UtilsMath.limitNumber(current, min, max);
    }

}
