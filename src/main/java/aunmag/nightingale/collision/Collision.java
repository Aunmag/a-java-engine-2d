package aunmag.nightingale.collision;

import aunmag.nightingale.basics.BaseObject;
import aunmag.nightingale.basics.BaseOperative;
import aunmag.nightingale.utilities.UtilsGraphics;
import org.joml.Vector2f;
import org.joml.Vector4f;

public abstract class Collision extends BaseObject implements BaseOperative {

    private boolean isRemoved = false;
    public Vector4f color = new Vector4f(1f, 1f, 1f, 0.5f);

    Collision(Vector2f position, float radians) {
        super(position, radians);
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
        float distanceBetween = a.getPosition().distance(b.getPosition());
        a.distanceBetweenFromLastCheck = distanceBetween;
        b.distanceBetweenFromLastCheck = distanceBetween;
        float distanceToCollision = a.getRadius() + b.getRadius();
        return distanceBetween < distanceToCollision;
    }

    public static boolean calculateIsCollision(
            CollisionCircle circle,
            CollisionLine line
    ) {
        float lineX1 = line.getPosition().x() - circle.getPosition().x();
        float lineY1 = line.getPosition().y() - circle.getPosition().y();
        float lineX2 = line.getPositionTail().x() - circle.getPosition().x();
        float lineY2 = line.getPositionTail().y() - circle.getPosition().y();

        float differenceX = lineX2 - lineX1;
        float differenceY = lineY2 - lineY1;

        float a = differenceX * differenceX + differenceY * differenceY;
        float b = (lineX1 * differenceX + lineY1 * differenceY) * 2f;
        float c = lineX1 * lineX1 + lineY1 * lineY1 - circle.getRadius() * circle.getRadius();

        if (-b < 0) {
            return c < 0;
        } else if (-b < (a * 2f)) {
            return (a * c * 4f - b * b) < 0;
        } else {
            return (a + b + c) < 0;
        }
    }

}
