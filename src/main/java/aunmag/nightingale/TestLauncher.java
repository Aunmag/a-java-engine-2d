package aunmag.nightingale;

import aunmag.nightingale.gui.GuiLabel;

class TestLauncher extends Application {

    public static void main(String[] args) {
        new TestLauncher().run();
    }

    private GuiLabel message = new GuiLabel(5, 5, 2, 1, "It worked!");

    protected void gameUpdate() {}

    protected void gameRender() {
        message.render();
    }

    protected void gameTerminate() {
        message.delete();
    }

}
