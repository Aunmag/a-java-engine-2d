package aunmag.nightingale.utilities;

import aunmag.nightingale.Application;

public class TimeFlow {

    private double current = 0.0;
    private double delta = 0.0;
    private double speed = 1.0;

    public void update() {
        delta = Application.getTimeDelta() * speed;
        current += delta;
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

    public void setCurrent(double current) {
        this.current = current;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

}
