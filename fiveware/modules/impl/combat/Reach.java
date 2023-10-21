package fiveware.modules.impl.combat;

import fiveware.interfaces.Methods;
import fiveware.modules.Category;
import fiveware.modules.Module;
import fiveware.userinterfaces.clickgui.settings.Setting;

public class Reach extends Module implements Methods {
   public Setting reach = new Setting("Reach", 4.0F, 0.0F, 6.0F, false, this);

   public Reach() {
      super("Reach", "Reach", "Extends your hit range.", -1, 0, Category.COMBAT);
   }
}
