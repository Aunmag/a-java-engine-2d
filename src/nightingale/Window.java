package nightingale;

import nightingale.basics.BaseSize;
import nightingale.data.DataEngine;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;

public class Window extends BaseSize {

    private long id;
    private boolean isFullscreen = false;
    private Matrix4f projection;

    public Window() {
        super(1024, 576);
        projection = calculateProjection();

        id = GLFW.glfwCreateWindow(
            (int) width,
            (int) height,
            DataEngine.titleFull,
            isFullscreen ? GLFW.glfwGetPrimaryMonitor() : 0,
            0
        );

        if (id == 0) {
            throw new IllegalStateException("Failed to create window!");
        }

        if (!isFullscreen) {
            GLFWVidMode videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
            GLFW.glfwSetWindowPos(
                    id,
                    (videoMode.width() - (int) width) / 2,
                    (videoMode.height() - (int) height) / 2
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

}
