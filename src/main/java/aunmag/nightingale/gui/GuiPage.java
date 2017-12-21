package aunmag.nightingale.gui;

import aunmag.nightingale.Application;
import aunmag.nightingale.structures.Texture;
import aunmag.nightingale.utilities.UtilsGraphics;
import org.lwjgl.opengl.GL11;

public class GuiPage {

    private GuiLabel[] labels;
    private GuiButton[] buttons;
    private Texture wallpaper;

    public GuiPage(GuiLabel[] labels, GuiButton[] buttons, Texture wallpaper) {
        this.labels = labels;
        this.buttons = buttons;
        this.wallpaper = wallpaper;
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
        UtilsGraphics.drawQuad(0, 0, width, height, true, false);
    }

    private void renderWallpaper() {
        wallpaper.bind();
        Application.getShader().setUniformProjection(Application.getWindow().projection);
        wallpaper.render();
    }

}
