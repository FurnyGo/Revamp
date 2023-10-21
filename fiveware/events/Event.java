package fiveware.events;

import fiveware.Fiveware;
import fiveware.modules.Module;
import java.util.Iterator;

public class Event {
   public static boolean cancelled;

   public static boolean isCancelled() {
      return cancelled;
   }

   public void setCancelled(boolean cancelled) {
      Event.cancelled = cancelled;
   }

   public void onFire() {
      Fiveware.getINSTANCE();
      Iterator var2 = Fiveware.moduleManager.getModules().iterator();

      while(var2.hasNext()) {
         Module module = (Module)var2.next();
         module.onEvent(this);
      }

   }
}
