package aunmag.nightingale.data;

public class DataTime {

    private static float timeDelta = 0;
    private static long timeCurrent = System.currentTimeMillis();
    private static long timePassed = 0;

    /* Setters */

    public static void setTimeDelta(float timeDelta) {
        DataTime.timeDelta = timeDelta;
    }

    public static void setTimeCurrent(long timeCurrent) {
        timePassed = timeCurrent - DataTime.timeCurrent;
        DataTime.timeCurrent = timeCurrent;
    }

    /* Getters */

    public static float getTimeDelta() {
        return timeDelta;
    }

    public static long getTimeCurrent() {
        return timeCurrent;
    }

    public static long getTimePassed() {
        return timePassed;
    }

}
