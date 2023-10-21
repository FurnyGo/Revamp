package fiveware.modules.impl.world;

import fiveware.events.Event;
import fiveware.events.impl.EventUpdate;
import fiveware.interfaces.Methods;
import fiveware.modules.Category;
import fiveware.modules.Module;
import fiveware.userinterfaces.clickgui.settings.Setting;
import net.minecraft.item.ItemBlock;

public class FastPlace extends Module implements Methods {
   public Setting blocksOnly = new Setting("Blocks Only", true, this);

   public FastPlace() {
      super("Fast Place", "FastPlace", "Removes placing delay.", -1, 0, Category.WORLD);
   }

   public void onEvent(Event event) {
      if (this.isToggled()) {
         if (event instanceof EventUpdate) {
            if (this.blocksOnly.isToggled() && (this.getPlayer().getHeldItem() == null || this.getPlayer().getHeldItem().getItem() == null || !(this.getPlayer().getHeldItem().getItem() instanceof ItemBlock))) {
               return;
            }

            this.setRightClickDelay(0);
         }

      }
   }
}
