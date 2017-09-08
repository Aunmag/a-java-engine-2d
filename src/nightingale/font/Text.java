package nightingale.font;

import nightingale.Application;
import nightingale.basics.BaseQuad;
import nightingale.shaders.ShaderFont;
import nightingale.structures.Vao;
import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Text extends BaseQuad {

    /* Static */

    private static Map<Font, List<Text>> all = new HashMap<>();
    private static final ShaderFont shader = new ShaderFont();

    public static void renderAll() {
        shader.bind();

        for (Font font: all.keySet()) {
            font.texture.bind();
            for (Text text: all.get(font)) {
                text.render();
            }
        }

        shader.unbind();
    }

    /* Dynamic */

    final String message;
    final float fontSize;
    final float spaceWidth;
    final float widthRatio;
    final Font font;
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
        this.font = font;
        this.widthRatio = width / Application.getWindow().getWidth();
        this.isCenteredX = isCenteredX;

        spaceWidth = font.spaceWidth * fontSize;
        vao = font.createTextVao(this);
        projection = calculateProjection();

        addToAll();
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

    private void addToAll() {
        List<Text> fontTexts = all.get(font);
        if (fontTexts == null) {
            fontTexts = new ArrayList<>();
            all.put(font, fontTexts);
        }

        fontTexts.add(this);
    }

    public void render() {
        // TODO: Optimize:
        shader.bind();
        font.texture.bind();
        vao.bind();

        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        shader.setUniformSampler(0);
        shader.setUniformColour(colour);
        shader.setUniformProjection(projection);
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, vao.vertexCount);

        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        vao.unbind();
    }

    public void remove() {
        List<Text> fontTexts = all.get(font);
        fontTexts.remove(this);

        if (fontTexts.isEmpty()) {
            all.remove(font);
        }
    }

    /* Setters */

    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        projection = calculateProjection();
    }

}
