package nightingale.gui;

import java.util.ArrayList;
import java.util.List;

public class GuiManager {

    private static List<GuiPage> pages = new ArrayList<>();

    public static void update() {
        getCurrentPage().update();
    }

    public static void back() {
        pages.remove(getLastPageIndex());
    }

    public static void open(GuiPage page) {
        pages.add(page);
    }

    public static void render() {
        getCurrentPage().render();
    }

    /* Getters */

    private static int getLastPageIndex() {
        return pages.size() - 1;
    }

    public static GuiPage getCurrentPage() {
        return pages.get(getLastPageIndex());
    }

    public static boolean isPageMainOpened() {
        return getLastPageIndex() == 0;
    }

}
