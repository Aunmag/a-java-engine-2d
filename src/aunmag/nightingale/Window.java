package aunmag.nightingale;

import aunmag.nightingale.basics.BaseQuad;
import aunmag.nightingale.data.DataEngine;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

public class Window extends BaseQuad {

    private long id;
    private Matrix4f projection;
    private boolean isCursorGrabbed = false;

    Window() {
        super(
                Configs.isFullscreen() ? getFullScreenWidth() : 1024,
                Configs.isFullscreen() ? getFullScreenHeight() : 576
        );
        projection = calculateProjection();

        GLFW.glfwWindowHint(GLFW.GLFW_SAMPLES, Configs.getAntialiasing());

        id = GLFW.glfwCreateWindow(
            (int) width,
            (int) height,
            DataEngine.titleFull,
            Configs.isFullscreen() ? GLFW.glfwGetPrimaryMonitor() : 0,
            0
        );

        if (id == 0) {
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
    }

    private Matrix4f calculateProjection() {
        projection = new Matrix4f();
        projection.setOrtho2D(-getCenterX(), getCenterX(), -getCenterY(), getCenterY());
        return projection;
    }

    public void swapBuffers() {
        GLFW.glfwSwapBuffers(id);
    }

    public Vector2f calculateViewPosition(float x, float y) {
        Vector3f viewPosition = calculateViewPosition(x, y, 0);
        return new Vector2f(viewPosition.x, viewPosition.y);
    }

    private Vector3f calculateViewPosition(float x, float y, float z) {
        Vector3f viewPosition = new Vector3f(
                x - getCenterX() + 1,
                getCenterY() - y - 1,
                z
        );
        viewPosition.mulPosition(projection);
        return viewPosition;
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
        super.setSize(width, height);
        projection = calculateProjection();
    }

    /* Getters */

    public long getId() {
        return id;
    }

    public Matrix4f getProjectionCopy() {
        return new Matrix4f(projection);
    }

    public static int getFullScreenWidth() {
        return GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor()).width();
    }

    public static int getFullScreenHeight() {
        return GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor()).height();
    }

}
