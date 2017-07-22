package engine.basics;

import org.joml.Vector3f;

public class BasePosition {

    protected Vector3f position;

    public BasePosition(Vector3f position) {
        this.position = position;
    }

    /* Setters */

    protected void setPosition(Vector3f position) {
        this.position = position;
    }

    protected void addPosition(Vector3f position) {
        this.position.add(position);
    }

    /* Getters */

    public Vector3f getPosition() {
        return position;
    }

    public float getX() {
        return position.x();
    }

    public float getY() {
        return position.y();
    }

    public float getZ() {
        return position.z();
    }

}
