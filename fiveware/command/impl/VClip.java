package fiveware.command.impl;

import fiveware.Fiveware;
import fiveware.command.Command;

public class VClip extends Command {
   public VClip() {
      super("VClip", "Clips you down / up.", "vc");
   }

   public void onCommand(String[] args, String command) {
      if (args.length == 0) {
         Fiveware.sendChatMessage(true, "§4.vc / vclip [blocks]");
      }

      if (args.length == 1) {
         this.mc.thePlayer.setPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + Double.parseDouble(args[0]), this.mc.thePlayer.posZ);
      }

   }
}
