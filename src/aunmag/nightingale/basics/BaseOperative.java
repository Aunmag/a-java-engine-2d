package aunmag.nightingale.basics;

public interface BaseOperative {

    default void update() {}

    default void render() {}

    void remove();

    boolean isRemoved();

}
