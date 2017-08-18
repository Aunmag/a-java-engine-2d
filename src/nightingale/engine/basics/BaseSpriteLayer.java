package nightingale.engine.basics;

import java.util.ArrayList;
import java.util.List;

public class BaseSpriteLayer {

    private List<BaseSprite> sprites = new ArrayList<>();
    private List<BaseSprite> spritesToDelete = new ArrayList<>();
    private final boolean isUpdatable;

    public BaseSpriteLayer(boolean isUpdatable) {
        this.isUpdatable = isUpdatable;
    }

    public void add(BaseSprite sprite) {
        sprites.add(sprite);
    }

    public void update() {
        if (!isUpdatable) {
            return;
        }

        for (BaseSprite sprite: sprites) {
            sprite.update();
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

}
