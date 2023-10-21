package fiveware.modules.impl.render;

import fiveware.events.Event;
import fiveware.events.impl.EventPacket;
import fiveware.interfaces.Methods;
import fiveware.modules.Category;
import fiveware.modules.Module;
import fiveware.userinterfaces.clickgui.settings.Setting;
import net.minecraft.network.play.server.S03PacketTimeUpdate;

public class Ambience extends Module implements Methods {
   public Setting mode = new Setting("Mode", new String[]{"Sunrise", "Day", "Sunset", "Night"}, "Night", this);

   public Ambience() {
      super("Ambience", "Ambience", "Changes the time client-side.", -1, 0, Category.RENDER);
   }

   public void onEvent(Event event) {
      if (this.isToggled()) {
         if (event instanceof EventPacket) {
            this.setInfo(this.information() ? "§7" + this.mode.getCurrentMode() + (this.leftSide() ? " §r" : "") : "");
            if (this.getWorld() == null) {
               return;
            }

            long time = this.mode.getCurrentMode().equalsIgnoreCase("Day") ? 5500L : (this.mode.getCurrentMode().equalsIgnoreCase("Sunset") ? 14000L : (this.mode.getCurrentMode().equalsIgnoreCase("Night") ? 18000L : 22000L));
            if (((EventPacket)event).getPacket() instanceof S03PacketTimeUpdate) {
               ((S03PacketTimeUpdate)((EventPacket)event).getPacket()).worldTime = time;
            }

            this.getWorld().setWorldTime(time);
         }

      }
   }
}
