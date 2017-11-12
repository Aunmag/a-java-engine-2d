package aunmag.nightingale.utilities;

public class TimerDone extends TimerBase {

    private double timeInitial = 0.0;

    public TimerDone(double timeDuration) {
        super(timeDuration);
    }

    /* Setters */

    public void setTimeInitial(double timeInitial) {
        this.timeInitial = timeInitial;
        setTimeTarget(timeInitial + getTimeDuration());
    }

    /* Getters */

    public double getTimeInitial() {
        return timeInitial;
    }

}
