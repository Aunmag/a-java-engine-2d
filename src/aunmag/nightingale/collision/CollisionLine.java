package aunmag.nightingale.collision;

import aunmag.nightingale.basics.BasePoint;
import aunmag.nightingale.utilities.UtilsGraphics;

public class CollisionLine extends Collision {

    public final BasePoint positionTail;

    public CollisionLine(float x, float y) {
        super(x, y, 0f);
        positionTail = new BasePoint(x, y);
    }

    public void render() {
        super.render();
        UtilsGraphics.drawLine(
                getX(),
                getY(),
                positionTail.getX(),
                positionTail.getY(),
                true
        );
    }

    /* Setters */

    public void setPosition(float x, float y) {
        positionTail.setPosition(getX(), getY());
        super.setPosition(x, y);
    }

}
