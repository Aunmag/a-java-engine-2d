package aunmag.nightingale.gui;

import aunmag.nightingale.Application;
import aunmag.nightingale.basics.BaseGrid;
import aunmag.nightingale.basics.BaseQuad;
import aunmag.nightingale.font.Font;
import aunmag.nightingale.font.Text;
import org.joml.Vector4f;

public class GuiLabel extends BaseQuad {

    private static final int padding = 2;
    private BaseGrid grid;
    private Text text;
    private Font font;

    public GuiLabel(int x, int y, int width, int height, String message) {
        this(BaseGrid.grid12, x, y, width, height, message, Font.fontTitle, 2);
    }

    public GuiLabel(
            int x,
            int y,
            int width,
            int height,
            String message,
            Font font,
            float fontSize
    ) {
        this(BaseGrid.grid12, x, y, width, height, message, font, fontSize);
    }

    public GuiLabel(
            BaseGrid grid,
            int x,
            int y,
            int width,
            int height,
            String message,
            Font font,
            float fontSize
    ) {
        super(x, y, width, height);
        this.grid = grid;
        this.font = font;

        BaseQuad onScreenQuad = calculateOnScreenQuad();
        text = new Text(0, 0, onScreenQuad.getWidth() * 2, message, fontSize, font, true);
        text.setPositionCenteredBy(
                onScreenQuad.getX() + onScreenQuad.getCenterX(),
                onScreenQuad.getY() + onScreenQuad.getCenterY()
        );
    }

    protected BaseQuad calculateOnScreenQuad() {
        float x = getX() * grid.getStepX() + padding;
        float y = getY() * grid.getStepY() + padding;
        float width = grid.getStepX() * getWidth() - padding * 2;
        float height = grid.getStepY() * getHeight()  - padding * 2;

        return new BaseQuad(x, y, width, height);
    }

    public void render() {
        Application.getShader().bind();
        font.renderPrepare();
        text.render();
    }

    public void delete() {
        text.delete();
    }

    /* Setters */

    public void setTextColour(float red, float green, float blue, float alpha) {
        text.setColour(red, green, blue, alpha);
    }

    public void setTextColour(Vector4f colour) {
        text.setColour(colour);
    }

}
