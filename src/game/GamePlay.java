package game;

import engine.Application;
import engine.Input;
import engine.rendering.Texture;
import engine.utilities.UtilsMath;
import game.objects.Actor;
import game.objects.Object;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

public class GamePlay {

    private static Actor player;

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
        player.addRadians((float) UtilsMath.PI_0_5);
        GamePlay.setPlayer(player);
        Actor.all.add(player);
    }

    public static void update() {
        if (Input.getIsKeyReleased(GLFW.GLFW_KEY_ESCAPE)) {
            Application.isRunning = false;
        }

        updatePlayerInput();

        Actor.allUpdate();
    }

    private static void updatePlayerInput() {
        if (player == null) {
            return;
        }

        player.isWalkingForward = Input.getIsKeyDown(GLFW.GLFW_KEY_W);
        player.isWalkingBack = Input.getIsKeyDown(GLFW.GLFW_KEY_S);
        player.isWalkingLeft = Input.getIsKeyDown(GLFW.GLFW_KEY_A);
        player.isWalkingRight = Input.getIsKeyDown(GLFW.GLFW_KEY_D);
        player.isSprinting = Input.getIsKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT);

        float sensitivity = 0.01f;
        float rotate = Input.getMouseVelocity().x() * sensitivity;
        player.addRadians(rotate);
    }

    public static void render() {
        Application.getShader().bind();
        Object.allRender();
        Actor.allRender();
    }

    public static void cleanUp() {}

    public static void terminate() {}

    /* Setters */

    public static void setPlayer(Actor player) {
        GamePlay.player = player;
    }

    /* Getters */

    public static Actor getPlayer() {
        return player;
    }

}
