package fiveware.modules.impl.render;

import fiveware.Fiveware;
import fiveware.interfaces.Methods;
import fiveware.modules.Category;
import fiveware.modules.Module;
import fiveware.userinterfaces.clickgui.settings.Setting;

public class Mixed extends Module implements Methods {
   public Setting c1r = new Setting("Color 1 R", 1.0F, 0.0F, 1.0F, false, this);
   public Setting c1g = new Setting("Color 1 G", 0.0F, 0.0F, 1.0F, false, this);
   public Setting c1b = new Setting("Color 1 B", 0.0F, 0.0F, 1.0F, false, this);
   public Setting c2r = new Setting("Color 2 R", 1.0F, 0.0F, 1.0F, false, this);
   public Setting c2g = new Setting("Color 2 G", 1.0F, 0.0F, 1.0F, false, this);
   public Setting c2b = new Setting("Color 2 B", 1.0F, 0.0F, 1.0F, false, this);

   public Mixed() {
      super("Mixed", "Mixed", "Changes the colors for mixed rainbow.", -1, 0, Category.RENDER);
   }

   public void onEnable() {
      Fiveware.sendChatMessage(true, "You can't enable Mixed.");
      this.toggle();
   }
}
