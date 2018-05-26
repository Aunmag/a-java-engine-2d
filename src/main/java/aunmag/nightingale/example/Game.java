package aunmag.nightingale.example;

import aunmag.nightingale.Application;
import aunmag.nightingale.font.FontStyleDefault;
import aunmag.nightingale.gui.GuiLabel;
import aunmag.nightingale.utilities.UtilsGraphics;
import org.lwjgl.opengl.GL11;

/**
 * The game inherits base engine {@link aunmag.nightingale.Application} class. When its
 * constructor is called the engine initialize everything it needs, then game's
 * constructor is being performed.
 */
class Game extends Application {

    private final GuiLabel message;
    private final GuiLabel details;

    Game() {
        message = new GuiLabel(5, 4, 2, 1, "Hello, World!");
        details = new GuiLabel(
                5, 5, 2, 1,
                "This message means the engine works!",
                FontStyleDefault.labelLight
        );
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
