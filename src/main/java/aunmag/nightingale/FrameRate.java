package aunmag.nightingale;

import aunmag.nightingale.utilities.UtilsValidate;

public class FrameRate {

    public static final int MIN = 1;
    public static final int MAX = 240;
    public static final int DEFAULT = 60;

    private int frequency;
    private double timeDelta = 0.0;
    private double timeDuration = 0.0;
    private double timeLastUpdate = 0;

    FrameRate(int frequency) {
        setFrequency(DEFAULT);
        setFrequency(frequency);
    }

    boolean calculateIsNow(double timeCurrent) {
        timeDelta = timeCurrent - timeLastUpdate;

        if (timeDelta >= timeDuration) {
            timeLastUpdate = timeCurrent;
            return true;
        } else {
            return false;
        }
    }

    /* Setters */

    public void setFrequency(int frequency) {
        if (!UtilsValidate.isInRange(frequency, MIN, MAX, "FPS limit")) {
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

}
