package aunmag.nightingale.collision;

import aunmag.nightingale.utilities.UtilsGraphics;
import aunmag.nightingale.utilities.UtilsMath;

import java.awt.Color;

public class CollisionCircle extends Collision {

    public final float radius;
    float distanceBetweenFromLastCheck;

    public CollisionCircle(float x, float y, float radius) {
        super(x, y);
        this.radius = radius;
    }

    public void render() {
        render(renderColor);
    }

    public void render(Color color) {
        // TODO: Don't render if is invisible!
        UtilsGraphics.setDrawColor(color);
        UtilsGraphics.drawPrepare();
        UtilsGraphics.drawCircle(getX(), getY(), radius, true, true);
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

            float pushX = distancePush * (float) Math.cos(radiansBetween);
            float pushY = distancePush * (float) Math.sin(radiansBetween);

            addPosition(pushX, pushY);
            opponent.subPosition(pushX, pushY);
        }
    }

}
