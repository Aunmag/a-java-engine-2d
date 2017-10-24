package aunmag.nightingale.basics;

import aunmag.nightingale.Application;
import aunmag.nightingale.structures.Texture;
import aunmag.nightingale.utilities.UtilsMath;
import org.joml.Matrix4f;

public abstract class BaseSprite extends BasePosition implements BaseOperative {

    private boolean isRemoved = false;
    private Texture texture;

    public BaseSprite(float x, float y, float radians, Texture texture) {
        super(x, y, radians);
        this.texture = texture;
    }

    public void render() {
        Matrix4f projection = Application.getCamera().calculateViewProjection(
                getX(), getY(), getRadians()
        );

        float size = texture.getMaxSide() * 2 * Application.getCamera().getScaleFull();

        float rangeX = size / Application.getWindow().getWidth() + 1;
        if (!UtilsMath.calculateIsNumberInsideRange(projection.m30(), -rangeX, rangeX)) {
            return;
        }

        float rangeY = size / Application.getWindow().getHeight() + 1;
        if (!UtilsMath.calculateIsNumberInsideRange(projection.m31(), -rangeY, rangeY)) {
            return;
        }

        Application.getShader().setUniformSampler(0);
        Application.getShader().setUniformProjection(projection);
        Application.getShader().setUniformColourDefault();
        texture.bind();
        texture.render();
    }

    public abstract void update();

    public void remove() {
        isRemoved = true;
    }

    /* Getters */

    public boolean isRemoved() {
        return isRemoved;
    }

}
