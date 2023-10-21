package fiveware.modules.impl.world;

import fiveware.events.Event;
import fiveware.events.impl.EventUpdate;
import fiveware.interfaces.Methods;
import fiveware.modules.Category;
import fiveware.modules.Module;
import fiveware.userinterfaces.clickgui.settings.Setting;

public class Timer extends Module implements Methods {
   public Setting speed = new Setting("Speed", 2.0F, 0.1F, 10.0F, false, this);

   public Timer() {
      super("Timer", "Timer", "Changes the game speed.", -1, 0, Category.WORLD);
   }

   public void onEvent(Event event) {
      if (this.isToggled()) {
         if (event instanceof EventUpdate) {
            this.getTimer().timerSpeed = this.speed.getCurrentValue();
         }

      }
   }

   public void onDisable() {
      this.getTimer().timerSpeed = 1.0F;
   }
}
