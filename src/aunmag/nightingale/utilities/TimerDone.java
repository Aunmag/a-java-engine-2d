package aunmag.nightingale.utilities;

public class TimerDone extends TimerBase {

    private double timeInitial = 0.0;

    public TimerDone(double timeDuration) {
        setTimeDuration(timeDuration);
    }

    private void updateTimeTarget() {
        setTimeTarget(timeInitial + getTimeDuration());
    }

    public boolean calculateIsDone(double timeCurrent) {
        return getTimeTarget() <= timeCurrent;
    }

    /* Setters */

    public void setTimeDuration(double timeDuration) {
        super.setTimeDuration(timeDuration);
        updateTimeTarget();
    }

    public void setTimeInitial(double timeInitial) {
        this.timeInitial = timeInitial;
        updateTimeTarget();
    }

    /* Getters */

    public double getTimeInitial() {
        return timeInitial;
    }

}
