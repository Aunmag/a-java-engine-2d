package aunmag.nightingale.gui;

import aunmag.nightingale.utilities.UtilsGraphics;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

public class GuiPage {

    private ArrayList<GuiLabel> labels = new ArrayList<>();
    private ArrayList<GuiButton> buttons = new ArrayList<>();

    public void open() {
        GuiManager.open(this);
    }

    public void add(GuiLabel label) {
        labels.add(label);
    }

    public void add(GuiButton button) {
        buttons.add(button);
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
        GL11.glColor4f(0.2f, 0.2f, 0.2f, 0.2f);
        UtilsGraphics.drawPrepare();
        UtilsGraphics.fillScreen();
    }

}
