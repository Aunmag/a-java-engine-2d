package nightingale.gui;

import nightingale.Application;
import nightingale.basics.BaseGrid;
import nightingale.basics.BaseQuad;
import nightingale.utilities.UtilsGraphics;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import java.awt.Color;

public class GuiButton extends GuiLabel {

    private static final Color colorDefault = new Color(128, 128, 128, 200);
    private static final Color colorTouched = new Color(166, 166, 166, 200);
    private static final Color fontColorUnavailable = new Color(204, 204, 204, 128);

    private BaseQuad onScreenQuad;
    private boolean isAvailable = true;
    private boolean isTouched = false;
    private boolean isPressed = false;
    private Runnable action;

    public GuiButton(
            BaseGrid grid,
            int x,
            int y,
            int width,
            int height,
            String text,
            Runnable action
    ) {
        super(grid, x, y, width, height, text);
        this.action = action;
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

        if (isPressed && action != null) {
            try {
                action.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void render() {
        Color color = isTouched ? colorTouched : colorDefault;
        GL11.glColor4f(
                // TODO: Optimize:
                color.getRed() / 255f,
                color.getGreen() / 255f,
                color.getBlue() / 255f,
                color.getAlpha() / 255f
        );
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

}
