package nightingale.demo.sprites;

import nightingale.basics.BaseSpriteLayer;
import nightingale.basics.BaseSprite;
import nightingale.structures.Texture;

public class Object extends BaseSprite {

    public static BaseSpriteLayer layer = new BaseSpriteLayer();

    public Object(float x, float y, float radians, Texture texture) {
        super(x, y, radians, texture);
    }

    public void update() {}

}
