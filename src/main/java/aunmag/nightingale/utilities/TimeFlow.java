package aunmag.nightingale.utilities;

public class TimeFlow {

    private double current = 0.0;
    private double delta = 0.0;
    private double speed = 1.0;

    public void add(double add, boolean useSpeed) {
        if (useSpeed) {
            add *= speed;
        }

        current += add;
    }

    /* Getters */

    public double getCurrent() {
        return current;
    }

    public double getDelta() {
        return delta;
    }

    public double getSpeed() {
        return speed;
    }

    /* Setters */

    public void setCurrent(double current, boolean doDeltaUpdate) {
        if (doDeltaUpdate) {
            delta = current - this.current;
        }

        this.current = current;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

}
