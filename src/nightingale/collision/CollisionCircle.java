package nightingale.collision;

import nightingale.utilities.UtilsGraphics;
import nightingale.utilities.UtilsMath;
import org.joml.Vector2f;

import java.awt.Color;

public class CollisionCircle extends Collision {

    final float radius;
    float distanceBetweenFromLastCheck;

    public CollisionCircle(Vector2f position, float radius) {
        super(position);
        this.radius = radius;
    }

    public void render() {
        render(renderColor);
    }

    public void render(Color color) {
        // TODO: Don't render if is invisible!
        UtilsGraphics.setDrawColor(color);
        UtilsGraphics.drawPrepare();
        UtilsGraphics.drawCircle(this, radius, true, true);
        UtilsGraphics.drawFinish();
    }

    public void preventCollisionWith(CollisionCircle opponent) {
        if (calculateIsCollision(this, opponent)) {
            float radiansBetween = UtilsMath.calculateRadiansBetween(this, opponent);
            float distanceBetween = distanceBetweenFromLastCheck;
            float distanceToCollision = radius + opponent.radius;
            float distanceIntersection = distanceToCollision - distanceBetween;
            float distancePush = distanceIntersection / 2f;
            float distancePushBuffer = distancePush * 0.01f;

            distancePush += distancePushBuffer;

            double pushX = distancePush * Math.cos(radiansBetween);
            double pushY = distancePush * Math.sin(radiansBetween);

            x += pushX;
            y += pushY;
            opponent.x -= pushX;
            opponent.y -= pushY;
        }
    }

}
