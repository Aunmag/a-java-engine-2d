package nightingale.engine.font;

import nightingale.engine.Application;
import nightingale.engine.shaders.ShaderFont;
import nightingale.engine.structures.Vao;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Text extends Vector2f {

    /* Static */

    private static Map<Font, List<Text>> all = new HashMap<>();
    private final static ShaderFont shader = new ShaderFont();

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

    public static void terminate() {
        shader.cleanUp();
    }

    /* Dynamic */

    final String message;
    final float fontSize;
    final float spaceWidth;
    final float lineWidthMax;
    final Font font;
    final boolean isCentered;
    final Vao vao;
    private Vector4f colour = new Vector4f(1f, 1f, 1f, 1f);
    private Matrix4f projection;

    public Text(
            float x,
            float y,
            String message,
            float fontSize,
            Font font,
            float lineWidthMax,
            boolean isCentered
    ) {
        super(x, y);
        this.message = message;
        this.fontSize = fontSize;
        this.font = font;
        this.lineWidthMax = lineWidthMax;
        this.isCentered = isCentered;
        spaceWidth = font.spaceWidth * fontSize;
        vao = font.createTextVao(this);

        projection = new Matrix4f().translate(
                x / Application.getWindow().getWidth(),
                y / -Application.getWindow().getHeight(),
                0
        );

        addToAll();
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

        GL30.glBindVertexArray(vao.id);
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        shader.setUniformSampler(0);
        shader.setUniformColour(colour);
        shader.setUniformProjection(projection);
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, vao.vertexCount);

        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL30.glBindVertexArray(0);
    }

    public void remove() {
        List<Text> fontTexts = all.get(font);
        fontTexts.remove(this);

        if (fontTexts.isEmpty()) {
            all.remove(font);
        }
    }

}
