package game;

import engine.Application;
import engine.Camera;
import engine.Input;
import engine.rendering.Texture;
import game.objects.Actor;
import game.objects.Object;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

public class GamePlay {

    public static void initialize() {
        Texture texture = Texture.getOrCreate("grass");
        int quantity = 10;
        int step = 2;
        int size = quantity * step;
        int start = size / -2;
        int end = start + size;
        for (float x = start; x < end; x += step) {
            for (float y = start; y < end; y += step) {
                Object.all.add(new Object(new Vector3f(x, y, 0), 0, texture));
            }
        }

        texture = Texture.getOrCreate("actor");
        Actor player = new Actor(new Vector3f(0, 0, 0), 0, texture);
        Actor.all.add(player);
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
        Application.getShader().bind();
        Object.allRender();
        Actor.allRender();
    }

    public static void cleanUp() {}

    public static void terminate() {}

}
