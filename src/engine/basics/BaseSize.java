package engine.basics;

public class BaseSize {

    protected int width;
    protected int height;
    private float centerX;
    private float centerY;

    public BaseSize(int size) {
        setSize(size);
    }

    public BaseSize(int width, int height) {
        setSize(width, height);
    }

    /* Setters */

    protected void setSize(int size) {
        this.width = size;
        this.height = size;

        float center = size / 2f;
        centerX = center;
        centerY = center;
    }

    protected void setSize(int width, int height) {
        this.width = width;
        this.height = height;

        centerX = width / 2f;
        centerY = height / 2f;
    }

    /* Getters */

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public float getCenterX() {
        return centerX;
    }

    public float getCenterY() {
        return centerY;
    }

}
