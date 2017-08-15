package nightingale.engine.gui;

import nightingale.engine.basics.BaseQuad;
import nightingale.engine.font.Font;
import nightingale.engine.font.FontLoader;
import nightingale.engine.font.Text;
import nightingale.engine.utilities.UtilsGraphics;
import org.joml.Vector2f;
import org.lwjgl.opengl.GL11;

public class GuiLabel extends BaseQuad {

//    private final static Font font = new FontLoader("ubuntu").build();

    private final Text text;

    public GuiLabel(Vector2f position, float width, float height, String message) {
        super(position, width, height);

        Font font = new FontLoader("ubuntu").build();
        System.out.println(position.y);

        float textWidth = (getPointDisplayB().x - getPointDisplayA().x) / 2f;
        float textShift = (getPointD().y - getPointA().y) / 3f;
        text = new Text(position.x, position.y + textShift, message, 1, font, textWidth, true);
    }

    public void render() {
        text.render();
        UtilsGraphics.drawPrepare();
        UtilsGraphics.drawQuad(this, false, false);
        UtilsGraphics.drawFinish();
    }

}
