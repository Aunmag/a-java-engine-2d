package aunmag.nightingale;

import org.joml.Vector3f;

public class Configs {

    private static boolean isFullscreen = false;
    private static boolean isSamplesLoadingEnabled = true;
    private static Vector3f clearColor = new Vector3f(0.2f, 0.2f, 0.2f);
    private static int antialiasing = 2;
    private static int fpsLimit = 60;

    /* Getters */

    public static boolean isFullscreen() {
        return isFullscreen;
    }

    public static boolean isSamplesLoadingEnabled() {
        return isSamplesLoadingEnabled;
    }

    public static Vector3f getClearColor() {
        return clearColor;
    }

    public static int getAntialiasing() {
        return antialiasing;
    }

    public static int getFpsLimit() {
        return fpsLimit;
    }

    /* Setters */

    public static void setFullscreen(boolean isFullscreen) {
        if (Application.isInitialized()) {
            String message = "Unable to change screen mode after engine initialization";
            System.err.println(message);
        } else {
            Configs.isFullscreen = isFullscreen;
        }
    }

    public static void setSamplesLoadingEnabled(boolean isSamplesLoadingEnabled) {
        if (Application.isInitialized()) {
            String message = "Warning: " +
                    "The all samples loaded already will continue to be available";
            System.err.println(message);
        }

        Configs.isSamplesLoadingEnabled = isSamplesLoadingEnabled;
    }

    public static void setClearColor(Vector3f clearColor) {
        Configs.clearColor = clearColor;
    }

    public static void setAntialiasing(int antialiasing) {
        if (Application.isInitialized()) {
            String message = "Unable to change antialiasing after engine initialization";
            System.err.println(message);
        } else {
            Configs.antialiasing = antialiasing;
        }
    }

    public static void setFpsLimit(int fpsLimit) {
        if (Application.isInitialized()) {
            String message = "Unable to change FPS limit after engine initialization";
            System.err.println(message);
        } else {
            Configs.fpsLimit = fpsLimit;
        }
    }

}
