package aunmag.nightingale.input;

import aunmag.nightingale.Application;
import org.lwjgl.glfw.GLFWScrollCallback;

public final class MouseWheel {

    public static final float VELOCITY_SMOOTH_MIN = 0.001f;
    public float smoothness = 8f;
    private float velocity = 0;
    private float velocitySmooth = 0;

    public final GLFWScrollCallback callbackUpdate = new GLFWScrollCallback() {
        @Override public void invoke(long win, double dx, double dy) {
            velocity = (float) dy;
            velocitySmooth += velocity;
        }
    };

    final void update() {
        velocity = 0;

        if (Math.abs(velocitySmooth) < VELOCITY_SMOOTH_MIN) {
            velocitySmooth = 0;
        } else {
            float delta = (float) Application.time.getDelta();
            velocitySmooth -= velocitySmooth * smoothness * delta;
        }
    }

    /* Getters */

    public final float getVelocity() {
        return velocity;
    }

    public final float getVelocitySmooth() {
        return velocitySmooth;
    }

}
