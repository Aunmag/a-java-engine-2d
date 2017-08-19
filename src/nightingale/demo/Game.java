package nightingale.demo;

import nightingale.Application;
import nightingale.basics.BaseGrid;
import nightingale.basics.BaseSprite;
import nightingale.data.DataEngine;
import nightingale.font.Font;
import nightingale.font.FontLoader;
import nightingale.font.Text;

import nightingale.gui.GuiButton;
import nightingale.gui.GuiLabel;
import nightingale.gui.GuiPage;
import nightingale.structures.Texture;
import nightingale.utilities.UtilsGraphics;
import nightingale.utilities.UtilsMath;
import nightingale.demo.sprites.Actor;
import nightingale.demo.sprites.Object;
import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

public class Game extends Application {

    private static final int borderSize = 512;

    private boolean isPause = true;
    private Actor player;
    private Text text;
    private GuiPage menu;

    Game() {
        initializeTerrain();
        player = createPlayer();
        text = createText();
        menu = createMenu();
    }

    private void initializeTerrain() {
        Texture texture = Texture.getOrCreate("images/grass");

        int quantity = 4;
        int step = 128;
        int size = step * quantity;
        int start = (size / -2) + (step / 2);
        int end = start + size;
        for (float x = start; x < end; x += step) {
            for (float y = start; y < end; y += step) {
                Object terrain = new Object(x, y, 0, texture);
                Object.all.add(terrain);
            }
        }
    }

    private Actor createPlayer() {
        Texture texture = Texture.getOrCreate("images/actor");
        Actor player = new Actor(0, 0, 0, texture);
        player.setRadians((float) UtilsMath.PIx0_5);
        Actor.all.add(player);
        Application.getCamera().setTarget(player);
        return player;
    }

    private Text createText() {
        FontLoader fontLoader = new FontLoader("ubuntu");
        Font font = fontLoader.build();
        String message = DataEngine.titleFull;
        float textWidth = Application.getWindow().getWidth();
        return new Text(10, 10, textWidth, message, 1, font, false);
    }

    private GuiPage createMenu() {
        GuiLabel[] labels = {
                new GuiLabel(
                        BaseGrid.grid12, 4, 2, 4, 1,
                        "Welcome!"
                ),
                new GuiLabel(
                        BaseGrid.grid12, 0, 3, 12, 1,
                        "This is " + DataEngine.title + " demo"
                ),
        };

        GuiButton[] buttons = {
                new GuiButton(
                        BaseGrid.grid12, 4, 8, 4, 1,
                        "Try it", () -> isPause = false
                ),
                new GuiButton(
                        BaseGrid.grid12, 4, 9, 4, 1,
                        "Quit", () -> Application.isRunning = false
                ),
        };

        return new GuiPage(labels, buttons, Texture.getOrCreate("images/wallpaper"));
    }

    protected void gameUpdate() {
        if (Application.getInput().isKeyPressed(GLFW.GLFW_KEY_ESCAPE)) {
            isPause = !isPause;
        }

        if (isPause) {
            menu.update();
        } else {
            updatePlayerInput();
            BaseSprite.updateAll(Actor.all);
            confinePlayer();
        }
    }

    protected void gameRender() {
        if (isPause) {
            menu.render();
        } else {
            BaseSprite.renderAll(Object.all);
            renderBorders();
            BaseSprite.renderAll(Actor.all);
            text.render();
        }
    }

    protected void gameCleanUp() {}

    protected void gameTerminate() {
        Text.terminate();
    }

    private void confinePlayer() {
        int n = borderSize / 2;

        if (n < player.getX()) {
            player.setX(n);
        } else if (player.getX() < -n) {
            player.setX(-n);
        }

        if (n < player.getY()) {
            player.setY(n);
        } else if (player.getY() < -n) {
            player.setY(-n);
        }
    }

    private void updatePlayerInput() {
        if (player == null) {
            return;
        }

        player.isWalkingForward = Application.getInput().isKeyDown(GLFW.GLFW_KEY_W);
        player.isWalkingBack = Application.getInput().isKeyDown(GLFW.GLFW_KEY_S);
        player.isWalkingLeft = Application.getInput().isKeyDown(GLFW.GLFW_KEY_A);
        player.isWalkingRight = Application.getInput().isKeyDown(GLFW.GLFW_KEY_D);
        player.isSprinting = Application.getInput().isKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT);

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
