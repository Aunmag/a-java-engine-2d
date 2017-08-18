package nightingale.engine.data;

public class DataTime {

    private static final int fpsLimit = 60;
    private static final float timeFrameDuration = 1000f / fpsLimit;
    private static float timeDelta = 0;
    private static long timeCurrent = 0;

    /* Setters */

    public static void setTimeDelta(float timeDelta) {
        DataTime.timeDelta = timeDelta;
    }

    public static void setTimeCurrent(long timeCurrent) {
        DataTime.timeCurrent = timeCurrent;
    }

    /* Getters */

    public static int getFpsLimit() {
        return fpsLimit;
    }

    public static float getTimeFrameDuration() {
        return timeFrameDuration;
    }

    public static float getTimeDelta() {
        return timeDelta;
    }

    public static long getTimeCurrent() {
        return timeCurrent;
    }

}
