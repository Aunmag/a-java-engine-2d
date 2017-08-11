package nightingale.engine;

import nightingale.engine.basics.BaseSize;
import nightingale.engine.data.DataEngine;
import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;

public class Window extends BaseSize {

    private long id;
    private boolean isFullscreen = false;
    private Matrix4f projection;

    public Window() {
        super(854, 480);

        id = GLFW.glfwCreateWindow(
            width,
            height,
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
                    (videoMode.width() - width) / 2,
                    (videoMode.height() - height) / 2
            );
        }

        GLFW.glfwShowWindow(id);
        GLFW.glfwMakeContextCurrent(id);
    }

    private void updateProjection() {
        projection = new Matrix4f();
        projection.setOrtho2D(-getCenterX(), getCenterX(), -getCenterY(), getCenterY());
    }

    public void swapBuffers() {
        GLFW.glfwSwapBuffers(id);
    }

    /* Setters */

    protected void setSize(int size) {
        super.setSize(size);
        updateProjection();
    }

    protected void setSize(int width, int height) {
        super.setSize(width, height);
        updateProjection();
    }

    /* Getters */

    public long getId() {
        return id;
    }

    public Matrix4f getProjectionCopy() {
        return new Matrix4f(projection);
    }

}
