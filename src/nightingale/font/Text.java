package nightingale.font;

import nightingale.Application;
import nightingale.basics.BaseQuad;
import nightingale.structures.Vao;
import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public class Text extends BaseQuad {

    public final String message;
    final float fontSize;
    final float spaceWidth;
    final float widthRatio;
    final boolean isCenteredX;
    final Vao vao;
    private Vector4f colour = new Vector4f(1f, 1f, 1f, 1f);
    private Matrix4f projection;

    public Text(
            float x,
            float y,
            float width,
            String message,
            float fontSize,
            Font font,
            boolean isCenteredX
    ) {
        super(
                x,
                y,
                width,
                Application.getWindow().getHeight() * Font.LINE_HEIGHT * fontSize
        );
        this.message = message;
        this.fontSize = fontSize;
        this.widthRatio = width / Application.getWindow().getWidth();
        this.isCenteredX = isCenteredX;

        spaceWidth = font.spaceWidth * fontSize;
        vao = font.createTextVao(this);
        projection = calculateProjection();
    }

    private Matrix4f calculateProjection() {
        Matrix4f projection = new Matrix4f();
        projection.translate(
                getX() / Application.getWindow().getWidth() * 2,
                getY() / -Application.getWindow().getHeight() * 2,
                0
        );
        return projection;
    }

    public void render() {
        vao.bind();

        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        Application.getShader().setUniformSampler(0);
        Application.getShader().setUniformColour(colour);
        Application.getShader().setUniformProjection(projection);
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, vao.vertexCount);

        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);

        vao.unbind();
    }

    public void delete() {
        vao.delete();
    }

    /* Setters */

    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        projection = calculateProjection();
    }

    public void setColour(float red, float green, float blue, float alpha) {
        colour.x = red;
        colour.y = green;
        colour.z = blue;
        colour.w = alpha;
    }

}
