package aunmag.nightingale.basics;

import aunmag.nightingale.utilities.UtilsMath;

public class BaseQuad extends BasePoint {

    private float width;
    private float height;
    private float centerX;
    private float centerY;
    private float aspectRatio;

    public BaseQuad(float x, float y, float width, float height) {
        super(x, y);
        setSize(width, height);
    }

    public BaseQuad(float width, float height) {
        super(0, 0);
        setSize(width, height);
    }

    public boolean calculateIsPointInside(float testX, float testY) {
        return UtilsMath.calculateIsNumberInsideRange(
                testX, getX(), getX() + width
        ) && UtilsMath.calculateIsNumberInsideRange(
                testY, getY(), getY() + height
        );
    }

    /* Setters */

    protected void setSize(float width, float height) {
        this.width = width;
        this.height = height;

        centerX = width / 2f;
        centerY = height / 2f;

        aspectRatio = width / height;
    }

    public void setPositionCenteredBy(float x, float y) {
        setPosition(x - getCenterX(), y - getCenterY());
    }

    /* Getters */

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getMaxSide() {
        return Float.max(width, height);
    }

    public float getCenterX() {
        return centerX;
    }

    public float getCenterY() {
        return centerY;
    }

    public float getAspectRatio() {
        return aspectRatio;
    }

}
