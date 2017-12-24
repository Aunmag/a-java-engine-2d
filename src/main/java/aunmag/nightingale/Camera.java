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
    public static final int ZOOM_MAX = 8;

    private Matrix4f viewMatrix = new Matrix4f();
    private float distanceView;
    private float scaleWindow;
    private float scaleZoom = ZOOM_MIN * 2;
    private float scaleFull;
    private float radiansOffset = 0;
    private Vector2f offset = new Vector2f(0, 0);

    Camera() {
        super(0, 0, 0);
        setDistanceView(40);
    }

    public void update() {
        float x = getX() + offset.x();
        float y = getY() + offset.y();
        float radians = UtilsMath.correctRadians(
                getRadians() + radiansOffset - UtilsMath.PIx0_5
        );

        viewMatrix = new Matrix4f(Application.getWindow().projection);
        viewMatrix.rotateZ(-radians);
        viewMatrix.scale(scaleFull);
        viewMatrix.translate(-x, -y, 0);

        Vector3f orientation = new Vector3f(0, 1, 0);
        Quaternionf quaternion = new Quaternionf(0, 0, 0);
        quaternion.set(0, 0, 0);
        quaternion.rotateZ(radians);
        orientation.rotate(quaternion);
        AudioMaster.setListenerPosition(getX(), getY(), 0, orientation);
    }

    private void updateScaleFull() {
        scaleFull = scaleWindow * scaleZoom;
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

    void resetOffsets() {
        offset.set(0, 0);
        radiansOffset = 0;
    }

    /* Setters */

    public void addOffset(float radians, float distance, boolean useZoom) {
        if (useZoom) {
            distance /= scaleFull;
        }

        radians = UtilsMath.correctRadians(radians + getRadians() + radiansOffset);
        float x = distance * (float) Math.cos(radians);
        float y = distance * (float) Math.sin(radians);
        offset.add(x, y);
    }

    public void setDistanceView(float distanceView) {
        this.distanceView = distanceView;
        scaleWindow = Application.getWindow().getDiagonal() / distanceView;
        updateScaleFull();
    }

    public void setScaleZoom(float scaleZoom) {
        this.scaleZoom = UtilsMath.limitNumber(scaleZoom, ZOOM_MIN, ZOOM_MAX);
        updateScaleFull();
    }

    public void addRadiansOffset(float radiansOffset) {
        this.radiansOffset += radiansOffset;
    }

    /* Getters */

    public float getDistanceView() {
        return distanceView;
    }

    public float getScaleWindow() {
        return scaleWindow;
    }

    public float getScaleZoom() {
        return scaleZoom;
    }

    public float getScaleFull() {
        return scaleFull;
    }

    public Vector2fc getOffset() {
        return offset.toImmutable();
    }

    public float getRadiansOffset() {
        return radiansOffset;
    }

}