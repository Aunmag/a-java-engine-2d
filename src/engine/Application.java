package engine;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import engine.rendering.Shader;

public abstract class Application {

    public static boolean isRunning = false;
    private static Window window;
    private static Camera camera;
    private static Shader shader;

    public final void run() {
        // TODO: Prevent multiple launching

        engineInitialize();

        int fpsLimit = 60;
        float timeFrameDuration = 1000 / fpsLimit;
        float timeDelta;
        long timeCurrent;
        long timeLast = System.currentTimeMillis();

        isRunning = true;
        while (isRunning) {
            timeCurrent = System.currentTimeMillis();
            long timePassed = timeCurrent - timeLast;
            timeDelta = timePassed / timeFrameDuration;

            if (timeDelta >= 1) {
                timeLast = timeCurrent;
                engineUpdate();
                engineRender();
                engineCleanUp();
            }
        }

        engineTerminate();
    }

    private void engineInitialize() {
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

        shader = new Shader("shader");
        gameInitialize();
    }

    private void engineUpdate() {
        Input.update();
        GLFW.glfwPollEvents();

        gameUpdate();
        camera.update();

        if (GLFW.glfwWindowShouldClose(window.getId())) {
            isRunning = false;
        }
    }

    private void engineRender() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        gameRender();
        window.swapBuffers();
    }

    private void engineCleanUp() {
        gameCleanUp();
    }

    private void engineTerminate() {
        gameTerminate();
        GLFW.glfwSetWindowShouldClose(window.getId(), true);
        GLFW.glfwTerminate();
    }

    protected abstract void gameInitialize();

    protected abstract void gameUpdate();

    protected abstract void gameRender();

    protected abstract void gameCleanUp();

    protected abstract void gameTerminate();

    /* Getters */

    public static Window getWindow() {
        return window;
    }

    public static Camera getCamera() {
        return camera;
    }

    public static Shader getShader() {
        return shader;
    }

}
