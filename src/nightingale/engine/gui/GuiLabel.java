package nightingale.engine.gui;

import nightingale.engine.basics.BaseGrid;
import nightingale.engine.basics.BaseQuad;
import nightingale.engine.font.Font;
import nightingale.engine.font.FontLoader;
import nightingale.engine.font.Text;

public class GuiLabel extends BaseQuad {

    public static final Font font = new FontLoader("ubuntu").build();
    private static final int padding = 2;

    private BaseGrid grid;
    private Text text;

    public GuiLabel(BaseGrid grid, int x, int y, int width, int height, String message) {
        super(x, y, width, height);
        this.grid = grid;

        BaseQuad onScreenQuad = calculateOnScreenQuad();
        text = new Text(0, 0, onScreenQuad.getWidth() * 2, message, 1.6f, font, true);
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
        text.render();
    }

}
