package aunmag.nightingale.utilities;

public class FluidToggle extends FluidValue {

    public static final int VALUE_MIN = 0;
    public static final int VALUE_MAX = 1;

    public FluidToggle(double timeDuration) {
        super(timeDuration);
    }

    public void on(double timeInitial) {
        super.setValueTarget(VALUE_MAX, timeInitial);
    }

    public void off(double timeInitial) {
        super.setValueTarget(VALUE_MIN, timeInitial);
    }

    public void toggle(double timeInitial) {
        boolean toggleOn = getValueTarget() == VALUE_MIN;
        super.setValueTarget(toggleOn ? VALUE_MAX : VALUE_MIN, timeInitial);
    }

    /* Setters */

    public void setValueTarget(float valueTarget, double timeInitial) {
        valueTarget = UtilsMath.limitNumber(valueTarget, VALUE_MIN, VALUE_MAX);
        super.setValueTarget(valueTarget, timeInitial);
    }

    /* Getters */

    public boolean isCompletelyOn() {
        return getValueCurrent() == VALUE_MAX;
    }

    public boolean isCompletelyOff() {
        return getValueCurrent() == VALUE_MIN;
    }

}
