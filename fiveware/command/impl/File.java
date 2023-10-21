package fiveware.command.impl;

import fiveware.Fiveware;
import fiveware.command.Command;

public class File extends Command {
   public File() {
      super("File", "Saves / Loads your saved files.", "f");
   }

   public void onCommand(String[] args, String command) {
      if (args.length == 0) {
         Fiveware.sendChatMessage(true, "§4.f(ile) s(ave) / l(oad)");
      }

      if (args.length > 0) {
         label49: {
            String var3;
            switch((var3 = args[0]).hashCode()) {
            case 108:
               if (!var3.equals("l")) {
                  return;
               }
               break label49;
            case 115:
               if (!var3.equals("s")) {
                  return;
               }
               break;
            case 3327206:
               if (!var3.equals("load")) {
                  return;
               }
               break label49;
            case 3522941:
               if (!var3.equals("save")) {
                  return;
               }
               break;
            default:
               return;
            }

            Fiveware.fileManager.writeAllFiles();
            Fiveware.sendChatMessage(false, "§cI have saved your files.");
            return;
         }

         Fiveware.fileManager.readAllFiles();
         Fiveware.sendChatMessage(false, "§cI have loaded your files.");
      }

   }
}
