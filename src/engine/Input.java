package engine;

import java.nio.DoubleBuffer;
import java.util.Arrays;

import org.joml.Vector2f;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;

public class Input {

    private static long window;
    private static Vector2f mousePosition;
    private static Vector2f mouseVelocity = new Vector2f(0, 0);
    private static boolean[] keys = new boolean[GLFW.GLFW_KEY_LAST];

    static {
        Arrays.fill(keys, false);
    }

    static void update() {
        for (int i = 0; i < keys.length; i++) {
            keys[i] = getIsKeyDown(i);
        }

        updateMouse();
    }

    private static void updateMouse() {
        // TODO: Learn more:
        DoubleBuffer xBuffer = BufferUtils.createDoubleBuffer(1);
        DoubleBuffer yBuffer = BufferUtils.createDoubleBuffer(1);
        GLFW.glfwGetCursorPos(window, xBuffer, yBuffer);
        xBuffer.rewind();
        yBuffer.rewind();
        float newX = (float) xBuffer.get();
        float newY = (float) yBuffer.get();

        if (mousePosition == null) {
            mousePosition = new Vector2f(newX, newY);
        } else {
            mouseVelocity.x = mousePosition.x - newX;
            mouseVelocity.y = mousePosition.y - newY;
            mousePosition.x = newX;
            mousePosition.y = newY;
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

    /* Getters */

    public static Vector2f getMouseVelocity() {
        return new Vector2f(mouseVelocity);
    }

}
