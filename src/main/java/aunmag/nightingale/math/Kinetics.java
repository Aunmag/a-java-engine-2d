package aunmag.nightingale.math;

import org.joml.Vector2f;

public class Kinetics {

    public float weight;
    public final Vector2f velocity;
    public float velocityRadians = 0f;
    public float restitutionFactor = 0.2f;
    public float restrictionFactor = 4.0f;

    public Kinetics(float weight) {
        this(weight, 0, 0);
    }

    public Kinetics(float weight, float velocityX, float velocityY) {
        this.weight = weight;
        this.velocity = new Vector2f(velocityX, velocityY);
    }

    public void update(float timeDelta) {
        float restriction = restrictionFactor * timeDelta;
        velocity.mul(1 - restriction);
        velocityRadians *= 1 - 2 * restriction;
    }

    public void addEnergy(float x, float y, float timeDelta) {
        float restriction = restrictionFactor * timeDelta;
        velocity.add(x * restriction, y * restriction);
    }

    public static void interact(Kinetics a, Kinetics b) {
        Vector2f velocityDifference = new Vector2f()
                .add(a.velocity)
                .sub(b.velocity);

        if (velocityDifference.x == 0 && velocityDifference.y == 0) {
            return;
        }

        velocityDifference.normalize();

        float velocityDot = new Vector2f()
                .add(a.velocity)
                .sub(b.velocity)
                .dot(velocityDifference);

        if (velocityDot < 0) {
            return;
        }

        float massA = 1f / a.weight;
        float massB = 1f / b.weight;
        float restitution = 1 + Math.max(a.restitutionFactor, b.restitutionFactor);
        float joule = velocityDot * restitution / (massA + massB);

        Vector2f impulse = new Vector2f(velocityDifference).mul(joule);
        a.velocity.sub(new Vector2f(impulse).mul(massA));
        b.velocity.add(new Vector2f(impulse).mul(massB));
    }

}
