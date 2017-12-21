package aunmag.nightingale.gui;

import aunmag.nightingale.basics.BaseGrid;

public class GuiButtonBack extends GuiButtonAction {

    private static final Runnable action = GuiManager::back;

    public GuiButtonBack(
            int x,
            int y,
            int width,
            int height,
            String text
    ) {
        super(x, y, width, height, text, action);
    }

    public GuiButtonBack(
            BaseGrid grid,
            int x,
            int y,
            int width,
            int height,
            String text
    ) {
        super(grid, x, y, width, height, text, action);
    }

}
