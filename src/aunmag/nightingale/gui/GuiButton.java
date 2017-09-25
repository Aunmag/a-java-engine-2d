package aunmag.nightingale.gui;

import aunmag.nightingale.Application;
import aunmag.nightingale.basics.BaseGrid;
import aunmag.nightingale.basics.BaseQuad;
import aunmag.nightingale.font.Font;
import aunmag.nightingale.utilities.UtilsGraphics;
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

    GuiButton(int x, int y, int width, int height, String text) {
        this(BaseGrid.grid12, x, y, width, height, text);
    }

    GuiButton(
            BaseGrid grid,
            int x,
            int y,
            int width,
            int height,
            String text
    ) {
        super(grid, x, y, width, height, text, Font.fontTitle, 1.5f);
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
        isPressed = isTouched && Application.getInput().isMouseButtonReleased(
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

        if (isAvailable) {
            setTextColour(1, 1, 1, 1);
        } else {
            isTouched = false;
            isPressed = false;
            setTextColour(
                fontColorUnavailable.getRed() / 255f,
                fontColorUnavailable.getGreen() / 255f,
                fontColorUnavailable.getBlue() / 255f,
                fontColorUnavailable.getAlpha() / 255f
            );
        }
    }

    /* Getters */

    public boolean isPressed() {
        return isPressed;
    }

}