package nightingale.collision;

import nightingale.basics.BasePoint;

public class CollisionLine extends Collision {

    private BasePoint positionTail;

    public CollisionLine(float x1, float y1, float x2, float y2) {
        super(x1, y1);
        positionTail = new BasePoint(x2, y2);
    }

    public void render() {}

    /* Getters */

    public BasePoint getPositionTail() {
        return positionTail;
    }

    /* Setters */

    public void setPosition(float x1, float y1, float x2, float y2) {
        super.setPosition(x1, y1);
        positionTail.setPosition(x2, y2);
    }

}
