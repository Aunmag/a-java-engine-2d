package aunmag.nightingale.collision;

import aunmag.nightingale.Application;
import aunmag.nightingale.structures.Texture;
import aunmag.nightingale.utilities.UtilsGraphics;
import aunmag.nightingale.utilities.UtilsMath;
import org.joml.Matrix4f;

public class CollisionCircle extends Collision {

    private float radius;
    float distanceBetweenFromLastCheck;

    public CollisionCircle(float x, float y, float radius) {
        super(x, y, 0f);
        this.radius = radius;
    }

    public void render() {
        super.render();
        UtilsGraphics.drawCircle(getX(), getY(), radius, true, true);
    }

    public void renderTexture(Texture texture) {
        Matrix4f projection = Application.getCamera().calculateViewProjection(
                getX(),
                getY(),
                getRadians()
        );

        float size = texture.getMaxSide() * 2 * Application.getCamera().getScaleFull();

        float rangeX = size / Application.getWindow().getWidth() + 1;
        if (!UtilsMath.calculateIsNumberInsideRange(projection.m30(), -rangeX, rangeX)) {
            return;
        }

        float rangeY = size / Application.getWindow().getHeight() + 1;
        if (!UtilsMath.calculateIsNumberInsideRange(projection.m31(), -rangeY, rangeY)) {
            return;
        }

        Application.getShader().setUniformProjection(projection);
        Application.getShader().setUniformColourDefault();
        texture.bind();
        texture.render();
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

    /* Setters */

    public void setRadius(float radius) {
        this.radius = radius;
    }

    /* Getters */

    public float getRadius() {
        return radius;
    }

}
