package fiveware.modules.impl.render;

import fiveware.events.Event;
import fiveware.events.impl.EventUpdate;
import fiveware.interfaces.Methods;
import fiveware.modules.Category;
import fiveware.modules.Module;
import fiveware.userinterfaces.clickgui.settings.Setting;
import net.minecraft.util.ResourceLocation;

public class Cape extends Module implements Methods {
   public Setting mode = new Setting("Mode", new String[]{"Moon", "Raindrops", "Mountains", "Anime"}, "Anime", this);

   public Cape() {
      super("Cape", "Cape", "Renders a client-side cape.", -1, 0, Category.RENDER);
   }

   public void onEvent(Event event) {
      if (this.isToggled()) {
         if (event instanceof EventUpdate) {
            this.setInfo(this.information() ? "§7" + this.mode.getCurrentMode() + (this.leftSide() ? " §r" : "") : "");
            this.setCape(new ResourceLocation("fiveware/capes/" + this.mode.getCurrentMode().replace(" ", "").toLowerCase() + ".png"));
         }

      }
   }

   public void onDisable() {
      this.setCape((ResourceLocation)null);
   }
}
