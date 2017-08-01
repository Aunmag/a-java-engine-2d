package engine.basics;

import engine.utilities.UtilsMath;
import org.joml.Vector3f;

public class BasePosition extends Vector3f {

    protected float radians = 0;

    public BasePosition(float x, float y, float z, float radians) {
        super(x, y, z);
        this.radians = radians;
    }

    /* Setters */

    public void setRadians(float radians) {
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
