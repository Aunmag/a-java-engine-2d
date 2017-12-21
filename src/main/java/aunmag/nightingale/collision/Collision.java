package aunmag.nightingale.collision;

import aunmag.nightingale.basics.BaseOperative;
import aunmag.nightingale.basics.BasePosition;
import aunmag.nightingale.utilities.UtilsGraphics;
import aunmag.nightingale.utilities.UtilsMath;
import org.joml.Vector4f;

public abstract class Collision extends BasePosition implements BaseOperative {

    private boolean isRemoved = false;
    public Vector4f color = new Vector4f(1f, 1f, 1f, 0.5f);

    Collision(float x, float y, float radians) {
        super(x, y, radians);
    }

    public void render() {
        UtilsGraphics.setDrawColor(color);
    }

    public void remove() {
        isRemoved = true;
    }

    /* Getters */

    public boolean isRemoved() {
        return isRemoved;
    }

    public static boolean calculateIsCollision(CollisionCircle a, CollisionCircle b) {
        float distanceBetween = UtilsMath.calculateDistanceBetween(a, b);
        a.distanceBetweenFromLastCheck = distanceBetween;
        b.distanceBetweenFromLastCheck = distanceBetween;
        float distanceToCollision = a.radius + b.radius;
        return distanceBetween < distanceToCollision;
    }

    public static boolean calculateIsCollision(
            CollisionCircle circle,
            CollisionLine line
    ) {
        float lineX1 = line.getX() - circle.getX();
        float lineY1 = line.getY() - circle.getY();
        float lineX2 = line.positionTail.getX() - circle.getX();
        float lineY2 = line.positionTail.getY() - circle.getY();

        float differenceX = lineX2 - lineX1;
        float differenceY = lineY2 - lineY1;

        float a = differenceX * differenceX + differenceY * differenceY;
        float b = (lineX1 * differenceX + lineY1 * differenceY) * 2f;
        float c = lineX1 * lineX1 + lineY1 * lineY1 - circle.radius * circle.radius;

        if (-b < 0) {
            return c < 0;
        } else if (-b < (a * 2f)) {
            return (a * c * 4f - b * b) < 0;
        } else {
            return (a + b + c) < 0;
        }
    }

}
