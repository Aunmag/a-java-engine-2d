package demo;

import engine.Application;
import engine.Input;
import engine.rendering.Texture;
import engine.utilities.UtilsGraphics;
import engine.utilities.UtilsMath;
import demo.sprites.Actor;
import demo.sprites.Object;
import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

public class GamePlay {

    private static Actor player;
    private static final int borderSize = 512;

    public static void initialize() {
        Texture texture = Texture.getOrCreate("grass");
        int quantity = 4;
        int step = 128;
        int size = step * quantity;
        int start = (size / -2) + (step / 2);
        int end = start + size;
        for (float x = start; x < end; x += step) {
            for (float y = start; y < end; y += step) {
                Object.all.add(new Object(x, y, 0, 0, texture));
            }
        }

        texture = Texture.getOrCreate("actor");
        Actor player = new Actor(0, 0, 0, 0, texture);
        player.setRadians((float) UtilsMath.PIx0_5);
        GamePlay.setPlayer(player);
        Application.getCamera().setTarget(player);
        Actor.all.add(player);
    }

    public static void update() {
        if (Input.getIsKeyReleased(GLFW.GLFW_KEY_ESCAPE)) {
            Application.isRunning = false;
        }

        updatePlayerInput();
        Actor.allUpdate();
        confinePlayer();
    }

    private static void confinePlayer() {
        int n = borderSize / 2;

        if (n < player.x()) {
            player.x = n;
        } else if (player.x() < -n) {
            player.x = -n;
        }

        if (n < player.y()) {
            player.y = n;
        } else if (player.y() < -n) {
            player.y = -n;
        }
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
        renderBorders();
        Actor.allRender();
    }

    private static void renderBorders() {
        GL11.glColor3f(1, 0, 0);
        GL11.glLineWidth(2);
        int n = borderSize / 2;
        UtilsGraphics.drawPrepare();
        UtilsGraphics.drawQuad(
                new Vector2f(-n, +n),
                new Vector2f(+n, +n),
                new Vector2f(+n, -n),
                new Vector2f(-n, -n),
                false,
                true
        );
        GL11.glColor3f(0, 1, 0);
        UtilsGraphics.drawCircle(new Vector2f(0, 0), 32, false, true);
        UtilsGraphics.drawFinish();
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