package fiveware.command.impl;

import fiveware.Fiveware;
import fiveware.command.Command;
import fiveware.modules.Module;
import java.util.Iterator;
import org.lwjgl.input.Keyboard;

public class Bind extends Command {
   public Bind() {
      super("Bind", "Binds modules into a key.", "b");
   }

   public void onCommand(String[] args, String command) {
      if (args.length == 0) {
         Fiveware.sendChatMessage(true, "§4.b(ind) [module] [key]");
      }

      if (args.length == 1) {
         if (args[0].equalsIgnoreCase("clear")) {
            Fiveware.getINSTANCE();
            Iterator var4 = Fiveware.moduleManager.getModules().iterator();

            while(var4.hasNext()) {
               Module m = (Module)var4.next();
               m.setKey(0);
            }

            Fiveware.getINSTANCE();
            Fiveware.moduleManager.getModuleByName("ClickGUI").setKey(54);
            Fiveware.sendChatMessage(false, "§cBinds have been cleared.");
         } else {
            Fiveware.sendChatMessage(true, "§4.b(ind) [module] [key]");
         }
      }

      if (args.length == 2) {
         boolean module = false;
         Fiveware.getINSTANCE();
         Iterator var5 = Fiveware.moduleManager.getModules().iterator();

         while(var5.hasNext()) {
            Module m = (Module)var5.next();
            if (m.getNameWithOutSpace().equalsIgnoreCase(args[0])) {
               m.setKey(Keyboard.getKeyIndex(args[1].toUpperCase()));
               Fiveware.sendChatMessage(false, "§cBinded §4" + m.getName() + "§c to §4" + Keyboard.getKeyName(m.getKey()) + "§c.");
               module = true;
               break;
            }
         }

         if (!module) {
            Fiveware.sendChatMessage(true, "§4.b(ind) [module] [key]");
         }
      }

   }
}
