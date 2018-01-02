package aunmag.nightingale.input;

import aunmag.nightingale.Application;
import org.joml.Vector2f;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;

import java.nio.DoubleBuffer;
import java.util.Arrays;

public final class Mouse {

    public final MouseWheel wheel = new MouseWheel();
    private final boolean[] buttons = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];
    private final Vector2f position = new Vector2f(0, 0);
    private final Vector2f velocity = new Vector2f(0, 0);

    Mouse() {
        Arrays.fill(buttons, false);
    }

    final void update() {
        wheel.update();
        updateButtons();
        updatePosition();
    }

    private void updateButtons() {
        if (!Input.isAvailable()) {
            return;
        }

        for (int button = 0; button < buttons.length; button++) {
            buttons[button] = isButtonDown(button);
        }
    }

    private void updatePosition() {
        if (!Input.isAvailable()) {
            velocity.set(0, 0);
            return;
        }

        DoubleBuffer xBuffer = BufferUtils.createDoubleBuffer(1);
        DoubleBuffer yBuffer = BufferUtils.createDoubleBuffer(1);
        GLFW.glfwGetCursorPos(Application.getWindow().id, xBuffer, yBuffer);

        xBuffer.rewind();
        yBuffer.rewind();
        float x = (float) xBuffer.get();
        float y = (float) yBuffer.get();

        velocity.set(position.x() - x, position.y() - y);
        position.set(x, y);
    }

    /* Getters */

    public final boolean isButtonDown(int button) {
        return Input.isAvailable()
                && GLFW.glfwGetMouseButton(Application.getWindow().id, button) == GLFW.GLFW_TRUE;
    }

    public final boolean isButtonPressed(int button) {
        return isButtonDown(button) && !buttons[button];
    }

    public final boolean isButtonReleased(int button) {
        return !isButtonDown(button) && buttons[button];
    }

    public final float getVelocityX() {
        return velocity.x();
    }

    public final float getVelocityY() {
        return velocity.y();
    }

    public final float getX() {
        return position.x();
    }

    public final float getY() {
        return position.y();
    }

}
