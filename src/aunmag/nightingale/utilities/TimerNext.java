package aunmag.nightingale.utilities;

public class TimerNext {

    private double timeDuration = 0.0;
    private double timeNext = 0.0;
    private boolean isNow = false;

    public TimerNext(double timeDuration) {
        setTimeDuration(timeDuration);
    }

    public void update(double timeCurrent) {
        isNow = false;

        if (timeCurrent > timeNext) {
            timeNext = timeCurrent + timeDuration;
            isNow = true;
        }
    }

    /* Setters */

    public void setTimeDuration(double timeDuration) {
        timeNext -= this.timeDuration;
        this.timeDuration = timeDuration;
        timeNext += this.timeDuration;
    }

    /* Getters */

    public double getTimeDuration() {
        return timeDuration;
    }

    public double getTimeNext() {
        return timeNext;
    }

    public boolean isNow() {
        return isNow;
    }

}
