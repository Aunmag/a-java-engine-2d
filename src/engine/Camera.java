package engine;

import engine.basics.BasePositionDirected;
import engine.utilities.UtilsMath;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera extends BasePositionDirected {

    private Matrix4f viewMatrix = new Matrix4f();
    private float zoom = 64;

    public Camera() {
        super(new Vector3f(0, 0, 0), 0);
    }

    public void update() {
        viewMatrix = new Matrix4f();
        viewMatrix.identity();
        Application.getWindow().getProjection().translate(position, viewMatrix);
        viewMatrix.rotate(radians, UtilsMath.axisRotation, viewMatrix);
        viewMatrix.scale(zoom);
    }

    /* Getters */

    public Matrix4f getViewMatrix() {
        return new Matrix4f(viewMatrix);
    }

}
