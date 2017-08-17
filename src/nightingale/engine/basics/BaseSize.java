package nightingale.engine.basics;

public class BaseSize {

    protected float width;
    protected float height;
    private float centerX;
    private float centerY;

    public BaseSize(float width, float height) {
        setSize(width, height);
    }

    /* Setters */

    protected void setSize(float width, float height) {
        this.width = width;
        this.height = height;

        centerX = width / 2f;
        centerY = height / 2f;
    }

    /* Getters */

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getCenterX() {
        return centerX;
    }

    public float getCenterY() {
        return centerY;
    }

}
