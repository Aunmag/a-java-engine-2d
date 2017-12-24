package aunmag.nightingale;

import aunmag.nightingale.audio.AudioMaster;
import aunmag.nightingale.audio.AudioSample;
import aunmag.nightingale.audio.AudioSource;
import aunmag.nightingale.data.DataEngine;
import aunmag.nightingale.shaders.ShaderTextured;
import aunmag.nightingale.structures.Model;
import aunmag.nightingale.structures.Shader;
import aunmag.nightingale.structures.Texture;
import aunmag.nightingale.structures.Vao;
import aunmag.nightingale.utilities.TimeFlow;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public abstract class Application {

    private static boolean isInitialized = false;
    private static boolean isRunning = false;
    private static Window window;
    private static Camera camera;
    private static ShaderTextured shader;
    public static final FrameRate frameRate = new FrameRate(60);
    public static final TimeFlow time = new TimeFlow();

    public Application() {
        if (isInitialized) {
            throw new IllegalStateException("Engine is already initialized!");
        }

        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("Failed to initialize GLFW!");
        }

        isInitialized = true;

        window = new Window();
        GL.createCapabilities();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glClearColor(0.2f, 0.2f, 0.2f, 1.0f);

        camera = new Camera();
        shader = new ShaderTextured();

        AudioMaster.initialize();
    }

    public final void run() {
        isRunning = true;

        while (isRunning) {
            double timeCurrent = (double) System.currentTimeMillis() / 1000.0;

            if (frameRate.calculateIsNow(timeCurrent)) {
                engineUpdate();
                engineRender();
            }
        }

        engineTerminate();
    }

    private void engineUpdate() {
        time.add(frameRate.getTimeDelta(), true);
        camera.resetOffsets();
        Input.update();
        GLFW.glfwPollEvents();

        gameUpdate();
        camera.update();

        if (GLFW.glfwWindowShouldClose(window.id)) {
            stopRunning();
        }
    }

    private void engineRender() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        gameRender();
        GLFW.glfwSwapBuffers(window.id);
    }

    private void engineTerminate() {
        gameTerminate();

        Vao.cleanUp();
        Texture.cleanUp();
        Model.cleanUp();
        Shader.cleanUp();
        AudioSource.cleanUp();
        AudioSample.cleanUp();
        AudioMaster.terminate();

        GLFW.glfwSetWindowShouldClose(window.id, true);
        GLFW.glfwTerminate();
        System.out.println(DataEngine.name + " has terminated gracefully.");
    }

    protected abstract void gameUpdate();

    protected abstract void gameRender();

    protected abstract void gameTerminate();

    public static void stopRunning() {
        isRunning = false;
    }

    /* Getters */

    public static boolean isInitialized() {
        return isInitialized;
    }

    public static boolean isRunning() {
        return isRunning;
    }

    public static Window getWindow() {
        return window;
    }

    public static Camera getCamera() {
        return camera;
    }

    public static ShaderTextured getShader() {
        return shader;
    }

}
