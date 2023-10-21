package fiveware.modules.impl.world;

import fiveware.events.Event;
import fiveware.events.impl.EventUpdate;
import fiveware.interfaces.Methods;
import fiveware.modules.Category;
import fiveware.modules.Module;

public class Parkour extends Module implements Methods {
   public Parkour() {
      super("Parkour", "Parkour", "Jumps at the edge of the block.", -1, 0, Category.WORLD);
   }

   public void onEvent(Event event) {
      if (this.isToggled()) {
         if (event instanceof EventUpdate && this.onGround() && this.getWorld().getCollidingBoundingBoxes(this.getPlayer(), this.getPlayer().getEntityBoundingBox().offset(0.0D, -0.5D, 0.0D).expand(-0.001D, 0.0D, -0.001D)).isEmpty()) {
            this.jump();
         }

      }
   }
}
