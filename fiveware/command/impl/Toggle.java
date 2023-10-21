package fiveware.command.impl;

import fiveware.Fiveware;
import fiveware.command.Command;
import fiveware.modules.Module;
import java.util.Iterator;

public class Toggle extends Command {
   public Toggle() {
      super("Toggle", "Enable/Disable a module.", "t");
   }

   public void onCommand(String[] args, String command) {
      if (args.length == 0) {
         Fiveware.getINSTANCE();
         Fiveware.sendChatMessage(true, "§4.t(oggle) [name]");
      }

      if (args.length > 0) {
         boolean Module = false;
         Fiveware.getINSTANCE();
         Iterator var5 = Fiveware.moduleManager.getModules().iterator();

         while(var5.hasNext()) {
            Module module = (Module)var5.next();
            if (module.getNameWithOutSpace().equalsIgnoreCase(args[0])) {
               Module = true;
               module.toggle();
               Fiveware.getINSTANCE();
               Fiveware.sendChatMessage(false, (module.isToggled() ? "§cEnabled" : "§cDisabled") + " §4" + module.getName());
               break;
            }
         }

         if (!Module) {
            Fiveware.getINSTANCE();
            Fiveware.sendChatMessage(true, "§4.t(oggle) [name]");
         }
      }

   }
}
