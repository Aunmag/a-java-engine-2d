package game.objects;

import engine.Application;
import engine.basics.BaseSprite;
import engine.rendering.Texture;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class Object extends BaseSprite {

    public static List<Object> all = new ArrayList<>();

    public static void allRender() {
        for (Object object : Object.all) {
            object.render();
        }
    }

    public Object(Vector3f position, float radians, Texture texture) {
        super(position, radians, texture);
    }

    protected void update() {}

    protected void delete() {}

}
