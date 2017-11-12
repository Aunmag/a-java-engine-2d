package aunmag.nightingale.utilities;

public class Timer {

    private double timeCurrent = 0.0;
    private double timeDuration = 0.0;
    private double timeDurationDeviationFactor = 0.0;
    private double timeDurationCurrent = 0.0;
    private double timeTarget = 0.0;

    public Timer(double timeDuration) {
        this(timeDuration, 0.0);
    }

    public Timer(double timeDuration, double timeDurationDeviationFactor) {
        this.timeDurationDeviationFactor = timeDurationDeviationFactor;
        setTimeDuration(timeDuration);
    }

    public boolean calculateIsDone(double timeCurrent) {
        setTimeCurrent(timeCurrent);
        return isDone();
    }

    public void next(boolean isDoneMustBe) {
        if (isDone() == isDoneMustBe) {
            next();
        }
    }

    public void next() {
        updateTimeDurationCurrent();
        setTimeTarget(timeDurationCurrent + timeCurrent);
    }

    private void updateTimeDurationCurrent() {
        timeDurationCurrent = UtilsMath.randomizeFlexibly(
                (float) timeDuration,
                (float) (timeDuration * timeDurationDeviationFactor)
        );
    }

    /* Setters */

    public void setTimeCurrent(double timeCurrent) {
        this.timeCurrent = timeCurrent;
    }

    public void setTimeDuration(double timeDuration) {
        addTimeTarget(-timeDurationCurrent);
        this.timeDuration = timeDuration;
        updateTimeDurationCurrent();
        addTimeTarget(timeDurationCurrent);
    }

    public void setTimeDurationDeviationFactor(double timeDurationDeviationFactor) {
        this.timeDurationDeviationFactor = timeDurationDeviationFactor;
    }

    public final void addTimeTarget(double addTimeTarget) {
        setTimeTarget(timeTarget + addTimeTarget);
    }

    public void setTimeTarget(double timeTarget) {
        this.timeTarget = timeTarget;
    }

    /* Getters */

    public double getTimeCurrent() {
        return timeCurrent;
    }

    public double getTimeDuration() {
        return timeDuration;
    }

    public double getTimeDurationDeviationFactor() {
        return timeDurationDeviationFactor;
    }

    public double getTimeDurationCurrent() {
        return timeDurationCurrent;
    }

    public double getTimeTarget() {
        return timeTarget;
    }

    public boolean isDone() {
        return timeCurrent >= timeTarget;
    }

}
