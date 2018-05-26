package aunmag.nightingale.math;

public class CollisionCL extends Collision {

    public final BodyCircle circle;
    public final BodyLine line;

    public CollisionCL(BodyCircle circle, BodyLine line) {
        this.circle = circle;
        this.line = line;

        float lineX1 = line.position.x - circle.position.x;
        float lineY1 = line.position.y - circle.position.y;
        float lineX2 = line.positionTail.x - circle.position.x;
        float lineY2 = line.positionTail.y - circle.position.y;

        float differenceX = lineX2 - lineX1;
        float differenceY = lineY2 - lineY1;
        float radiusSquare = circle.radius * circle.radius;

        float a = differenceX * differenceX + differenceY * differenceY;
        float b = (lineX1 * differenceX + lineY1 * differenceY) * 2f;
        float c = lineX1 * lineX1 + lineY1 * lineY1 - radiusSquare;

        if (b > 0) {
            isTrue = c < 0;
        } else if (-b < 2 * a) {
            isTrue = a * c * 4f - b * b < 0;
        } else {
            isTrue = a + b + c < 0;
        }
    }

}
