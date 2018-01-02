package aunmag.nightingale;

public class FrameRate {

    public static final int MIN = 1;
    public static final int DEFAULT = 60;
    public static final float TIME_DELTA_MAX = 1f / MIN;

    private int frequency;
    private double timeDelta = 0.0;
    private double timeDuration = 0.0;
    private double timeLastUpdate = 0.0;

    FrameRate(int frequency) {
        setFrequency(DEFAULT);
        setFrequency(frequency);
    }

    boolean tryNext(double timeCurrent) {
        timeDelta = timeCurrent - timeLastUpdate;

        boolean isNow = timeDelta >= timeDuration;

        if (isNow) {
            timeLastUpdate = timeCurrent;

            if (timeDelta > TIME_DELTA_MAX) {
                timeDelta = TIME_DELTA_MAX;
            }
        } else {
            timeDelta = 0;
        }

        return isNow;
    }

    /* Setters */

    public void setFrequency(int frequency) {
        if (frequency < MIN) {
            String message = String.format(
                    "FPS limit must not be less than %s. Got %s instead!",
                    MIN,
                    frequency
            );
            System.out.println(message);
            return;
        }

        this.frequency = frequency;
        timeDuration = 1.0 / (double) frequency;
    }

    /* Getters */

    public int getFrequency() {
        return frequency;
    }

    public double getTimeDelta() {
        return timeDelta;
    }

    public double getTimeDuration() {
        return timeDuration;
    }

    public double getTimeLastUpdate() {
        return timeLastUpdate;
    }

}
