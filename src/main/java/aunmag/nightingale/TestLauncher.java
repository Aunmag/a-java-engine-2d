package aunmag.nightingale;

import aunmag.nightingale.font.FontStyleDefault;
import aunmag.nightingale.gui.GuiLabel;
import aunmag.nightingale.utilities.UtilsGraphics;
import org.lwjgl.opengl.GL11;

class TestLauncher extends Application {

    public static void main(String[] args) {
        new TestLauncher().run();
    }

    private GuiLabel message = new GuiLabel(5, 4, 2, 1, "It worked!");
    private GuiLabel details = new GuiLabel(
            5, 5, 2, 1,
            "This is second text",
            FontStyleDefault.labelLight
    );

    private TestLauncher() {
        Application.frameRate.setFrequency(1);
    }

    protected void gameUpdate() {}

    protected void gameRender() {
        float n = 20;
        GL11.glColor3f(0.8f, 0.8f, 0.8f);
        UtilsGraphics.drawPrepare();
        UtilsGraphics.drawQuad(n, n, n, n, true, false);

        message.render();
        details.render();

        float sizeX = Application.getWindow().getWidth();
        float sizeY = Application.getWindow().getHeight();
        UtilsGraphics.drawQuad(sizeX - 2 * n, sizeY - 2 * n, n, n, true, false);
    }

    protected void gameTerminate() {
        message.delete();
        details.delete();
    }

}
