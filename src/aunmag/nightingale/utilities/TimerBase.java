package aunmag.nightingale.utilities;

public class TimerBase {

    public enum From {NOW, LAST_TARGET}

    private double timeCurrent = 0.0;
    private double timeDuration = 0.0;
    private double timeTarget = 0.0;

    public TimerBase(double timeDuration) {
        setTimeDuration(timeDuration);
    }

    public boolean calculateIsDone(double timeCurrent) {
        setTimeCurrent(timeCurrent);
        return isDone();
    }

    public void next(From from, boolean isDoneMustBe) {
        if (isDone() == isDoneMustBe) {
            next(from);
        }
    }

    public void next(From from) {
        if (from == From.NOW) {
            setTimeTarget(getTimeCurrent() + getTimeDuration());
        } else if (from == From.LAST_TARGET) {
            addTimeTarget(getTimeDuration());
        }
    }

    /* Setters */

    public void setTimeCurrent(double timeCurrent) {
        this.timeCurrent = timeCurrent;
    }

    public void setTimeDuration(double timeDuration) {
        addTimeTarget(-getTimeDuration());
        this.timeDuration = timeDuration;
        addTimeTarget(timeDuration);
    }

    protected final void addTimeTarget(double addTimeTarget) {
        setTimeTarget(timeTarget + addTimeTarget);
    }

    protected void setTimeTarget(double timeTarget) {
        this.timeTarget = timeTarget;
    }

    /* Getters */

    public double getTimeCurrent() {
        return timeCurrent;
    }

    public double getTimeDuration() {
        return timeDuration;
    }

    public double getTimeTarget() {
        return timeTarget;
    }

    public boolean isDone() {
        return timeCurrent >= timeTarget;
    }

}
