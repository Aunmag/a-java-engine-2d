package aunmag.nightingale.font;

import aunmag.nightingale.Application;
import aunmag.nightingale.structures.Vao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class TextManager {

    private final Map<Font, List<Text>> texts = new HashMap<>();

    TextManager() {}

    void add(Text text) {
        List<Text> textsByFont = texts.get(text.font);
        if (textsByFont == null) {
            textsByFont = new ArrayList<>();
            texts.put(text.font, textsByFont);
        }

        textsByFont.add(text);
    }

    void remove(Text text) {
        List<Text> textsByFont = texts.get(text.font);
        if (textsByFont != null) {
            textsByFont.remove(text);
        }
    }

    public void renderAll() {
        Application.getShader().bind();

        for (Font font: texts.keySet()) {
            font.texture.bind();
            for (Text text: texts.get(font)) {
                if (text.isRenderingOrdered() && !text.isRemoved()) {
                    text.render();
                }
            }
        }

        Vao.unbind();
    }

}
