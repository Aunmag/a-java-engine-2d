package aunmag.nightingale;

import aunmag.nightingale.audio.AudioMaster;
import aunmag.nightingale.basics.BasePosition;
import aunmag.nightingale.basics.BaseSprite;
import aunmag.nightingale.utilities.UtilsMath;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Camera extends BasePosition {

    public static final int ZOOM_MIN = 1;
    public static final int ZOOM_MAX = 10;
    public static final int DISTANCE_VIEW_MIN = 1;

    private Matrix4f viewMatrix = new Matrix4f();
    private float distanceView = 1280;
    private float zoom = 2;
    private float zoomView = 1;
    private float offset = Application.getWindow().getCenterY() / 3f;
    private BaseSprite target;

    public Camera() {
        super(0, 0, 0);
    }

    public void update() {
        updateZoomView();

        if (target != null) {
            setPosition(-target.getX(), -target.getY());
            setRadians(target.getRadians() - (float) UtilsMath.PIx0_5);

            float offsetZoomed = offset / zoomView;
            float offsetX = offsetZoomed * target.getCos();
            float offsetY = offsetZoomed * target.getSin();
            addPosition(-offsetX, -offsetY);
        }

        Vector2f viewPosition = new Vector2f(getX(), getY());
        viewPosition.mul(zoomView);

        viewMatrix = Application.getWindow().getProjectionCopy();
        viewMatrix.rotateZ(-getRadians());
        viewMatrix.translate(viewPosition.x, viewPosition.y, 0);
        viewMatrix.scale(zoomView);

        Vector3f orientation = new Vector3f(0, 1, 0);
        Quaternionf quaternion = new Quaternionf(0, 0, 0);
        quaternion.rotateZ(getRadians());
        orientation.rotate(quaternion);
        AudioMaster.setListenerPosition(-getX(), -getY(), 1f / zoom, orientation);
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

    /* Setters */

    public void setTarget(BaseSprite target) {
        this.target = target;
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

}
