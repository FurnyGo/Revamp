package fiveware.modules.impl.movement;

import fiveware.events.Event;
import fiveware.events.impl.EventUpdate;
import fiveware.interfaces.Methods;
import fiveware.modules.Category;
import fiveware.modules.Module;
import fiveware.userinterfaces.clickgui.settings.Setting;
import net.minecraft.client.entity.EntityPlayerSP;

public class FastLadder extends Module implements Methods {
   public Setting mode = new Setting("Mode", new String[]{"Motion", "Timer"}, "Motion", this);
   public Setting speed = new Setting("Speed", 1.1F, 1.0F, 10.0F, false, this);

   public FastLadder() {
      super("Fast Ladder", "FastLadder", "Speeds you up on ladders.", -1, 0, Category.MOVEMENT);
   }

   public void onEvent(Event event) {
      if (this.isToggled()) {
         if (event instanceof EventUpdate) {
            this.setInfo(this.information() ? "§7" + this.mode.getCurrentMode() + (this.leftSide() ? " §r" : "") : "");
            if (!this.isMoving()) {
               return;
            }

            if (this.isOnLadder()) {
               if (this.mode.getCurrentMode().equalsIgnoreCase("Motion")) {
                  EntityPlayerSP var10000 = this.getPlayer();
                  var10000.motionY += (double)(this.speed.getCurrentValue() / 10.0F);
               } else {
                  this.getTimer().timerSpeed = this.speed.getCurrentValue();
               }
            } else if (!this.isOnLadder() && this.mode.getCurrentMode().equalsIgnoreCase("Timer")) {
               this.getTimer().timerSpeed = 1.0F;
            }
         }

      }
   }

   public void onDisable() {
      this.getTimer().timerSpeed = 1.0F;
   }
}
