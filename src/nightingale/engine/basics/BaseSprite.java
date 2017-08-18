package nightingale.engine.basics;

import nightingale.engine.Application;
import nightingale.engine.structures.Texture;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseSprite extends BasePosition {

    private static final int LAYER_UNDEFINED = 0;
    private static final int LAYER_MAXIMAL = 8;
    private static Map<Integer, List<BaseSprite>> all = new HashMap<>();

    public static void updateAll() {
        for (Integer layer: all.keySet()) {
            for (BaseSprite sprite: all.get(layer)) {
                sprite.update();
            }
        }
    }

    public static void renderAll() {
        for (Integer layer: all.keySet()) {
            for (BaseSprite sprite: all.get(layer)) {
                sprite.render();
            }
        }
    }

    public static void renderLayer(int layer) {
        validateLayer(layer);

        List<BaseSprite> sprites = all.get(layer);

        if (sprites != null) {
            for (BaseSprite sprite: all.get(layer)) {
                sprite.render();
            }
        }
    }

    private static void validateLayer(int layer) {
        if (layer <= LAYER_UNDEFINED) {
            throw new IllegalStateException(
                    String.format(
                            "Layer must be grater than %s. Got %s instead!",
                            LAYER_UNDEFINED,
                            layer
                    )
            );
        }

        if (LAYER_MAXIMAL < layer) {
            throw new IllegalStateException(
                    String.format(
                            "Layer must be equal or smaller than %s. Got %s instead!",
                            LAYER_MAXIMAL,
                            layer
                    )
            );
        }
    }

    protected boolean isValid = true;
    private Texture texture;
    private int layer = LAYER_UNDEFINED;

    public BaseSprite(float x, float y, float radians, Texture texture) {
        super(x, y, radians);
        this.texture = texture;
    }

    public void addToLayer(int layer) {
        if (this.layer != LAYER_UNDEFINED) {
            throw new IllegalStateException(
                    String.format("This sprite is already on %s layer!", this.layer)
            );
        }

        validateLayer(layer);

        List<BaseSprite> sprites = all.get(layer);

        if (sprites == null) {
            sprites = new ArrayList<>();
            all.put(layer, sprites);
        }

        sprites.add(this);
        this.layer = layer;
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

    public void delete() {
        if (layer == 0) {
            return;
        }

        List<BaseSprite> sprites = all.get(layer);
        sprites.remove(this);

        if (sprites.isEmpty()) {
            all.remove(layer);
        }
    }

}
