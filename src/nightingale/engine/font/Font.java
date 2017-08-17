package nightingale.engine.font;

import nightingale.engine.structures.Texture;
import nightingale.engine.structures.Vao;
import nightingale.engine.utilities.UtilsLanguage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Font {

    public static final float LINE_HEIGHT = 0.03f;
    static final int SPACE_ASCII = 32;

    final Texture texture;
    private final Map<Integer, Character> characters;
    final float spaceWidth;

    Font(Texture texture, Map<Integer, Character> characters, float spaceWidth) {
        this.texture = texture;
        this.characters = characters;
        this.spaceWidth = spaceWidth;
    }

    Vao createTextVao(Text text) {
        List<Line> lines = createTextLines(text);
        List<Float> vertices = new ArrayList<>();
        List<Float> textureCoordinates = new ArrayList<>();

        float cursorX = 0f;
        float cursorY = 0f;

        for (Line line: lines) {
            if (text.isCenteredX) {
                cursorX = (text.widthRatio - line.getWidth()) / 2f;
            }

            List<Word> words = line.getWordsCopy();
            for (Word word: words) {
                List<Character> characters = word.getCharactersCopy();
                for (Character character: characters) {
                    addCharacterVertices(
                            cursorX,
                            cursorY,
                            character,
                            text.fontSize,
                            vertices
                    );
                    textureCoordinates.addAll(
                            Arrays.asList(character.textureCoordinates)
                    );
                    cursorX += character.offsetAdvanceX * text.fontSize;
                }
                cursorX += text.spaceWidth;
            }
            cursorX = 0;
            cursorY += LINE_HEIGHT * text.fontSize;
        }

        return new Vao(
                UtilsLanguage.convertListToArray(vertices),
                UtilsLanguage.convertListToArray(textureCoordinates)
        );
    }

    private List<Line> createTextLines(Text text) {
        List<Line> lines = new ArrayList<>();
        Word word = new Word(text.fontSize);
        Line line = new Line(text.spaceWidth);

        for (int i = 0; i < text.message.length(); i++) {
            int ascii = text.message.charAt(i);
            int iNext = i + 1;
            boolean isEndText = text.message.length() == iNext;
            boolean isEndWord = isEndText || SPACE_ASCII == text.message.charAt(iNext);

            if (ascii != SPACE_ASCII) {
                Character character = characters.get(ascii);
                word.addCharacter(character);
            }

            if (isEndWord) {
                if (line.calculateWidthWithWord(word) > text.widthRatio) {
                    lines.add(line);
                    line = new Line(text.spaceWidth);
                }
                line.addWord(word);
                word = new Word(text.fontSize);
            }
        }

        lines.add(line);
        return lines;
    }

    private void addCharacterVertices(
            float cursorX,
            float cursorY,
            Character character,
            float fontSize,
            List<Float> vertices
    ) {
        float aX = cursorX + (character.offsetX * fontSize);
        float aY = cursorY + (character.offsetY * fontSize);
        float bX = aX + (character.sizeX * fontSize);
        float bY = aY + (character.sizeY * fontSize);

        aX = (aX * 2) - 1;
        aY = (aY * -2) + 1;
        bX = (bX * 2) - 1;
        bY = (bY * -2) + 1;

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

}
