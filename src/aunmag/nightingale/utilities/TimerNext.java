package aunmag.nightingale.utilities;

public class TimerNext extends TimerBase {

    private boolean isNow = false;

    public TimerNext(double timeDuration) {
        setTimeDuration(timeDuration);
    }

    public void update(double timeCurrent) {
        isNow = false;

        if (timeCurrent > getTimeTarget()) {
            setTimeTarget(timeCurrent + getTimeDuration());
            isNow = true;
        }
    }

    /* Setters */

    public void setTimeDuration(double timeDuration) {
        addTimeTarget(-getTimeDuration());
        super.setTimeDuration(timeDuration);
        addTimeTarget(+getTimeDuration());
    }

    /* Getters */

    public boolean isNow() {
        return isNow;
    }

}
