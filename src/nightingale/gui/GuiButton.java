package nightingale.gui;

import nightingale.Application;
import nightingale.basics.BaseGrid;
import nightingale.basics.BaseQuad;
import nightingale.utilities.UtilsGraphics;
import org.lwjgl.glfw.GLFW;

import java.awt.*;

public class GuiButton extends GuiLabel {

    private static final Color colorDefault = new Color(128, 128, 128, 200);
    private static final Color colorTouched = new Color(166, 166, 166, 200);
    private static final Color fontColorUnavailable = new Color(204, 204, 204, 128);

    private BaseQuad onScreenQuad;
    private boolean isAvailable = true;
    private boolean isTouched = false;
    private boolean isPressed = false;

    GuiButton(
            int x,
            int y,
            int width,
            int height,
            String text
    ) {
        super(BaseGrid.grid12, x, y, width, height, text);
        onScreenQuad = calculateOnScreenQuad();
    }

    GuiButton(
            BaseGrid grid,
            int x,
            int y,
            int width,
            int height,
            String text
    ) {
        super(grid, x, y, width, height, text);
        onScreenQuad = calculateOnScreenQuad();
    }

    public void update() {
        if (!isAvailable) {
            return;
        }

        isTouched = onScreenQuad.calculateIsPointInside(
                Application.getInput().getMouseX(),
                Application.getInput().getMouseY()
        );
        isPressed = isTouched && Application.getInput().isMouseButtonPressed(
                GLFW.GLFW_MOUSE_BUTTON_1
        );
    }

    public void render() {
        Color color = isTouched ? colorTouched : colorDefault;
        UtilsGraphics.setDrawColor(color);
        UtilsGraphics.drawPrepare();
        UtilsGraphics.drawQuad(onScreenQuad, true, false);

        super.render();
    }

    /* Setters */

    public void setIsAvailable(boolean isAvailable) {
        if (isAvailable == this.isAvailable) {
            return;
        } else {
            this.isAvailable = isAvailable;
        }

        if (!isAvailable) {
            isTouched = false;
            isPressed = false;
        }
    }

    /* Getters */

    public boolean isPressed() {
        return isPressed;
    }

}
