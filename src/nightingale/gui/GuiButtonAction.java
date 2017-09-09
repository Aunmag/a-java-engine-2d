package nightingale.gui;

import nightingale.basics.BaseGrid;

public class GuiButtonAction extends GuiButton {

    private Runnable action;

    public GuiButtonAction(
            BaseGrid grid,
            int x,
            int y,
            int width,
            int height,
            String text,
            Runnable action
    ) {
        super(grid, x, y, width, height, text);
        this.action = action;
    }

    public void update() {
        if (isPressed() && action != null) {
            try {
                action.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
