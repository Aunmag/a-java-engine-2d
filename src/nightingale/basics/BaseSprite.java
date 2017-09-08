package nightingale.basics;

import nightingale.Application;
import nightingale.structures.Texture;
import nightingale.utilities.UtilsMath;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseSprite extends BasePosition implements BaseOperative {

    public static void updateAll(List<? extends BaseSprite> sprites) {
        List<BaseSprite> spritesToDelete = new ArrayList<>();

        for (BaseSprite sprite: sprites) {
            sprite.update();
            if (sprite.isRemoved()) {
                spritesToDelete.add(sprite);
            }
        }

        for (BaseSprite sprite: spritesToDelete) {
            sprites.remove(sprite);
        }
    }

    public static void renderAll(List<? extends BaseSprite> sprites) {
        for (BaseSprite sprite: sprites) {
            sprite.render();
        }
    }

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

        float size = texture.getMaxSide() * 2 * Application.getCamera().getZoomView();

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
