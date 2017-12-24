package aunmag.nightingale.input;

import aunmag.nightingale.Application;
import aunmag.nightingale.Window;

public class Input {

    public static final Mouse mouse = new Mouse();
    public static final Keyboard keyboard = new Keyboard();

    public static void update() {
        mouse.update();
        keyboard.update();
    }

    /* Getters */

    public static boolean isAvailable() {
        return Application.getWindow().id != Window.UNDEFINED_ID;
    }

}
