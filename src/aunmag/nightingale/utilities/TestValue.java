package aunmag.nightingale.utilities;

import aunmag.nightingale.Input;
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
        if (Input.isKeyPressed(keyIncrease)) {
            current += step;
        }

        if (Input.isKeyPressed(keyDecrease)) {
            current -= step;
        }

        if (Input.isKeyPressed(keySetMin)) {
            current = min;
        }

        if (Input.isKeyPressed(keySetMax)) {
            current = max;
        }

        correct();

        if (Input.isKeyPressed(keySwitch)) {
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
