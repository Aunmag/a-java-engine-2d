package nightingale.engine;

import nightingale.engine.basics.BaseSize;
import nightingale.engine.data.DataEngine;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;

public class Window extends BaseSize {

    private long id;
    private boolean isFullscreen = false;
    private Matrix4f projection;
    private float aspectRatio;

    public Window() {
        super(854, 480);

        id = GLFW.glfwCreateWindow(
            (int) width,
            (int) height,
            DataEngine.titleFull,
            isFullscreen ? GLFW.glfwGetPrimaryMonitor() : 0,
            0
        );

        if (id == 0) {
            throw new IllegalStateException("Failed to create id!");
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

    private void updateProjection() {
        projection = new Matrix4f();
        projection.setOrtho2D(-getCenterX(), getCenterX(), -getCenterY(), getCenterY());
    }

    private void updateAspectRatio() {
        aspectRatio = width / height;
    }

    public void swapBuffers() {
        GLFW.glfwSwapBuffers(id);
    }

    public Vector2f calculateDisplayPosition(Vector2f position) {
        return new Vector2f((position.x + 1) / width - 1, (-position.y - 1) / height + 1);
    }

    /* Setters */

    protected void setSize(float width, float height) {
        super.setSize(width, height);
        updateProjection();
        updateAspectRatio();
    }

    /* Getters */

    public long getId() {
        return id;
    }

    public Matrix4f getProjectionCopy() {
        return new Matrix4f(projection);
    }

    public float getAspectRatio() {
        return aspectRatio;
    }

}
