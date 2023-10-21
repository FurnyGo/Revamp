package fiveware.modules.impl.combat;

import fiveware.events.Event;
import fiveware.events.impl.EventUpdate;
import fiveware.interfaces.Methods;
import fiveware.modules.Category;
import fiveware.modules.Module;
import fiveware.userinterfaces.clickgui.settings.Setting;
import net.minecraft.entity.EntityLivingBase;

public class WTap extends Module implements Methods {
   public Setting hurtTime = new Setting("Hurt Time", 9.0F, 1.0F, 10.0F, true, this);

   public WTap() {
      super("W Tap", "WTap", "Removes bots.", -1, 0, Category.COMBAT);
   }

   public void onEvent(Event event) {
      if (this.isToggled()) {
         if (event instanceof EventUpdate && this.getObjectMouseOver() != null && this.getObjectMouseOver().entityHit instanceof EntityLivingBase) {
            EntityLivingBase target = (EntityLivingBase)this.getObjectMouseOver().entityHit;
            if ((float)target.hurtTime == this.hurtTime.getCurrentValue()) {
               this.getPlayer().sprintReset = true;
            }
         }

      }
   }
}
