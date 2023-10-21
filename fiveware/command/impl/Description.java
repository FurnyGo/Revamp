package fiveware.command.impl;

import fiveware.Fiveware;
import fiveware.command.Command;
import fiveware.modules.Module;
import java.util.Iterator;

public class Description extends Command {
   public Description() {
      super("Description", "Shows a description for any module.", "desc");
   }

   public void onCommand(String[] args, String command) {
      if (args.length == 0) {
         Fiveware.sendChatMessage(true, "§4.desc(ription) [module]");
      }

      if (args.length > 0) {
         boolean module = false;
         Fiveware.getINSTANCE();
         Iterator var5 = Fiveware.moduleManager.getModules().iterator();

         while(var5.hasNext()) {
            Module m = (Module)var5.next();
            if (m.getNameWithOutSpace().equalsIgnoreCase(args[0])) {
               Fiveware.sendChatMessage(false, "§c" + m.getDescription());
               module = true;
            }
         }

         if (!module) {
            Fiveware.sendChatMessage(true, "§4.desc(ription) [module]");
         }
      }

   }
}
