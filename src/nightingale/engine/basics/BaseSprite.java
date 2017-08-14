package nightingale.engine.basics;

import nightingale.engine.Application;
import nightingale.engine.rendering.Texture;
import org.joml.Matrix4f;

public abstract class BaseSprite extends BasePosition {

    protected boolean isValid = true;
    private Texture texture;

    public BaseSprite(float x, float y, float radians, Texture texture) {
        super(x, y, radians);
        this.texture = texture;
    }

    protected void render() {
        texture.bind(0);

        Matrix4f transformation = new Matrix4f();
        transformation.translate(x, y, 0);
        transformation.rotateZ(radians);

        Matrix4f projection = Application.getCamera().getViewMatrixCopy();
        projection.mul(transformation);

        Application.getShader().setUniformSampler(0);
        Application.getShader().setUniformProjection(projection);

        texture.render();
    }

    protected abstract void update();

    protected abstract void delete();

}
