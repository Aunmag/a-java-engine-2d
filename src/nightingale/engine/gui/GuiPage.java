package nightingale.engine.gui;

import nightingale.engine.Application;
import nightingale.engine.utilities.UtilsGraphics;
import org.joml.Vector2f;
import org.lwjgl.opengl.GL11;

public class GuiPage {

    private GuiLabel[] labels;
    private GuiButton[] buttons;

    public GuiPage(GuiLabel[] labels, GuiButton[] buttons) {
        this.labels = labels;
        this.buttons = buttons;
    }

    public void update() {
        for (GuiButton button: buttons) {
            button.update();
        }
    }

    public void render() {
        fillScreen();

        for (GuiLabel label: labels) {
            label.render();
        }

        for (GuiButton button: buttons) {
            button.render();
        }
    }

    private void fillScreen() {
        GL11.glColor4f(0.3f, 0.3f, 0.3f, 0.3f);

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

}
