package nightingale.gui;

import nightingale.Application;
import nightingale.structures.Texture;
import nightingale.utilities.UtilsGraphics;
import org.joml.Vector2f;
import org.lwjgl.opengl.GL11;

public class GuiPage {

    private GuiLabel[] labels;
    private GuiButton[] buttons;
    private Texture wallpaper;

    public GuiPage(GuiLabel[] labels, GuiButton[] buttons, Texture wallpaper) {
        this.labels = labels;
        this.buttons = buttons;
        this.wallpaper = wallpaper;

        if (wallpaper != null && !wallpaper.isScaled()) {
            wallpaper.scaleAsWallpaper();
        }
    }

    public void open() {
        GuiManager.open(this);
    }

    public void update() {
        for (GuiButton button: buttons) {
            button.update();
        }
    }

    public void render() {
        if (wallpaper != null) {
            renderWallpaper();
        }

        fillScreen();

        for (GuiLabel label: labels) {
            label.render();
        }

        for (GuiButton button: buttons) {
            button.render();
        }
    }

    private void fillScreen() {
        GL11.glColor4f(0.2f, 0.2f, 0.2f, 0.6f);

        int width = (int) Application.getWindow().getWidth();
        int height = (int) Application.getWindow().getHeight();

        UtilsGraphics.drawPrepare();
        UtilsGraphics.drawQuad(
                new Vector2f(0, 0),
                new Vector2f(width, 0),
                new Vector2f(width, height),
                new Vector2f(0, height),
                true,
                false
        );
    }

    private void renderWallpaper() {
        wallpaper.bind();

        Application.getShader().setUniformSampler(0);
        Application.getShader().setUniformProjection(
                Application.getWindow().getProjectionCopy()
        );

        wallpaper.render();
    }

}
