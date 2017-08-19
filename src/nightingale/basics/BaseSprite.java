package nightingale.basics;

import nightingale.Application;
import nightingale.structures.Texture;
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
        texture.bind();

        Matrix4f transformation = new Matrix4f();
        transformation.translate(x, y, 0);
        transformation.rotateZ(radians);

        Matrix4f projection = Application.getCamera().getViewMatrixCopy();
        projection.mul(transformation);

        Application.getShader().setUniformSampler(0);
        Application.getShader().setUniformProjection(projection);

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
