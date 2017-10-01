package aunmag.nightingale.basics;

import org.joml.Vector2f;
import org.joml.Vector2fc;

public class BasePoint {

    private Vector2f position;

    public BasePoint(float x, float y) {
        position = new Vector2f(x, y);
    }

    public final void addPosition(float x, float y) {
        setPosition(position.x + x, position.y + y);
    }

    public final void subPosition(float x, float y) {
        setPosition(position.x - x, position.y - y);
    }

    /* Setters */

    public void setX(float x) {
        setPosition(x, position.y);
    }

    public void setY(float y) {
        setPosition(position.x, y);
    }

    public void setPosition(float x, float y) {
        position.x = x;
        position.y = y;
    }

    /* Getters */

    public float getX() {
        return position.x();
    }

    public float getY() {
        return position.y();
    }

    public Vector2fc getPosition() {
        return position.toImmutable();
    }

}
