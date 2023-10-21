package fiveware.modules.impl.combat;

import fiveware.interfaces.Methods;
import fiveware.modules.Category;
import fiveware.modules.Module;
import fiveware.userinterfaces.clickgui.settings.Setting;

public class Hitboxes extends Module implements Methods {
   public Setting hitbox = new Setting("Hitbox", 0.25F, 0.0F, 2.0F, false, this);

   public Hitboxes() {
      super("Hitboxes", "Hitboxes", "Expands the hitbox for entities.", -1, 0, Category.COMBAT);
   }
}
