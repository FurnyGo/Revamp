package fiveware.command.impl;

import fiveware.Fiveware;
import fiveware.command.Command;

public class Reload extends Command {
   public Reload() {
      super("Reload", "Reloads the client.", "rl");
   }

   public void onCommand(String[] args, String command) {
      Fiveware.getINSTANCE().shutdown();
      Fiveware.getINSTANCE().setup(true);
      Fiveware.sendChatMessage(false, "§cReloaded.");
   }
}
