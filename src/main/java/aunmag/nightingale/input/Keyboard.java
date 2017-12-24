package aunmag.nightingale.input;

import aunmag.nightingale.Application;
import org.lwjgl.glfw.GLFW;

import java.util.Arrays;

public final class Keyboard {

    private final static boolean[] keys = new boolean[GLFW.GLFW_KEY_LAST];

    Keyboard() {
        Arrays.fill(keys, false);
    }

    final void update() {
        for (int key = 0; key < keys.length; key++) {
            keys[key] = isKeyDown(key);
        }
    }

    /* Getters */

    public final boolean isKeyDown(int key) {
        return Input.isAvailable()
                && GLFW.glfwGetKey(Application.getWindow().id, key) == GLFW.GLFW_TRUE;
    }

    public final boolean isKeyPressed(int key) {
        return isKeyDown(key) && !keys[key];
    }

    public final boolean isKeyReleased(int key) {
        return !isKeyDown(key) && keys[key];
    }

}
