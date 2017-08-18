package nightingale.font;

import java.util.ArrayList;
import java.util.List;

class Line {

    private List<Word> words = new ArrayList<>();
    private float width = 0;
    private final float widthSpace;

    Line(float widthSpace) {
        this.widthSpace = widthSpace;
    }

    void addWord(Word word) {
        width += calculateWordWidthInsideLine(word);
        words.add(word);
    }

    float calculateWidthWithWord(Word word) {
        float wordWidthInsideLine = calculateWordWidthInsideLine(word);
        return width + wordWidthInsideLine;
    }

    private float calculateWordWidthInsideLine(Word word) {
        if (words.isEmpty()) {
            return word.getWidth();
        } else {
            return word.getWidth() + widthSpace;
        }
    }

    /* Getters */

    List<Word> getWordsCopy() {
        return new ArrayList<>(words);
    }

    float getWidth() {
        return width;
    }

}
