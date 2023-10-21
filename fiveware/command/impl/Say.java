package fiveware.command.impl;

import fiveware.Fiveware;
import fiveware.command.Command;

public class Say extends Command {
   public Say() {
      super("Say", "Sends a packet to say anything in chat.", "s");
   }

   public void onCommand(String[] args, String command) {
      if (args.length == 0) {
         Fiveware.getINSTANCE();
         Fiveware.sendChatMessage(true, "§4.s(ay) [message]");
      }

      if (args.length > 0) {
         this.mc.thePlayer.sendChatMessage(String.join(" ", args));
      }

   }
}
