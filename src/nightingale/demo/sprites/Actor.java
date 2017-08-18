package nightingale.demo.sprites;

import nightingale.basics.BaseSpriteLayer;
import nightingale.basics.BaseSprite;
import nightingale.structures.Texture;
import nightingale.utilities.UtilsMath;

public class Actor extends BaseSprite {

    public static BaseSpriteLayer layer = new BaseSpriteLayer();

    private float velocity = 1.38f;
    public boolean isWalking = false;
    public boolean isWalkingForward = false;
    public boolean isWalkingBack = false;
    public boolean isWalkingLeft = false;
    public boolean isWalkingRight = false;
    public boolean isSprinting = false;

    public Actor(float x, float y, float radians, Texture texture) {
        super(x, y, radians, texture);
    }

    public void update() {
        updateIsWalking();

        if (isWalking) {
            walk();
        }
    }

    private void updateIsWalking() {
        isWalking = isWalkingForward || isWalkingLeft || isWalkingRight || isWalkingBack;
    }

    private void walk() {
        if (isWalkingForward) {
            move(radians, velocity);
        }

        if (isWalkingBack) {
            move(radians - (float) Math.PI, velocity);
        }

        if (isWalkingLeft) {
            move(radians + (float) UtilsMath.PIx0_5, velocity);
        }

        if (isWalkingRight) {
            move(radians - (float) UtilsMath.PIx0_5, velocity);
        }
    }

    private void move(float movementRadians, float movementVelocity) {
        if (isSprinting && isWalkingForward) {
            movementVelocity *= 2;
        }

        x += movementVelocity * Math.cos(movementRadians);
        y += movementVelocity * Math.sin(movementRadians);
    }

}
