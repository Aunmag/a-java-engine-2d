package engine;

import engine.basics.BasePositionDirected;
import engine.utilities.UtilsMath;
import game.objects.Actor;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera extends BasePositionDirected {

    private Matrix4f viewMatrix = new Matrix4f();
    private float zoom = 64;
    private Actor target;

    public Camera() {
        super(new Vector3f(0, 0, 0), 0);
    }

    public void update() {
        if (target != null) {
            position = target.getPositionCopy().negate();
            radians = target.getRadians() - (float) UtilsMath.PI_0_5;
        }

        Vector3f viewPosition = new Vector3f(position);
        viewPosition.mul(zoom);

        viewMatrix = Application.getWindow().getProjectionCopy();
        viewMatrix.rotate(-radians, UtilsMath.axisRotation);
        viewMatrix.translate(viewPosition);
        viewMatrix.scale(zoom);
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
