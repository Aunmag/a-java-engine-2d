package aunmag.nightingale.gui;

import aunmag.nightingale.basics.BaseGrid;

public class GuiButtonLink extends GuiButtonAction {

    public GuiButtonLink(
            int x,
            int y,
            int width,
            int height,
            String text,
            GuiPage destination
    ) {
        super(x, y, width, height, text, destination::open);
    }

    public GuiButtonLink(
            BaseGrid grid,
            int x,
            int y,
            int width,
            int height,
            String text,
            GuiPage destination
    ) {
        super(grid, x, y, width, height, text, destination::open);
    }

}
