package nightingale.basics;

import nightingale.utilities.UtilsMath;
import org.joml.Vector2f;

public class BaseQuad extends BaseSize {

    private Vector2f pointA;
    private Vector2f pointB;
    private Vector2f pointC;
    private Vector2f pointD;

    public BaseQuad(float x, float y, float width, float height) {
        super(width, height);
        setPosition(x, y);
    }

    public boolean calculateIsPointInside(float testX, float testY) {
        return UtilsMath.calculateIsNumberInsideRange(
                testX, getX(), getX() + width
        ) && UtilsMath.calculateIsNumberInsideRange(
                testY, getY(), getY() + height
        );
    }

    /* Setters */

    public void setPosition(float x, float y) {
        pointA = new Vector2f(x, y);
        pointB = new Vector2f(x + width, y);
        pointC = new Vector2f(x + width, y + height);
        pointD = new Vector2f(x, y + height);
    }

    public void setPositionCenteredBy(float x, float y) {
        setPosition(x - getCenterX(), y - getCenterY());
    }

    /* Getters */

    public float getX() {
        return pointA.x;
    }

    public float getY() {
        return pointA.y;
    }

    public Vector2f getPointA() {
        return pointA;
    }

    public Vector2f getPointB() {
        return pointB;
    }

    public Vector2f getPointC() {
        return pointC;
    }

    public Vector2f getPointD() {
        return pointD;
    }

}
