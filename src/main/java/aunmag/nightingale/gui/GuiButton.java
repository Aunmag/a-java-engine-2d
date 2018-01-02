package aunmag.nightingale.gui;

import aunmag.nightingale.input.Input;
import aunmag.nightingale.basics.BaseGrid;
import aunmag.nightingale.basics.BaseQuad;
import aunmag.nightingale.font.Font;
import aunmag.nightingale.utilities.UtilsGraphics;
import org.joml.Vector4f;
import org.lwjgl.glfw.GLFW;

public class GuiButton extends GuiLabel {

    private static final Vector4f colorDefault = new Vector4f(0.5f, 0.5f, 0.5f, 0.8f);
    private static final Vector4f colorTouched = new Vector4f(0.6f, 0.6f, 0.6f, 0.8f);
    private static final Vector4f colorFont = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);
    private static final Vector4f colorFontLight = new Vector4f(0.8f, 0.8f, 0.8f, 0.5f);

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
                Input.mouse.getX(),
                Input.mouse.getY()
        );
        isPressed = isTouched && Input.mouse.isButtonReleased(GLFW.GLFW_MOUSE_BUTTON_1);
    }

    public void render() {
        Vector4f color = isTouched ? colorTouched : colorDefault;
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
            setTextColour(colorFont);
        } else {
            isTouched = false;
            isPressed = false;
            setTextColour(colorFontLight);
        }
    }

    /* Getters */

    public boolean isPressed() {
        return isPressed;
    }

}
