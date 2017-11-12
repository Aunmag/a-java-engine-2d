package aunmag.nightingale.utilities;

public class TimerDone {

    private double timeDuration = 0.0;
    private double timeInitial = 0.0;
    private double timeTarget = 0.0;

    public TimerDone(double timeDuration) {
        setTimeDuration(timeDuration);
    }

    private void updateTimeTarget() {
        timeTarget = timeInitial + timeDuration;
    }

    public boolean calculateIsDone(double timeCurrent) {
        return timeTarget <= timeCurrent;
    }

    /* Setters */

    public void setTimeDuration(double timeDuration) {
        this.timeDuration = timeDuration;
        updateTimeTarget();
    }

    public void setTimeInitial(double timeInitial) {
        this.timeInitial = timeInitial;
        updateTimeTarget();
    }

    /* Getters */

    public double getTimeDuration() {
        return timeDuration;
    }

    public double getTimeInitial() {
        return timeInitial;
    }

    public double getTimeTarget() {
        return timeTarget;
    }

}
