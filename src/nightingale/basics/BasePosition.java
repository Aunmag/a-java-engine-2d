package nightingale.basics;

import nightingale.utilities.UtilsMath;

public class BasePosition extends BasePoint {

    private float radians = 0;

    public BasePosition(float x, float y, float radians) {
        super(x, y);
        this.radians = radians;
    }

    public final void addRadiansCarefully(float radians) {
        setRadians(UtilsMath.correctRadians(radians + this.radians));
    }

    /* Setters */

    public void setRadians(float radians) {
        this.radians = radians;
    }

    /* Getters */

    public float getRadians() {
        return radians;
    }

}
