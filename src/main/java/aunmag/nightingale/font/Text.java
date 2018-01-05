package aunmag.nightingale.font;

import aunmag.nightingale.Application;
import aunmag.nightingale.basics.BaseQuad;
import com.sun.istack.internal.Nullable;
import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public class Text extends BaseQuad {

    public static final TextManager manager = new TextManager();

    final FontStyle style;
    private final float widthRatio;
    private TextVao vao = null;
    private Vector4f colour = new Vector4f(1f, 1f, 1f, 1f);
    private Matrix4f projection;
    private boolean isRemoved = false;
    private boolean isRenderingOrdered = false;

    public Text(float x, float y, float width, String message, FontStyle style) {
        super(
                x,
                y,
                width,
                Application.getWindow().getHeight() * style.lineHeight
        );
        this.style = style;
        this.widthRatio = width / Application.getWindow().getWidth();

        load(message);
        projection = calculateProjection();

        manager.add(this);
    }

    public void load(String message) {
        if (message.equals(getMessage())) {
            return;
        }

        removeVao();
        vao = new TextVao(message, style, widthRatio);
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

    public void orderRendering() {
        isRenderingOrdered = true;
    }

    void render() {
        if (!isRenderingOrdered || isRemoved) {
            return;
        }

        vao.bind();

        Application.getShader().setUniformColour(colour);
        Application.getShader().setUniformProjection(projection);

        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, vao.vertexCount);
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);

        isRenderingOrdered = false;
    }

    public void remove() {
        if (isRemoved) {
            return;
        }

        removeVao();

        isRemoved = true;
    }

    private void removeVao() {
        if (vao != null) {
            vao.remove();
            vao = null;
        }
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

    public void setColour(Vector4f colour) {
        this.colour = colour;
    }

    /* Getters */

    public boolean isRemoved() {
        return isRemoved;
    }

    @Nullable
    public String getMessage() {
        if (vao == null) {
            return null;
        } else {
            return vao.message;
        }
    }

}
