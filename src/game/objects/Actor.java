package game.objects;

import engine.basics.BaseSprite;
import engine.rendering.Texture;
import engine.utilities.UtilsMath;
import org.joml.Vector3f;

import java.util.ArrayList;

public class Actor extends BaseSprite {

    public static java.util.List<Actor> all = new ArrayList<>();

    private float velocity = 1.38f;

    public boolean isWalking = false;
    public boolean isWalkingForward = false;
    public boolean isWalkingBack = false;
    public boolean isWalkingLeft = false;
    public boolean isWalkingRight = false;
    public boolean isSprinting = false;
    public boolean isAttacking = false;

    public static void allUpdate() {
        for (Actor actor: Actor.all) {
            actor.update();
        }
    }

    public static void allRender() {
        for (Actor actor: Actor.all) {
            actor.render();
        }
    }

    public Actor(Vector3f position, float radians, Texture texture) {
        super(position, radians, texture);
    }

    protected void update() {
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

        position.x += movementVelocity * Math.cos(movementRadians);
        position.y += movementVelocity * Math.sin(movementRadians);
    }

    protected void delete() {}

}
