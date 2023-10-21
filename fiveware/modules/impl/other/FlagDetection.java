package fiveware.modules.impl.other;

import fiveware.Fiveware;
import fiveware.events.Event;
import fiveware.events.impl.EventPacket;
import fiveware.interfaces.Methods;
import fiveware.modules.Category;
import fiveware.modules.Module;
import fiveware.userinterfaces.clickgui.settings.Setting;
import fiveware.userinterfaces.test.Notification;
import fiveware.userinterfaces.test.NotificationManager;
import java.util.Iterator;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;

public class FlagDetection extends Module implements Methods {
   public Setting check1 = new Setting("Combat Check", false, this);
   public Setting check2 = new Setting("Other Check", false, this);
   public Setting notify = new Setting("Notify", true, this);

   public FlagDetection() {
      super("Flag Detection", "FlagDetection", "Disables modules apon flag.", -1, 0, Category.OTHER);
   }

   public void onEvent(Event event) {
      if (this.isToggled()) {
         if (event instanceof EventPacket && ((EventPacket)event).chatPacket() instanceof S08PacketPlayerPosLook) {
            Iterator var3 = Fiveware.getToggledModules().iterator();

            while(var3.hasNext()) {
               Module m = (Module)var3.next();
               if (m.name.equalsIgnoreCase("Speed")) {
                  NotificationManager.show(new Notification(Notification.NotificationType.ERROR, "Flagged", m.name, 2));
                  m.toggle();
               } else if (m.name.equalsIgnoreCase("Flight")) {
                  NotificationManager.show(new Notification(Notification.NotificationType.ERROR, "Flagged", m.name, 2));
                  m.toggle();
               }

               if (this.check1.isToggled()) {
                  if (m.name.equalsIgnoreCase("Criticals")) {
                     NotificationManager.show(new Notification(Notification.NotificationType.ERROR, "Flagged", m.name, 2));
                     m.toggle();
                  } else if (m.name.equalsIgnoreCase("Velocity")) {
                     NotificationManager.show(new Notification(Notification.NotificationType.ERROR, "Flagged", m.name, 2));
                     m.toggle();
                  }
               }

               if (this.check2.isToggled()) {
                  if (m.name.equalsIgnoreCase("FastLadder")) {
                     NotificationManager.show(new Notification(Notification.NotificationType.ERROR, "Flagged", m.name, 2));
                     m.toggle();
                  } else if (m.name.equalsIgnoreCase("NoSlowdown")) {
                     NotificationManager.show(new Notification(Notification.NotificationType.ERROR, "Flagged", m.name, 2));
                     m.toggle();
                  }
               }
            }
         }

      }
   }
}
