package aunmag.nightingale.math;

import aunmag.nightingale.utilities.UtilsGraphics;

public class BodyCircle extends Body {

    public float radius;

    public BodyCircle(float x, float y, float radians, float radius) {
        super(x, y, radians);
        this.radius = radius;
    }

    @Override
    public void render() {
        super.render();
        UtilsGraphics.drawCircle(position.x, position.y, radius, true, true);
    }

}
