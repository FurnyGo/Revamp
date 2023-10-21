package fiveware.command.impl;

import fiveware.Fiveware;
import fiveware.command.Command;
import fiveware.command.CommandManager;
import java.util.Iterator;

public class Help extends Command {
   public Help() {
      super("Help", "Shows a list of commands.", "h");
   }

   public void onCommand(String[] args, String command) {
      Fiveware.sendChatMessage(false, "§4-§c-§4-§c-§4-§c-§4-§c-§4-");
      Iterator var4 = CommandManager.commands.iterator();

      while(var4.hasNext()) {
         Command cmd = (Command)var4.next();
         Fiveware.sendChatMessage(false, "§c" + cmd.getName() + " | §4" + cmd.getDescription());
      }

      Fiveware.sendChatMessage(false, "§c-§4-§c-§4-§c-§4-§c-§4-§c-");
   }
}
