package aunmag.nightingale.utilities;

public class FluidToggle extends FluidValue {

    public static final int VALUE_MIN = 0;
    public static final int VALUE_MAX = 1;

    public FluidToggle(long timeDuration) {
        super(timeDuration);
    }

    public void on(long timeInitial) {
        super.setValueTarget(VALUE_MAX, timeInitial);
    }

    public void off(long timeInitial) {
        super.setValueTarget(VALUE_MIN, timeInitial);
    }

    public void toggle(long timeInitial) {
        boolean toggleOn = getValueTarget() == VALUE_MIN;
        super.setValueTarget(toggleOn ? VALUE_MAX : VALUE_MIN, timeInitial);
    }

    /* Setters */

    public void setValueTarget(float valueTarget, long timeInitial) {
        if (valueTarget < VALUE_MIN) {
            valueTarget = VALUE_MIN;
        } else if (VALUE_MAX < valueTarget) {
            valueTarget = VALUE_MAX;
        }

        super.setValueTarget(valueTarget, timeInitial);
    }

    /* Getters */

    public boolean isCompitelyOn() {
        return getValueCurrent() == VALUE_MAX;
    }

    public boolean isCompitelyOff() {
        return getValueCurrent() == VALUE_MIN;
    }

}
