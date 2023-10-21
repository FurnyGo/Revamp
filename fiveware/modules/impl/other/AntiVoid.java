package fiveware.modules.impl.other;

import fiveware.events.Event;
import fiveware.events.impl.EventPre;
import fiveware.interfaces.Methods;
import fiveware.modules.Category;
import fiveware.modules.Module;
import fiveware.userinterfaces.clickgui.settings.Setting;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;

public class AntiVoid extends Module implements Methods {
   public Setting dist = new Setting("Fall Distance", 2.5F, 1.0F, 10.0F, false, this);

   public AntiVoid() {
      super("Anti Void", "AntiVoid", "Prevents you from falling to the void.", -1, 0, Category.OTHER);
   }

   public void onEvent(Event event) {
      if (this.isToggled()) {
         if (event instanceof EventPre && this.getPlayer().fallDistance > this.dist.getCurrentValue()) {
            if (this.getPlayer().posY < 0.0D) {
               ((EventPre)event).setY(((EventPre)event).getY() + 6.900000095367432D);
            } else {
               for(int i = (int)Math.ceil(this.getPlayer().posY); i >= 0; --i) {
                  if (this.getWorld().getBlockState(new BlockPos(this.getPlayer().posX, (double)i, this.getPlayer().posZ)).getBlock() != Blocks.air) {
                     return;
                  }
               }

               ((EventPre)event).setY(((EventPre)event).getY() + 6.900000095367432D);
            }
         }

      }
   }
}
