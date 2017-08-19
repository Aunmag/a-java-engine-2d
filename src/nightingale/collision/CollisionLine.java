package nightingale.collision;

import org.joml.Vector2f;

public class CollisionLine extends Collision {

    private Vector2f positionTail;

    public CollisionLine(Vector2f a, Vector2f b) {
        super(a);
        positionTail = new Vector2f(b);
    }

    public void render() {}

    /* Getters */

    public Vector2f getPositionTail() {
        return positionTail;
    }

    /* Setters */

    public void setPosition(float x1, float y1, float x2, float y2) {
        x = x1;
        y = y1;
        positionTail.x = x2;
        positionTail.y = y2;
    }

}
