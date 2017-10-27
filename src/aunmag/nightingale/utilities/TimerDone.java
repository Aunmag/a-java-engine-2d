package aunmag.nightingale.utilities;

public class TimerDone {

    private int timeDuration = 0;
    private long timeInitial = 0;
    private long timeTarget = 0;

    public TimerDone(int timeDuration) {
        setTimeDuration(timeDuration);
    }

    private void updateTimeTarget() {
        timeTarget = timeInitial + timeDuration;
    }

    public boolean calculateIsDone(long timeCurrent) {
        return timeTarget <= timeCurrent;
    }

    /* Setters */

    public void setTimeDuration(int timeDuration) {
        this.timeDuration = timeDuration;
        updateTimeTarget();
    }

    public void setTimeInitial(long timeInitial) {
        this.timeInitial = timeInitial;
        updateTimeTarget();
    }

    /* Getters */

    public int getTimeDuration() {
        return timeDuration;
    }

    public long getTimeInitial() {
        return timeInitial;
    }

    public long getTimeTarget() {
        return timeTarget;
    }

}
