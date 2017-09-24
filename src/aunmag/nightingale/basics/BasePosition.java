package aunmag.nightingale.basics;

import aunmag.nightingale.utilities.UtilsMath;

public class BasePosition extends BasePoint {

    private float radians;
    private float cos;
    private float sin;

    public BasePosition(float x, float y, float radians) {
        super(x, y);
        setRadians(radians);
    }

    public final void addRadiansCarefully(float radians) {
        setRadians(UtilsMath.correctRadians(radians + this.radians));
    }

    /* Setters */

    public void setRadians(float radians) {
        if (radians == this.radians) {
            return;
        }

        this.radians = radians;
        cos = (float) Math.cos(radians);
        sin = (float) Math.sin(radians);
    }

    /* Getters */

    public float getRadians() {
        return radians;
    }

    public float getCos() {
        return cos;
    }

    public float getSin() {
        return sin;
    }

}
