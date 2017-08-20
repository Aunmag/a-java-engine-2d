package nightingale.basics;

public class BaseSize {

    protected float width;
    protected float height;
    private float centerX;
    private float centerY;
    private float aspectRatio;

    public BaseSize(float width, float height) {
        setSize(width, height);
    }

    /* Setters */

    protected void setSize(float width, float height) {
        this.width = width;
        this.height = height;

        centerX = width / 2f;
        centerY = height / 2f;

        aspectRatio = width / height;
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
