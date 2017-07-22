package engine.basics;

import engine.utilities.UtilsMath;
import org.joml.Vector3f;

public class BasePositionDirected extends BasePosition {

    protected float radians = 0;

    public BasePositionDirected(Vector3f position, float radians) {
        super(position);
        this.radians = radians;
    }

    /* Setters */

    protected void setRadians(float radians) {
        this.radians = radians;
    }

    public void addRadians(float radians) {
        setRadians(UtilsMath.correctRadians(radians + this.radians));
    }

    /* Getters */

    public float getRadians() {
        return radians;
    }

}
