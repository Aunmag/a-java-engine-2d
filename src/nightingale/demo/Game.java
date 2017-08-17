package nightingale.demo;

import nightingale.engine.Application;
import nightingale.engine.basics.BaseGrid;
import nightingale.engine.data.DataEngine;
import nightingale.engine.font.Font;
import nightingale.engine.font.FontLoader;
import nightingale.engine.font.Text;

import nightingale.engine.gui.GuiLabel;
import nightingale.engine.structures.Texture;
import nightingale.engine.utilities.UtilsGraphics;
import nightingale.engine.utilities.UtilsMath;
import nightingale.demo.sprites.Actor;
import nightingale.demo.sprites.Object;
import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

public class Game extends Application {

    private static final int borderSize = 512;

    private Actor player;
    private Text text;
    private GuiLabel label;

    Game() {
        Texture texture = Texture.getOrCreate("images/grass");
        int quantity = 4;
        int step = 128;
        int size = step * quantity;
        int start = (size / -2) + (step / 2);
        int end = start + size;
        for (float x = start; x < end; x += step) {
            for (float y = start; y < end; y += step) {
                Object.all.add(new Object(x, y, 0, texture));
            }
        }

        texture = Texture.getOrCreate("images/actor");
        player = new Actor(0, 0, 0, texture);
        player.radians = (float) UtilsMath.PIx0_5;
        Application.getCamera().setTarget(player);
        Actor.all.add(player);

        FontLoader fontLoader = new FontLoader("ubuntu");
        Font font = fontLoader.build();
        String message = DataEngine.titleFull;
        float textWidth = Application.getWindow().getWidth();
        text = new Text(10, 10, textWidth, message, 1, font, false);

        BaseGrid grid = new BaseGrid(12);
        label = new GuiLabel(grid, 3, 10, 6, 1, "Welcome! This is a test label.");
    }

    protected void gameUpdate() {
        if (Application.getInput().getIsKeyReleased(GLFW.GLFW_KEY_ESCAPE)) {
            Application.isRunning = false;
        }

        updatePlayerInput();
        Actor.allUpdate();
        confinePlayer();
    }

    protected void gameRender() {
        Object.allRender();
        renderBorders();
        Actor.allRender();

        text.render();
        label.render();
    }

    protected void gameCleanUp() {}

    protected void gameTerminate() {
        Text.terminate();
    }

    private void confinePlayer() {
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

    private void updatePlayerInput() {
        if (player == null) {
            return;
        }

        player.isWalkingForward = Application.getInput().getIsKeyDown(GLFW.GLFW_KEY_W);
        player.isWalkingBack = Application.getInput().getIsKeyDown(GLFW.GLFW_KEY_S);
        player.isWalkingLeft = Application.getInput().getIsKeyDown(GLFW.GLFW_KEY_A);
        player.isWalkingRight = Application.getInput().getIsKeyDown(GLFW.GLFW_KEY_D);
        player.isSprinting = Application.getInput().getIsKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT);

        float sensitivity = 0.01f;
        float rotate = Application.getInput().getMouseVelocity().x() * sensitivity;
        player.addRadiansCarefully(rotate);
    }

    private void renderBorders() {
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

}
