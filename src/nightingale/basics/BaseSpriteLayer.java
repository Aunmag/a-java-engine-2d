package nightingale.basics;

import java.util.ArrayList;
import java.util.List;

public class BaseSpriteLayer {

    private List<BaseSprite> sprites = new ArrayList<>();
    private List<BaseSprite> spritesToDelete = new ArrayList<>();

    public void add(BaseSprite sprite) {
        sprites.add(sprite);
    }

    public void update() {
        for (BaseSprite sprite: sprites) {
            sprite.update();
            if (sprite.isRemoved()) {
                spritesToDelete.add(sprite);
            }
        }
    }

    public void render() {
        for (BaseSprite sprite: sprites) {
            sprite.render();
        }
    }

    public void cleanUp() {
        sprites.removeAll(spritesToDelete);
        spritesToDelete.clear();
    }

    public void clear() {
        for (BaseSprite sprite: sprites) {
            sprite.remove();
        }

        sprites.clear();
        spritesToDelete.clear();
    }

}
