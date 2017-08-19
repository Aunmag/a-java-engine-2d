package nightingale.collision;

import nightingale.utilities.UtilsMath;
import org.joml.Vector2f;

import java.awt.Color;

public abstract class Collision extends Vector2f {

    static final Color renderColor = new Color(255, 255, 255, 100);

    Collision(Vector2f position) {
        super(position);
    }

    public abstract void render();

    static boolean calculateIsCollision(CollisionCircle a, CollisionCircle b) {
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
        float lineX1 = line.x - circle.x;
        float lineY1 = line.y - circle.y;
        float lineX2 = line.getPositionTail().x - circle.x;
        float lineY2 = line.getPositionTail().y - circle.y;

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