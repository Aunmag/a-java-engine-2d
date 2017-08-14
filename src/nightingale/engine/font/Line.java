package nightingale.engine.font;

import java.util.ArrayList;
import java.util.List;

class Line {

    private List<Word> words = new ArrayList<>();
    private float width = 0;
    private final float widthMax;
    private final float widthSpace;

    Line(float widthMax, float widthSpace) {
        this.widthMax = widthMax;
        this.widthSpace = widthSpace;
    }

    void addWord(Word word) {
        if (!calculateIsWordFit(word)) {
            throw new IllegalStateException("Word is too wide to fit line!");
        }

        width += calculateWordWidthInsideLine(word);
        words.add(word);
    }

    boolean calculateIsWordFit(Word word) {
        float wordWidthInsideLine = calculateWordWidthInsideLine(word);
        return width + wordWidthInsideLine <= widthMax;
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
