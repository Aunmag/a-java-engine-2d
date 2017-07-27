package engine.basics;

import engine.Application;
import engine.rendering.Texture;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public abstract class BaseSprite extends BasePositionDirected {

    protected boolean isValid = true;
    protected Texture texture;

    public BaseSprite(Vector3f position, float radians, Texture texture) {
        super(position, radians);
        this.texture = texture;
    }

    protected void render() {
        texture.bind(0);

        Matrix4f transformation = createTransformationMatrix();
        Matrix4f view = Application.getCamera().getViewMatrixCopy();
        view.mul(transformation);

        Application.getShader().setUniform("sampler", 0);
        Application.getShader().setUniform("projection", view);

        texture.getModel().render();
    }

    private Matrix4f createTransformationMatrix() {
        Matrix4f matrix = new Matrix4f();
        matrix.translate(position);
        matrix.rotateZ(radians);
        return matrix;
    }

    protected abstract void update();

    protected abstract void delete();

    /* Getters */

    public boolean getIsValid() {
        return isValid;
    }

    public Texture getTexture() {
        return texture;
    }

}
