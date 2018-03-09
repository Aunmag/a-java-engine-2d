package aunmag.nightingale.collision;

import aunmag.nightingale.utilities.UtilsGraphics;
import org.joml.Vector2f;

public class CollisionLine extends Collision {

    private final Vector2f positionTail;

    public CollisionLine(Vector2f position) {
        super(position, 0f);
        positionTail = new Vector2f(position);
    }

    public void render() {
        super.render();
        UtilsGraphics.drawLine(
                getPosition().x(),
                getPosition().y(),
                positionTail.x(),
                positionTail.y(),
                true
        );
    }

    public void pullUpTail() {
        positionTail.set(getPosition());
    }

    /* Getters */

    public Vector2f getPositionTail() {
        return positionTail;
    }
}
