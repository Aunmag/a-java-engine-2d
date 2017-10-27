package aunmag.nightingale.utilities;

public class TimerNext {

    private int timeDuration = 0;
    private long timeNext = 0;
    private boolean isNow = false;

    public TimerNext(int timeDuration) {
        setTimeDuration(timeDuration);
    }

    public void update(long timeCurrent) {
        isNow = false;

        if (timeCurrent > timeNext) {
            timeNext = timeCurrent + timeDuration;
            isNow = true;
        }
    }

    /* Setters */

    public void setTimeDuration(int timeDuration) {
        timeNext -= this.timeDuration;
        this.timeDuration = timeDuration;
        timeNext += this.timeDuration;
    }

    /* Getters */

    public int getTimeDuration() {
        return timeDuration;
    }

    public long getTimeNext() {
        return timeNext;
    }

    public boolean isNow() {
        return isNow;
    }

}
