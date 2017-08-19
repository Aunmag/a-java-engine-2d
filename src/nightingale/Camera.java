package nightingale;

import nightingale.basics.BasePosition;
import nightingale.basics.BaseSprite;
import nightingale.utilities.UtilsMath;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Camera extends BasePosition {

    private Matrix4f viewMatrix = new Matrix4f();
    private float zoom = 2;
    private BaseSprite target;

    public Camera() {
        super(0, 0, 0);
    }

    public void update() {
        if (target != null) {
            setPosition(-target.getX(), -target.getY());
            setRadians(target.getRadians() - (float) UtilsMath.PIx0_5);
        }

        Vector2f viewPosition = new Vector2f(getX(), getY());
        viewPosition.mul(zoom);

        viewMatrix = Application.getWindow().getProjectionCopy();
        viewMatrix.rotateZ(-getRadians());
        viewMatrix.translate(viewPosition.x, viewPosition.y, 0);
        viewMatrix.scale(zoom);
    }

    public Vector2f calculateViewPosition(Vector2f position) {
        // TODO: Optimize
        Vector3f viewPosition = new Vector3f(position.x, position.y, 0);
        viewPosition.mulPosition(viewMatrix);
        return new Vector2f(viewPosition.x(), viewPosition.y());
    }

    /* Setters */

    public void setTarget(BaseSprite target) {
        this.target = target;
    }

    /* Getters */

    public Matrix4f getViewMatrixCopy() {
        return new Matrix4f(viewMatrix);
    }

}
