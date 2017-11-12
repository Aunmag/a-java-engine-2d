package aunmag.nightingale.utilities;

abstract class TimerBase {

    private double timeDuration = 0.0;
    private double timeTarget = 0.0;

    /* Setters */

    public void setTimeDuration(double timeDuration) {
        this.timeDuration = timeDuration;
    }

    protected final void addTimeTarget(double addTimeTarget) {
        setTimeTarget(timeTarget + addTimeTarget);
    }

    protected void setTimeTarget(double timeTarget) {
        this.timeTarget = timeTarget;
    }

    /* Getters */

    public double getTimeDuration() {
        return timeDuration;
    }

    public double getTimeTarget() {
        return timeTarget;
    }

}
