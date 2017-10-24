package aunmag.nightingale;

import aunmag.nightingale.audio.AudioMaster;
import aunmag.nightingale.basics.BasePosition;
import aunmag.nightingale.utilities.UtilsMath;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector2f;
import org.joml.Vector2fc;
import org.joml.Vector3f;

public class Camera extends BasePosition {

    public static final int ZOOM_MIN = 1;
    public static final int ZOOM_MAX = 10;
    public static final int DISTANCE_VIEW_MIN = 1;

    private Matrix4f viewMatrix = new Matrix4f();
    private float distanceView = 1280;
    private float zoom = 2;
    private float zoomView = 1;
    private float radiansOffset = 0;
    private Vector2f offset = new Vector2f(0, 0);

    Camera() {
        super(0, 0, 0);
    }

    public void update() {
        updateZoomView();

        Quaternionf quaternion = new Quaternionf(0, 0, 0);

        Vector3f offset = new Vector3f(this.offset.x(), this.offset.y(), 0);
        quaternion.rotateZ(radiansOffset);
        offset.rotate(quaternion);
        quaternion.set(0, 0, 0);

        float radians = UtilsMath.correctRadians(getRadians() + radiansOffset);
        float x = getX() + offset.x();
        float y = getY() + offset.y();

        viewMatrix = new Matrix4f(Application.getWindow().projection);
        viewMatrix.rotateZ(-radians);
        viewMatrix.translate(-x * zoomView, -y * zoomView, 0);
        viewMatrix.scale(zoomView);

        Vector3f orientation = new Vector3f(0, 1, 0);
        quaternion.set(0, 0, 0);
        quaternion.rotateZ(radians);
        orientation.rotate(quaternion);
        AudioMaster.setListenerPosition(getX(), getY(), 0, orientation);
    }

    private void updateZoomView() {
        zoomView = zoom * (Application.getWindow().getMaxSide() / distanceView);
    }

    public Vector2f calculateViewPosition(float x, float y) {
        Vector3f viewPosition = new Vector3f(x, y, 0);
        viewPosition.mulPosition(viewMatrix);
        return new Vector2f(viewPosition.x, viewPosition.y);
    }

    public Matrix4f calculateViewProjection(float x, float y, float radians) {
        Matrix4f projection = new Matrix4f(viewMatrix);
        projection.translate(x, y, 0);
        projection.rotateZ(radians);
        return projection;
    }

    void resetTemporaryVariables() {
        offset.set(0, 0);
        radiansOffset = 0;
    }

    /* Setters */

    public void addOffset(float radians, float distance, boolean useZoom) {
        if (useZoom) {
            distance /= zoomView;
        }

//        radians += getRadians() + UtilsMath.PIx0_5 +
        radians = UtilsMath.correctRadians(radians + getRadians() + UtilsMath.PIx0_5);
        float x = distance * (float) Math.cos(radians);
        float y = distance * (float) Math.sin(radians);
        offset.add(x, y);
    }

    public void setDistanceView(float distanceView) {
        if (distanceView < DISTANCE_VIEW_MIN) {
            distanceView = DISTANCE_VIEW_MIN;
        }

        this.distanceView = distanceView;
    }

    public void setZoom(float zoom) {
        if (zoom < ZOOM_MIN) {
            zoom = ZOOM_MIN;
        } else if (zoom > ZOOM_MAX) {
            zoom = ZOOM_MAX;
        }

        this.zoom = zoom;
    }

    public void addRadiansTemporary(float radiansOffset) {
        this.radiansOffset += radiansOffset;
    }

    /* Getters */

    public float getDistanceView() {
        return distanceView;
    }

    public float getZoom() {
        return zoom;
    }

    public float getZoomView() {
        return zoomView;
    }

    public Vector2fc getOffset() {
        return offset.toImmutable();
    }

    public float getRadiansOffset() {
        return radiansOffset;
    }

}
