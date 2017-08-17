package nightingale.engine.basics;

import nightingale.engine.Application;
import nightingale.engine.utilities.UtilsGraphics;
import org.joml.Vector2f;
import org.lwjgl.opengl.GL11;

public class BaseGrid {

    private final int slices;
    private float stepX;
    private float stepY;

    public BaseGrid(int slices) {
        this.slices = slices;
        stepX = calculateStepX();
        stepY = calculateStepY();
    }

    private float calculateStepX() {
        return Application.getWindow().getWidth() / (float) slices;
    }

    private float calculateStepY() {
        return Application.getWindow().getHeight() / (float) slices;
    }

    public void render() {
        GL11.glColor4f(1f, 1f, 1f, 0.2f);

        for (float n = 0f; n < slices; n++) {
            UtilsGraphics.drawLine(
                    new Vector2f(stepX * n, 0),
                    new Vector2f(stepX * n, Application.getWindow().height),
                    false
            );

            UtilsGraphics.drawLine(
                    new Vector2f(0, stepY * n),
                    new Vector2f(Application.getWindow().width, stepY * n),
                    false
            );
        }
    }

    /* Getters */

    public float getStepX() {
        return stepX;
    }

    public float getStepY() {
        return stepY;
    }

}
