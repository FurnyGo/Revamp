package fiveware.command.impl;

import fiveware.Fiveware;
import fiveware.command.Command;
import java.awt.Color;

public class ClientColor extends Command {
   public ClientColor() {
      super("ClientColor", "Changes the client color.", "c");
   }

   public void onCommand(String[] args, String command) {
      if (args.length == 0 || args.length < 3) {
         Fiveware.sendChatMessage(true, "§4.c(lientcolor) [red] [green] [blue]");
      }

      if (args.length == 3) {
         if (Fiveware.getClientCOLOR() == Color.BLACK) {
            Fiveware.setClientCOLOR(new Color(50, 50, 50));
         } else {
            Fiveware.setClientCOLOR(new Color(Integer.parseInt(args[0].replace(",", "")), Integer.parseInt(args[1].replace(",", "")), Integer.parseInt(args[2].replace(",", ""))));
         }

         Fiveware.sendChatMessage(false, "§cChanged the client color to §4" + this.getColor(args[0].replace(",", ""), args[1].replace(",", ""), args[2].replace(",", "")) + ".");
      }

   }

   private String getColor(String red, String green, String blue) {
      return String.format("#%06X", 16777215 & (new Color(Integer.parseInt(red), Integer.parseInt(green), Integer.parseInt(blue))).getRGB());
   }
}
