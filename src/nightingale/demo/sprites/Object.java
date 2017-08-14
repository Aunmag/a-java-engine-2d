package nightingale.demo.sprites;

import nightingale.engine.basics.BaseSprite;
import nightingale.engine.structures.Texture;

import java.util.ArrayList;
import java.util.List;

public class Object extends BaseSprite {

    public static List<Object> all = new ArrayList<>();

    public static void allRender() {
        for (Object object: Object.all) {
            object.render();
        }
    }

    public Object(float x, float y, float radians, Texture texture) {
        super(x, y, radians, texture);
    }

    protected void update() {}

    protected void delete() {}

}
