package game;

import engine.Application;
import engine.Camera;
import engine.Input;
import engine.rendering.Texture;
import game.objects.Object;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

public class GamePlay {

    public static void initialize() {
        Texture texture = Texture.getOrCreate("grass");
        Object.all.add(new Object(new Vector3f(0, 0, 0), 0, texture));
        Object.all.add(new Object(new Vector3f(1, 1, 0), 1, texture));
        Object.all.add(new Object(new Vector3f(3, 1, 0), 2, texture));
    }

    public static void update() {
        if (Input.getIsKeyReleased(GLFW.GLFW_KEY_ESCAPE)) {
            Application.isRunning = false;
        }

        Camera camera = Application.getCamera();

        if (Input.getIsKeyDown(GLFW.GLFW_KEY_A)) {
            camera.getPosition().sub(new Vector3f(-5, 0, 0));
        }
        if (Input.getIsKeyDown(GLFW.GLFW_KEY_D)) {
            camera.getPosition().sub(new Vector3f(5, 0, 0));
        }
        if (Input.getIsKeyDown(GLFW.GLFW_KEY_W)) {
            camera.getPosition().sub(new Vector3f(0, 5, 0));
        }
        if (Input.getIsKeyDown(GLFW.GLFW_KEY_S)) {
            camera.getPosition().sub(new Vector3f(0, -5, 0));
        }

        if (Input.getIsKeyDown(GLFW.GLFW_KEY_Q)) {
            camera.addRadians(-0.05f);
        }
        if (Input.getIsKeyDown(GLFW.GLFW_KEY_E)) {
            camera.addRadians(0.05f);
        }
    }

    public static void render() {
        Object.allRender();
    }

    public static void cleanUp() {}

    public static void terminate() {}

}
