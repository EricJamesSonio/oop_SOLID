package pos;

import pos.service.SeederService;
import pos.ui.UI;
import pos.ui.tui.MainTUI;
import pos.ui.gui.GuiPlaceholder;

public class Main {
    public static void main(String[] args) {
        SeederService seeder = new SeederService();
        seeder.seedIfNeeded();
        UI ui = MainTUI.create();
        // UI ui = new GuiPlaceholder();

        ui.start();
    }
}
