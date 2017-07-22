package engine.basics;

import engine.Application;
import engine.rendering.Model;
import engine.rendering.Texture;
import engine.utilities.UtilsMath;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public abstract class BaseSprite extends BasePositionDirected {

    protected static final Model model;
    protected boolean isValid = true;
    protected Texture texture;

    static {
        float[] vertices = new float[] {
                -1f, 1f, 0,
                1f, 1f, 0,
                1f, -1f, 0,
                -1f, -1f, 0,
        };

        float[] texture = new float[] {
                0, 0,
                1, 0,
                1, 1,
                0, 1,
        };

        int[] indices = new int[] {
                0, 1, 2,
                2, 3, 0
        };

        model = new Model(vertices, texture, indices);
    }

    public BaseSprite(Vector3f position, float radians, Texture texture) {
        super(position, radians);
        this.texture = texture;
    }

    protected void render() {
        texture.bind(0);

        Matrix4f transformation = UtilsMath.createTransformationMatrix(this);

        Matrix4f view = Application.getCamera().getViewMatrix();
        view.mul(transformation);

        Application.getShader().setUniform("sampler", 0);
        Application.getShader().setUniform("projection", view);

        model.render();
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
