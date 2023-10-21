package fiveware.modules.impl.render;

import fiveware.events.Event;
import fiveware.events.impl.EventUpdate;
import fiveware.interfaces.Methods;
import fiveware.modules.Category;
import fiveware.modules.Module;
import fiveware.userinterfaces.clickgui.settings.Setting;

public class Animations extends Module implements Methods {
   public Setting mode = new Setting("Mode", new String[]{"Fiveware", "Exhibition 1", "Exhibition 2", "1.7", "Spin", "Sigma", "Slide"}, "Fiveware", this);
   public Setting smooth = new Setting("Smooth Hits", true, this);

   public Animations() {
      super("Animations", "Animations", "Adds animations when autoblocking.", -1, 0, Category.RENDER);
   }

   public void onEvent(Event event) {
      if (this.isToggled()) {
         if (event instanceof EventUpdate) {
            this.setInfo(this.information() ? "§7" + this.mode.getCurrentMode() + (this.leftSide() ? " §r" : "") : "");
         }

      }
   }
}
