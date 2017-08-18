package nightingale.basics;

import nightingale.utilities.UtilsMath;
import org.joml.Vector2f;

public class BasePosition extends Vector2f {

    public float radians = 0;

    public BasePosition(float x, float y, float radians) {
        super(x, y);
        this.radians = radians;
    }

    public void addRadiansCarefully(float radians) {
        this.radians = UtilsMath.correctRadians(radians + this.radians);
    }

}
