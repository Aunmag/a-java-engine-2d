package nightingale.demo.sprites;

import nightingale.engine.basics.BaseSpriteLayer;
import nightingale.engine.basics.BaseSprite;
import nightingale.engine.structures.Texture;

public class Object extends BaseSprite {

    public static BaseSpriteLayer layer = new BaseSpriteLayer(false);

    public Object(float x, float y, float radians, Texture texture) {
        super(x, y, radians, texture);
    }

    public void update() {}

}
