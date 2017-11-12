package aunmag.nightingale.utilities;

public class FluidValue {

    private float valueInitial = 0;
    private float valueCurrent = 0;
    private float valueTarget = 0;
    private float flexDegree = 1;
    private double timeInitial = ((double) System.currentTimeMillis() / 1000.0);
    private double timeDuration;

    public FluidValue(double timeDuration) {
        this.timeDuration = timeDuration;
    }

    public void update(double timeCurrent) {
        if (isTargetReached()) {
            return;
        }

        double timePassed = timeCurrent - timeInitial;
        double timeDone = timePassed / timeDuration;

        if (timeDone >= 1) {
            reachTargetNow();
        } else {
            timeDone = Math.pow(timeDone, flexDegree);
            float valueRange = valueTarget - valueInitial;
            float valueIncrease = (float) (valueRange * timeDone);
            valueCurrent = valueInitial + valueIncrease;
        }
    }

    public void reachTargetNow() {
        valueCurrent = valueTarget;
    }

    /* Setters */

    public void setValueTarget(float valueTarget, double timeInitial) {
        if (valueTarget != this.valueTarget) {
            valueInitial = this.valueCurrent;
            this.valueTarget = valueTarget;
            setTimeInitial(timeInitial);
        }
    }

    public void setFlexDegree(float flexDegree) {
        this.flexDegree = flexDegree;
    }

    public void setTimeInitial(double timeInitial) {
        this.timeInitial = timeInitial;
    }

    public void setTimeDuration(double timeDuration) {
        this.timeDuration = timeDuration;
    }

    /* Getters */

    public float getValueCurrent() {
        return valueCurrent;
    }

    public float getValueTarget() {
        return valueTarget;
    }

    public boolean isTargetReached() {
        return valueCurrent == valueTarget;
    }

    public double getTimeDuration() {
        return timeDuration;
    }

}
