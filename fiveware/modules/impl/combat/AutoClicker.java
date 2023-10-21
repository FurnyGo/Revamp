package fiveware.modules.impl.combat;

import fiveware.events.Event;
import fiveware.events.impl.EventUpdate;
import fiveware.interfaces.Methods;
import fiveware.modules.Category;
import fiveware.modules.Module;
import fiveware.userinterfaces.clickgui.settings.Setting;
import fiveware.utilities.misc.Randomizer;
import fiveware.utilities.time.Timer;
import net.minecraft.client.Minecraft;
import net.minecraft.util.MovingObjectPosition;
import org.lwjgl.input.Mouse;

public class AutoClicker extends Module implements Methods {
   private Timer timer = new Timer();
   public Setting mincps = new Setting("Minimum CPS", 8.0F, 1.0F, 20.0F, false, this);
   public Setting maxcps = new Setting("Maximum CPS", 12.0F, 1.0F, 20.0F, false, this);
   public Setting breakblocks = new Setting("Break Blocks", false, this);
   public Setting ncd = new Setting("No Click Delay", true, this);

   public AutoClicker() {
      super("Auto Clicker", "AutoClicker", "Automatically clicks for you.", -1, 0, Category.COMBAT);
   }

   public void onEvent(Event event) {
      if (this.mincps.getCurrentValue() >= this.maxcps.getCurrentValue()) {
         this.maxcps.currentValue = this.mincps.getCurrentValue() + 0.01F;
      }

      if (this.isToggled()) {
         if (event instanceof EventUpdate) {
            if (this.breakblocks.isToggled() && this.getObjectMouseOver().typeOfHit != null && this.getObjectMouseOver().typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
               return;
            }

            if (Mouse.isButtonDown(0)) {
               if (this.ncd.isToggled()) {
                  Minecraft.getMinecraft().leftClickCounter = 0;
               }

               if (this.timer.hasReached((long)(895.0F / Randomizer.randomFloat(this.mincps.getCurrentValue(), this.maxcps.getCurrentValue())))) {
                  Minecraft.getMinecraft().clickMouse();
                  this.timer.reset();
               }
            }
         }

      }
   }
}
