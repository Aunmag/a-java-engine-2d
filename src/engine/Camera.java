package engine;

import engine.basics.BasePosition;
import engine.utilities.UtilsMath;
import game.objects.Actor;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Camera extends BasePosition {

    private Matrix4f viewMatrix = new Matrix4f();
    private float zoom = 2;
    private Actor target;

    public Camera() {
        super(0, 0, 0, 0);
    }

    public void update() {
        if (target != null) {
            x = -target.x();
            y = -target.y();
            radians = target.getRadians() - (float) UtilsMath.PIx0_5;
        }

        Vector3f viewPosition = new Vector3f(x, y, z);
        viewPosition.mul(zoom);

        viewMatrix = Application.getWindow().getProjectionCopy();
        viewMatrix.rotate(-radians, UtilsMath.axisRotation);
        viewMatrix.translate(viewPosition);
        viewMatrix.scale(zoom);
    }

    public Vector2f calculateViewPosition(Vector2f position) {
        Vector3f viewPosition = new Vector3f(position.x(), position.y(), 0);
        viewPosition.mulPosition(viewMatrix);
        return new Vector2f(viewPosition.x(), viewPosition.y());
    }

    /* Setters */

    public void setTarget(Actor target) {
        this.target = target;
    }

    /* Getters */

    public Matrix4f getViewMatrixCopy() {
        return new Matrix4f(viewMatrix);
    }

}
