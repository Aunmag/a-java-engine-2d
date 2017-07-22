package engine;

import java.util.Arrays;

import org.lwjgl.glfw.GLFW;

public class Input {

    private static long window;
    private static boolean[] keys = new boolean[GLFW.GLFW_KEY_LAST];

    static {
        Arrays.fill(keys, false);
    }

    static void update() {
        for(int i = 0; i < keys.length; i++) {
            keys[i] = getIsKeyDown(i);
        }
    }

    /* Setter */

    static void setWindow(long window) {
        Input.window = window;
    }

    /* Getters */

    public static boolean getIsKeyDown(int key) {
        return GLFW.glfwGetKey(window, key) == GLFW.GLFW_TRUE;
    }

    public static boolean getIsKeyPressed(int key) {
        return getIsKeyDown(key) && !keys[key];
    }

    public static boolean getIsKeyReleased(int key) {
        return !getIsKeyDown(key) && keys[key];
    }

    public static boolean getIsMouseButtonDown(int button) {
        return GLFW.glfwGetMouseButton(window, button) == GLFW.GLFW_TRUE;
    }

}
