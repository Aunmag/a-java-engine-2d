package nightingale.engine.basics;

import nightingale.engine.Application;
import org.joml.Vector2f;

public class BaseQuad extends BaseSize {

    private Vector2f pointA;
    private Vector2f pointB;
    private Vector2f pointC;
    private Vector2f pointD;
    private Vector2f pointDisplayA;
    private Vector2f pointDisplayB;
    private Vector2f pointDisplayC;
    private Vector2f pointDisplayD;

    public BaseQuad(Vector2f position, float width, float height) {
        super(width, height);
        setPosition(position);
    }

    /* Setters */

    public void setPosition(Vector2f position) {
        pointA = new Vector2f(position);
        pointB = new Vector2f(position.x + width, position.y);
        pointC = new Vector2f(position.x + width, position.y + height);
        pointD = new Vector2f(position.x, position.y + height);
        pointDisplayA = Application.getWindow().calculateDisplayPosition(pointA);
        pointDisplayB = Application.getWindow().calculateDisplayPosition(pointB);
        pointDisplayC = Application.getWindow().calculateDisplayPosition(pointC);
        pointDisplayD = Application.getWindow().calculateDisplayPosition(pointD);
    }

    /* Getters */

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

    public Vector2f getPointDisplayA() {
        return pointDisplayA;
    }

    public Vector2f getPointDisplayB() {
        return pointDisplayB;
    }

    public Vector2f getPointDisplayC() {
        return pointDisplayC;
    }

    public Vector2f getPointDisplayD() {
        return pointDisplayD;
    }

}
