package aunmag.nightingale.basics;

import aunmag.nightingale.utilities.UtilsMath;

public class BasePosition extends BasePoint {

    private float radians;

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
