package nightingale.engine.basics;

import nightingale.engine.utilities.UtilsMath;
import org.joml.Vector3f;

public class BasePosition extends Vector3f {

    public float radians = 0;

    public BasePosition(float x, float y, float z, float radians) {
        super(x, y, z);
        this.radians = radians;
    }

    public void addRadiansCarefully(float radians) {
        this.radians = UtilsMath.correctRadians(radians + this.radians);
    }

}
