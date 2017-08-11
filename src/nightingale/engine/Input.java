package nightingale.engine;

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

    Input(long windowId) {
        this.windowId = windowId;
        Arrays.fill(keys, false);
        mousePosition = fetchMousePositionNew();
    }

    void update() {
        for (int i = 0; i < keys.length; i++) {
            keys[i] = getIsKeyDown(i);
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

    public boolean getIsKeyDown(int key) {
        return GLFW.glfwGetKey(windowId, key) == GLFW.GLFW_TRUE;
    }

    public boolean getIsKeyPressed(int key) {
        return getIsKeyDown(key) && !keys[key];
    }

    public boolean getIsKeyReleased(int key) {
        return !getIsKeyDown(key) && keys[key];
    }

    public boolean getIsMouseButtonDown(int button) {
        return GLFW.glfwGetMouseButton(windowId, button) == GLFW.GLFW_TRUE;
    }

    /* Getters */

    public Vector2f getMouseVelocity() {
        return new Vector2f(mouseVelocity);
    }

}
