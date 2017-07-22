package game.objects;

import engine.basics.BaseSprite;
import engine.rendering.Texture;
import org.joml.Vector3f;

import java.util.ArrayList;

public class Actor extends BaseSprite {

    public static java.util.List<Actor> all = new ArrayList<>();

    public static void allRender() {
        for (Actor actor: Actor.all) {
            actor.render();
        }
    }

    public Actor(Vector3f position, float radians, Texture texture) {
        super(position, radians, texture);
    }

    protected void update() {}

    protected void delete() {}

}
