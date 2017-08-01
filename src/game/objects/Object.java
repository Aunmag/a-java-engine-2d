package game.objects;

import engine.basics.BaseSprite;
import engine.rendering.Texture;

import java.util.ArrayList;
import java.util.List;

public class Object extends BaseSprite {

    public static List<Object> all = new ArrayList<>();

    public static void allRender() {
        for (Object object : Object.all) {
            object.render();
        }
    }

    public Object(float x, float y, float z, float radians, Texture texture) {
        super(x, y, z, radians, texture);
    }

    protected void update() {}

    protected void delete() {}

}
