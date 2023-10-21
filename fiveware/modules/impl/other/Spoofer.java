package fiveware.modules.impl.other;

import fiveware.Fiveware;
import fiveware.events.Event;
import fiveware.events.impl.EventUpdate;
import fiveware.interfaces.Methods;
import fiveware.modules.Category;
import fiveware.modules.Module;
import fiveware.userinterfaces.clickgui.settings.Setting;

public class Spoofer extends Module implements Methods {
   public Setting mode = new Setting("Mode", new String[]{"Forge", "Lunar"}, "Lunar", this);

   public Spoofer() {
      super("Spoofer", "Spoofer", "Spoofs your client name.", -1, 0, Category.OTHER);
   }

   public void onEvent(Event event) {
      if (this.isToggled()) {
         if (event instanceof EventUpdate) {
            this.setInfo(this.information() ? this.mode.getCurrentMode() + (this.leftSide() ? " §r" : "") : "");
         }

      }
   }

   public String getBrand() {
      return this.mode.getCurrentMode().equalsIgnoreCase("Forge") ? "fml,forge" : "lunarclient:" + Fiveware.getLUNARID();
   }
}
