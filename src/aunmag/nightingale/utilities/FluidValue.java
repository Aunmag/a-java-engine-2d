package aunmag.nightingale.utilities;

public class FluidValue {

    private float valueInitial = 0;
    private float valueCurrent = 0;
    private float valueTarget = 0;
    private float flexDegree = 1;
    private long timeInitial = System.currentTimeMillis();
    private long timeDuration;

    public FluidValue(long timeDuration) {
        this.timeDuration = timeDuration;
    }

    public void update(long timeCurrent) {
        if (isTargetReached()) {
            return;
        }

        long timePassed = timeCurrent - timeInitial;
        float timeDone = (float) timePassed / (float) timeDuration;

        if (timeDone >= 1) {
            reachTargetNow();
        } else {
            timeDone = (float) Math.pow(timeDone, flexDegree);
            float valueRange = valueTarget - valueInitial;
            float valueIncrease = valueRange * timeDone;
            valueCurrent = valueInitial + valueIncrease;
        }
    }

    public void reachTargetNow() {
        valueCurrent = valueTarget;
    }

    /* Setters */

    public void setValueTarget(float valueTarget, long timeInitial) {
        if (valueTarget != this.valueTarget) {
            valueInitial = this.valueCurrent;
            this.valueTarget = valueTarget;
            setTimeInitial(timeInitial);
        }
    }

    public void setFlexDegree(float flexDegree) {
        this.flexDegree = flexDegree;
    }

    public void setTimeInitial(long timeInitial) {
        this.timeInitial = timeInitial;
    }

    public void setTimeDuration(long timeDuration) {
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

    public long getTimeDuration() {
        return timeDuration;
    }

}
