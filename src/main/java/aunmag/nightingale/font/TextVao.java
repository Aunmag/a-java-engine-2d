package aunmag.nightingale.font;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

final class TextVao {

    private boolean isRemoved = false;
    private final int id = GL30.glGenVertexArrays();
    private final int idVertices = GL15.glGenBuffers();
    private final int idTextureCoordinates = GL15.glGenBuffers();
    final int vertexCount;
    private final String message;
    private final FontStyle style;

    TextVao(String message, FontStyle style, float widthRatio) {
        this.message = message;
        this.style = style;

        List<Line> lines = createLines(widthRatio);
        List<Float> vertices = new ArrayList<>();
        List<Float> textureCoordinates = new ArrayList<>();

        float cursorX = 0f;
        float cursorY = 0f;

        for (Line line: lines) {
            if (style.isCentred) {
                cursorX = (widthRatio - line.getWidth()) / 2f;
            }

            List<Word> words = line.getWordsCopy();
            for (Word word: words) {
                List<Character> characters = word.getCharactersCopy();
                for (Character character: characters) {
                    addCharacterVertices(cursorX, cursorY, character, vertices);
                    textureCoordinates.addAll(
                            Arrays.asList(character.textureCoordinates)
                    );
                    cursorX += character.offsetAdvanceX * style.size;
                }
                cursorX += style.spaceWidth;
            }
            cursorX = 0;
            cursorY += style.lineHeight;
        }

        bind();
        storeDataInAttributeList(idVertices, 0, vertices);
        storeDataInAttributeList(idTextureCoordinates, 1, textureCoordinates);
        unbind();

        vertexCount = vertices.size() / 2;
    }

    private List<Line> createLines(float widthRatio) {
        List<Line> lines = new ArrayList<>();
        Word word = new Word(style.size);
        Line line = new Line(style.spaceWidth);

        for (int i = 0; i < message.length(); i++) {
            int ascii = message.charAt(i);
            int iNext = i + 1;
            boolean isEndText = message.length() == iNext;
            boolean isEndLine = isEndText || message.charAt(iNext) == '\n';
            boolean isEndWord = isEndLine || message.charAt(iNext) == ' ';

            if (ascii != ' ') {
                Character character = style.font.getCharacterByAscii(ascii);
                word.addCharacter(character);
            }

            if (isEndWord) {
                if (line.calculateWidthWithWord(word) > widthRatio) {
                    lines.add(line);
                    line = new Line(style.spaceWidth);
                }
                line.addWord(word);
                word = new Word(style.size);
            }

            if (isEndLine) {
                lines.add(line);
                line = new Line(style.spaceWidth);
            }
        }

        lines.add(line);
        return lines;
    }

    private void addCharacterVertices(
            float cursorX,
            float cursorY,
            Character character,
            List<Float> vertices
    ) {
        final float scale = 2.0f;
        final float size = style.size * scale;

        float aX = size * character.offsetX + scale * (cursorX - 0.5f);
        float aY = size * character.offsetY + scale * (cursorY - 0.5f);
        float bX = size * character.sizeX + aX;
        float bY = size * character.sizeY + aY;

        aY = -aY;
        bY = -bY;

        vertices.add(aX);
        vertices.add(aY);
        vertices.add(aX);
        vertices.add(bY);
        vertices.add(bX);
        vertices.add(bY);
        vertices.add(bX);
        vertices.add(bY);
        vertices.add(bX);
        vertices.add(aY);
        vertices.add(aX);
        vertices.add(aY);
    }

    private void storeDataInAttributeList(int id, int attributeNumber, List<Float> data) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.size());

        for (float value: data) {
            buffer.put(value);
        }

        buffer.flip();

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, id);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(attributeNumber, 2, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    final void bind() {
        if (isRemoved) {
            return;
        }

        GL30.glBindVertexArray(id);
    }

    static void unbind() {
        GL30.glBindVertexArray(0);
    }

    final void remove() {
        if (isRemoved) {
            return;
        }

        GL30.glDeleteVertexArrays(id);
        GL15.glDeleteBuffers(idVertices);
        GL15.glDeleteBuffers(idTextureCoordinates);

        isRemoved = true;
    }

    /* Getters */

    final boolean isRemoved() {
        return isRemoved;
    }

}
