package aunmag.nightingale.gui;

import aunmag.nightingale.basics.BaseGrid;
import aunmag.nightingale.basics.BaseQuad;
import aunmag.nightingale.font.FontStyleDefault;
import aunmag.nightingale.input.Input;
import aunmag.nightingale.utilities.UtilsGraphics;
import com.sun.istack.internal.Nullable;
import org.joml.Vector4f;
import org.lwjgl.glfw.GLFW;

public class GuiButton extends GuiLabel {

    public static final Runnable actionBack = GuiManager::back;
    private static final Vector4f colorDefault = new Vector4f(0.5f, 0.5f, 0.5f, 0.8f);
    private static final Vector4f colorTouched = new Vector4f(0.6f, 0.6f, 0.6f, 0.8f);
    private static final Vector4f colorFont = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);
    private static final Vector4f colorFontLight = new Vector4f(0.8f, 0.8f, 0.8f, 0.5f);

    private BaseQuad onScreenQuad;
    private boolean isAvailable = true;
    private boolean isTouched = false;
    private boolean isPressed = false;
    @Nullable private Runnable action;

    public GuiButton(
            int x,
            int y,
            int width,
            int height,
            String text,
            @Nullable Runnable action
    ) {
        this(BaseGrid.grid12, x, y, width, height, text, action);
    }

    public GuiButton(
            BaseGrid grid,
            int x,
            int y,
            int width,
            int height,
            String text,
            @Nullable Runnable action
    ) {
        super(grid, x, y, width, height, text, FontStyleDefault.label);
        this.action = action;
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

        if (isPressed && action != null) {
            try {
                action.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
