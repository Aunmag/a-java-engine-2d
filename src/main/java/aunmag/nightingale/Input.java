package aunmag.nightingale;

import java.nio.DoubleBuffer;
import java.util.Arrays;

import org.joml.Vector2f;
import org.joml.Vector2fc;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;

public class Input {

    private static boolean[] keys = new boolean[GLFW.GLFW_KEY_LAST];
    private static boolean[] mouseButtons = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];
    private static Vector2f mousePosition = new Vector2f(0f, 0f);
    private static Vector2f mouseVelocity = new Vector2f(0f, 0f);

    static {
        Arrays.fill(keys, false);
        Arrays.fill(mouseButtons, false);
    }

    static void update() {
        for (int key = 0; key < keys.length; key++) {
            keys[key] = isKeyDown(key);
        }

        for (int button = 0; button < mouseButtons.length; button++) {
            mouseButtons[button] = isMouseButtonDown(button);
        }

        updateMouse();
    }

    private static void updateMouse() {
        if (!isAvailable()) {
            mouseVelocity.set(0, 0);
            return;
        }

        DoubleBuffer xBuffer = BufferUtils.createDoubleBuffer(1);
        DoubleBuffer yBuffer = BufferUtils.createDoubleBuffer(1);
        GLFW.glfwGetCursorPos(getWindowId(), xBuffer, yBuffer);

        // TODO: Learn more:
        xBuffer.rewind();
        yBuffer.rewind();
        float x = (float) xBuffer.get();
        float y = (float) yBuffer.get();

        float xVelocity = mousePosition.x() - x;
        float yVelocity = mousePosition.y() - y;

        mouseVelocity.set(xVelocity, yVelocity);
        mousePosition.set(x, y);
    }

    /* Getters */

    private static long getWindowId() {
        return Application.getWindow().id;
    }

    public static boolean isAvailable() {
        return getWindowId() != Window.UNDEFINED_ID;
    }

    public static boolean isKeyDown(int key) {
        return isAvailable() && GLFW.glfwGetKey(getWindowId(), key) == GLFW.GLFW_TRUE;
    }

    public static boolean isKeyPressed(int key) {
        return isKeyDown(key) && !keys[key];
    }

    public static boolean isKeyReleased(int key) {
        return !isKeyDown(key) && keys[key];
    }

    public static boolean isMouseButtonDown(int button) {
        return isAvailable() && GLFW.glfwGetMouseButton(getWindowId(), button) == GLFW.GLFW_TRUE;
    }

    public static boolean isMouseButtonPressed(int button) {
        return isMouseButtonDown(button) && !mouseButtons[button];
    }

    public static boolean isMouseButtonReleased(int button) {
        return !isMouseButtonDown(button) && mouseButtons[button];
    }

    public static Vector2fc getMouseVelocity() {
        return mouseVelocity.toImmutable();
    }

    public static float getMouseX() {
        return mousePosition.x();
    }

    public static float getMouseY() {
        return mousePosition.y();
    }

}
