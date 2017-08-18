package nightingale;

import java.nio.DoubleBuffer;
import java.util.Arrays;

import org.joml.Vector2f;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;

public class Input {

    private final long windowId;
    private Vector2f mousePosition;
    private Vector2f mouseVelocity = new Vector2f(0, 0);
    private boolean[] keys = new boolean[GLFW.GLFW_KEY_LAST];
    private boolean[] mouseButtons = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];

    Input(long windowId) {
        this.windowId = windowId;
        Arrays.fill(keys, false);
        Arrays.fill(mouseButtons, false);
        mousePosition = fetchMousePositionNew();
    }

    void update() {
        for (int i = 0; i < keys.length; i++) {
            keys[i] = isKeyDown(i);
        }

        for (int i = 0; i < mouseButtons.length; i++) {
            mouseButtons[i] = isMouseButtonDown(i);
        }

        updateMouse();
    }

    private void updateMouse() {
        Vector2f mousePositionNew = fetchMousePositionNew();
        mouseVelocity.x = mousePosition.x - mousePositionNew.x;
        mouseVelocity.y = mousePosition.y - mousePositionNew.y;
        mousePosition = mousePositionNew;
    }

    private Vector2f fetchMousePositionNew() {
        DoubleBuffer xBuffer = BufferUtils.createDoubleBuffer(1);
        DoubleBuffer yBuffer = BufferUtils.createDoubleBuffer(1);
        GLFW.glfwGetCursorPos(windowId, xBuffer, yBuffer);

        // TODO: Learn more:
        xBuffer.rewind();
        yBuffer.rewind();
        float newX = (float) xBuffer.get();
        float newY = (float) yBuffer.get();

        return new Vector2f(newX, newY);
    }

    /* Getters */

    public boolean isKeyDown(int key) {
        return GLFW.glfwGetKey(windowId, key) == GLFW.GLFW_TRUE;
    }

    public boolean isKeyPressed(int key) {
        return isKeyDown(key) && !keys[key];
    }

    public boolean isKeyReleased(int key) {
        return !isKeyDown(key) && keys[key];
    }

    public boolean isMouseButtonDown(int button) {
        return GLFW.glfwGetMouseButton(windowId, button) == GLFW.GLFW_TRUE;
    }

    public boolean isMouseButtonPressed(int button) {
        return isMouseButtonDown(button) && !mouseButtons[button];
    }

    public boolean isMouseButtonReleased(int button) {
        return !isMouseButtonDown(button) && mouseButtons[button];
    }

    public Vector2f getMouseVelocity() {
        return new Vector2f(mouseVelocity);
    }

    public float getMouseX() {
        return mousePosition.x;
    }

    public float getMouseY() {
        return mousePosition.y;
    }

}
