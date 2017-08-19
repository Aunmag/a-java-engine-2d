package nightingale.demo.sprites;

import nightingale.basics.BaseSprite;
import nightingale.structures.Texture;
import nightingale.utilities.UtilsMath;

import java.util.ArrayList;
import java.util.List;

public class Actor extends BaseSprite {

    public static List<Actor> all = new ArrayList<>();

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
            move(getRadians(), velocity);
        }

        if (isWalkingBack) {
            move(getRadians() - (float) Math.PI, velocity);
        }

        if (isWalkingLeft) {
            move(getRadians() + (float) UtilsMath.PIx0_5, velocity);
        }

        if (isWalkingRight) {
            move(getRadians() - (float) UtilsMath.PIx0_5, velocity);
        }
    }

    private void move(float movementRadians, float movementVelocity) {
        if (isSprinting && isWalkingForward) {
            movementVelocity *= 2;
        }

        addPosition(
                movementVelocity * (float) Math.cos(movementRadians),
                movementVelocity * (float) Math.sin(movementRadians)
        );
    }

}
