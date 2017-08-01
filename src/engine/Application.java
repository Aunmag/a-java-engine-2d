package engine;

import demo.GamePlay;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import engine.rendering.Shader;

public class Application {

    public static boolean isRunning = false;
    private static Window window;
    private static Camera camera;
    private static Shader shader;

    public static void main(String[] args) {
        prepare();

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
                update();
                render();
                cleanUp();
            }
        }

        stop();
    }

    private static void prepare() {
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
        GamePlay.initialize();
    }

    private static void update() {
        Input.update();
        GLFW.glfwPollEvents();

        GamePlay.update();
        camera.update();

        if (GLFW.glfwWindowShouldClose(window.getId())) {
            isRunning = false;
        }
    }

    private static void render() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        GamePlay.render();
        window.swapBuffers();
    }

    private static void cleanUp() {}

    private static void stop() {
        GamePlay.terminate();
        GLFW.glfwSetWindowShouldClose(window.getId(), true);
        GLFW.glfwTerminate();
    }

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
