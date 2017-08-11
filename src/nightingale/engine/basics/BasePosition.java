package nightingale.engine.basics;

import nightingale.engine.utilities.UtilsMath;
import org.joml.Vector2f;

public class BasePosition extends Vector2f {

    protected float radians = 0;

    public BasePosition(float x, float y, float radians) {
        super(x, y);
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
