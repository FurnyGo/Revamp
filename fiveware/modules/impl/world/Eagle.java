package fiveware.modules.impl.world;

import fiveware.events.Event;
import fiveware.events.impl.EventUpdate;
import fiveware.interfaces.Methods;
import fiveware.modules.Category;
import fiveware.modules.Module;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.BlockPos;

public class Eagle extends Module implements Methods {
   public Eagle() {
      super("Eagle", "Eagle", "Sneaks when bridging.", -1, 0, Category.WORLD);
   }

   public void onEvent(Event event) {
      if (this.isToggled()) {
         if (event instanceof EventUpdate) {
            if (!this.isIngame()) {
               return;
            }

            BlockPos position = new BlockPos(this.getX(), this.getY() - 1.0D, this.getZ());
            if (this.getCurrentEquippedItem() != null && this.getCurrentEquippedItem().getItem() instanceof ItemBlock) {
               this.getGameSettings().keyBindSneak.pressed = false;
               if (this.getWorld().getBlockState(position).getBlock() == Blocks.air) {
                  this.getGameSettings().keyBindSneak.pressed = true;
               }
            }
         }

      }
   }

   public void onDisable() {
      this.getGameSettings().keyBindSneak.pressed = false;
   }
}
