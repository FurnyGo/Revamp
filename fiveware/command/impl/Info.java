package fiveware.command.impl;

import fiveware.Fiveware;
import fiveware.command.Command;
import fiveware.updater.Updater;
import java.awt.Color;

public class Info extends Command {
   public Info() {
      super("Info", "Shows info about Fiveware.", "i");
   }

   public void onCommand(String[] args, String command) {
      Fiveware.sendChatMessage(false, "§4-§c-§4-§c-§4-§c-§4-§c-§4-");
      Fiveware.sendChatMessage(false, "§c" + Fiveware.getNAME() + " §4" + Fiveware.getVERSION() + (Updater.clientIsOutdated() ? " §4[§cOutdated§4]" : " §2[§aUpdated§2]"));
      Fiveware.sendChatMessage(false, "§cCreated by §4" + Fiveware.getAUTHOR());
      Fiveware.sendChatMessage(false, "§cSigned in as §4" + Fiveware.getUSERNAME());
      Fiveware.sendChatMessage(false, "§cLoaded Modules §4" + Fiveware.moduleManager.getModules().size());
      Fiveware.sendChatMessage(false, "§cToggled Modules §4" + Fiveware.getToggledModules().size());
      Fiveware.sendChatMessage(false, "§cLoaded Settings §4" + Fiveware.settingManager.getSetting().size());
      Fiveware.sendChatMessage(false, "§cClient Color §4" + this.getColor(Fiveware.getClientCOLOR().getRed(), Fiveware.getClientCOLOR().getGreen(), Fiveware.getClientCOLOR().getBlue()));
      Fiveware.sendChatMessage(false, "§c-§4-§c-§4-§c-§4-§c-§4-§c-");
   }

   private String getColor(int red, int green, int blue) {
      return String.format("#%06X", 16777215 & (new Color(red, green, blue)).getRGB());
   }
}
