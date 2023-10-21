package fiveware.modules.impl.movement;

import fiveware.events.Event;
import fiveware.events.impl.EventUpdate;
import fiveware.interfaces.Methods;
import fiveware.modules.Category;
import fiveware.modules.Module;
import net.minecraft.potion.Potion;

public class Sprint extends Module implements Methods {
   public Sprint() {
      super("Sprint", "Sprint", "Automatically sprints for you.", -1, 0, Category.MOVEMENT);
   }

   public void onEvent(Event event) {
      if (this.isToggled()) {
         if (event instanceof EventUpdate && !this.getPlayer().isCollidedHorizontally && !this.getPlayer().isPotionActive(Potion.moveSlowdown) && !this.getPlayer().isPotionActive(Potion.harm) && !this.getPlayer().isBurning() && this.getPlayer().moveForward > 0.0F && this.getPlayer().getFoodStats().getFoodLevel() > 6) {
            this.getPlayer().setSprinting(true);
         }

      }
   }

   public void onDisable() {
      this.getPlayer().setSprinting(false);
   }
}
