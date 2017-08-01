package engine.basics;

import engine.Application;
import engine.rendering.Texture;
import org.joml.Matrix4f;

public abstract class BaseSprite extends BasePosition {

    protected boolean isValid = true;
    private Texture texture;

    public BaseSprite(float x, float y, float z, float radians, Texture texture) {
        super(x, y, z, radians);
        this.texture = texture;
    }

    protected void render() {
        texture.bind(0);

        Matrix4f transformation = new Matrix4f();
        transformation.translate(this);
        transformation.rotateZ(radians);

        Matrix4f projection = Application.getCamera().getViewMatrixCopy();
        projection.mul(transformation);

        Application.getShader().setUniform("sampler", 0);
        Application.getShader().setUniform("projection", projection);

        texture.render();
    }

    protected abstract void update();

    protected abstract void delete();

}
