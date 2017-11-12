package aunmag.nightingale.data;

public class DataTime {

    private static double delta = 0;
    private static long currentMilliseconds = System.currentTimeMillis();
    private static long passedMilliseconds = 0;

    /* Setters */

    public static void setDelta(double delta) {
        DataTime.delta = delta;
    }

    public static void setCurrentMilliseconds(long currentMilliseconds) {
        passedMilliseconds = currentMilliseconds - DataTime.currentMilliseconds;
        DataTime.currentMilliseconds = currentMilliseconds;
    }

    /* Getters */

    public static double getDelta() {
        return delta;
    }

    public static long getCurrentMilliseconds() {
        return currentMilliseconds;
    }

    public static long getPassedMilliseconds() {
        return passedMilliseconds;
    }

}
