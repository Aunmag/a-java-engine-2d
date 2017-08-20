package nightingale.basics;

import nightingale.data.DataTime;

public abstract class BaseWorld implements BaseOperative {

    private long timeCurrent = 0;

    public void update() {
        timeCurrent += DataTime.getTimePassed();
    }

    public abstract void render();

    public abstract void cleanUp();

    public abstract void remove();

    /* Getters */

    public long getTimeCurrent() {
        return timeCurrent;
    }

}
