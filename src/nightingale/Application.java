package nightingale;

import nightingale.data.DataEngine;
import nightingale.data.DataTime;
import nightingale.structures.Model;
import nightingale.structures.Texture;
import nightingale.structures.Vao;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import nightingale.shaders.ShaderSprite;

public abstract class Application {

    // TODO: Prevent multiple launching
    public static boolean isRunning = false;
    private static Window window;
    private static Camera camera;
    private static ShaderSprite shader;
    private static Input input;

    public Application() {
        if (!GLFW.glfwInit()) {
            System.err.println("GLFW Failed to initialize!");
            System.exit(1);
        }

        window = new Window();
        GL.createCapabilities();

        camera = new Camera();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_BLEND);

        shader = new ShaderSprite("shaderSprite");

        input = new Input(window.getId());
    }

    public final void run() {
        isRunning = true;
        long timeLast = System.currentTimeMillis();

        while (isRunning) {
            long timeCurrent = System.currentTimeMillis();
            long timePassed = timeCurrent - timeLast;
            float timeDelta = timePassed / DataTime.getTimeFrameDuration();

            if (timeDelta >= 1) {
                DataTime.setTimeCurrent(timeCurrent);
                DataTime.setTimeDelta(timeDelta);
                engineUpdate();
                engineRender();
                timeLast = timeCurrent;
            }
        }

        engineTerminate();
    }

    private void engineUpdate() {
        input.update();
        GLFW.glfwPollEvents();

        gameUpdate();
        camera.update();

        if (GLFW.glfwWindowShouldClose(window.getId())) {
            isRunning = false;
        }
    }

    private void engineRender() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        Application.getShader().bind();
        gameRender();
        window.swapBuffers();
    }

    private void engineTerminate() {
        gameTerminate();
        Vao.cleanUp();
        Texture.cleanUp();
        Model.cleanUp();
        ShaderSprite.cleanUp();
        GLFW.glfwSetWindowShouldClose(window.getId(), true);
        GLFW.glfwTerminate();
        System.out.println(DataEngine.name + " has terminated gracefully.");
    }

    protected abstract void gameUpdate();

    protected abstract void gameRender();

    protected abstract void gameTerminate();

    /* Getters */

    public static Window getWindow() {
        return window;
    }

    public static Camera getCamera() {
        return camera;
    }

    public static ShaderSprite getShader() {
        return shader;
    }

    public static Input getInput() {
        return input;
    }

}
