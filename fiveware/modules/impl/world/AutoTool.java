package fiveware.modules.impl.world;

import fiveware.events.Event;
import fiveware.events.impl.EventUpdate;
import fiveware.interfaces.Methods;
import fiveware.modules.Category;
import fiveware.modules.Module;
import net.minecraft.item.ItemStack;
import org.lwjgl.input.Mouse;

public class AutoTool extends Module implements Methods {
   public AutoTool() {
      super("Auto Tool", "AutoTool", "Switches to a compatible tool for you.", -1, 0, Category.WORLD);
   }

   public void onEvent(Event event) {
      if (this.isToggled()) {
         if (event instanceof EventUpdate) {
            boolean breaking = false;
            int prevSlot = -1;
            int bestSlot = -1;
            float bestSpeed = 1.0F;
            if (this.getCurrentScreen() == null && this.isIngame() && this.getObjectMouseOver() != null && this.getObjectMouseOver().getBlockPos() != null && this.getObjectMouseOver().entityHit == null && Mouse.isButtonDown(0)) {
               for(int k = 0; k < 9; ++k) {
                  ItemStack item = this.getPlayer().inventory.getStackInSlot(k);
                  if (item != null) {
                     float speed = item.getStrVsBlock(this.getWorld().getBlockState(this.getObjectMouseOver().getBlockPos()).getBlock());
                     if (speed > bestSpeed) {
                        bestSpeed = speed;
                        bestSlot = k;
                     }
                  }
               }

               if (bestSlot != -1 && this.getPlayer().inventory.currentItem != bestSlot) {
                  this.getPlayer().inventory.currentItem = bestSlot;
                  breaking = true;
               } else if (bestSlot == -1) {
                  if (breaking) {
                     this.getPlayer().inventory.currentItem = prevSlot;
                     breaking = false;
                  }

                  int var9 = this.getPlayer().inventory.currentItem;
               }
            }
         }

      }
   }
}
