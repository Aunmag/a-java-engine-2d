package aunmag.nightingale;

import aunmag.nightingale.basics.BaseQuad;
import aunmag.nightingale.data.DataEngine;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;

public class Window extends BaseQuad {

    public static final int UNDEFINED_ID = 0;
    public final long id;
    public final Matrix4fc projection;
    private boolean isCursorGrabbed = false;
    private boolean isInitialized = false;

    Window() {
        super(
                Configs.isFullscreen() ? getFullScreenWidth() : 1024,
                Configs.isFullscreen() ? getFullScreenHeight() : 576
        );

        projection = new Matrix4f().setOrtho2D(
                -getCenterX(),
                +getCenterX(),
                -getCenterY(),
                +getCenterY()
        );

        GLFW.glfwWindowHint(GLFW.GLFW_SAMPLES, Configs.getAntialiasing());

        id = GLFW.glfwCreateWindow(
            (int) width,
            (int) height,
            DataEngine.titleFull,
            Configs.isFullscreen() ? GLFW.glfwGetPrimaryMonitor() : 0,
            0
        );

        if (id == UNDEFINED_ID) {
            throw new IllegalStateException("Failed to create window!");
        }

        if (!Configs.isFullscreen()) {
            GLFW.glfwSetWindowPos(
                    id,
                    (getFullScreenWidth() - (int) width) / 2,
                    (getFullScreenHeight() - (int) height) / 2
            );
        }

        GLFW.glfwShowWindow(id);
        GLFW.glfwMakeContextCurrent(id);

        isInitialized = true;
    }

    public void swapBuffers() {
        GLFW.glfwSwapBuffers(id);
    }

    public Vector2f calculateViewPosition(float x, float y) {
        x = (x - getCenterX() + 1) / getCenterX();
        y = (getCenterY() - y - 1) / getCenterY();
        return new Vector2f(x, y);
    }

    public void setCursorGrabbed(boolean isCursorGrabbed) {
        if (isCursorGrabbed == this.isCursorGrabbed) {
            return;
        } else {
            this.isCursorGrabbed = isCursorGrabbed;
        }

        if (isCursorGrabbed) {
            GLFW.glfwSetInputMode(id, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_HIDDEN);
            GLFW.glfwSetInputMode(id, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED);
        } else {
            GLFW.glfwSetInputMode(id, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_NORMAL);
        }
    }

    /* Setters */

    protected void setSize(float width, float height) {
        if (isInitialized) {
            String message = "Unable to change window size after initialization";
            System.err.println(message);
        } else {
            super.setSize(width, height);
        }
    }

    /* Getters */

    public static int getFullScreenWidth() {
        return GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor()).width();
    }

    public static int getFullScreenHeight() {
        return GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor()).height();
    }

}
