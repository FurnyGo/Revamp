package fiveware.modules.impl.other;

import fiveware.events.Event;
import fiveware.events.impl.EventPacket;
import fiveware.events.impl.EventUpdate;
import fiveware.interfaces.Methods;
import fiveware.modules.Category;
import fiveware.modules.Module;
import fiveware.userinterfaces.clickgui.settings.Setting;
import fiveware.utilities.misc.Randomizer;
import java.util.Arrays;
import java.util.List;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S3EPacketTeams;
import net.minecraft.util.StringUtils;

public class AutoServer extends Module implements Methods {
   private String mode;
   private String type;
   private List<String> randomgg = Arrays.asList("gg", "GG!", "GG", "gg!");
   public Setting smode = new Setting("Mode", new String[]{"Hypixel", "Minemora"}, "Hypixel", this);
   public Setting gg = new Setting("AutoGG", false, this);
   public Setting join = new Setting("AutoJoin", true, this);

   public AutoServer() {
      super("Auto Server", "AutoServer", "Assists you with servers.", -1, 0, Category.OTHER);
   }

   public void onEvent(Event event) {
      if (this.isToggled()) {
         if (event instanceof EventUpdate) {
            this.setInfo(this.information() ? "§7" + this.smode.getCurrentMode() + (this.leftSide() ? " §r" : "") : "");
         }

         if (event instanceof EventPacket) {
            EventPacket e = (EventPacket)event;
            String message;
            if (this.smode.getCurrentMode().equalsIgnoreCase("Hypixel")) {
               if (e.getPacket() instanceof S3EPacketTeams) {
                  message = StringUtils.stripControlCodes(((S3EPacketTeams)e.getPacket()).func_149311_e());
                  if (message.contains("Mode: Normal")) {
                     this.mode = "normal";
                  } else if (message.contains("Mode: Insane")) {
                     this.mode = "insane";
                  } else if (message.contains("Mode: Mega")) {
                     this.type = "mega";
                     this.mode = "normal";
                  } else if (message.contains("Teams left")) {
                     this.type = "teams";
                  }
               }

               if (e.chatPacket() instanceof S02PacketChat) {
                  message = ((S02PacketChat)e.getPacket()).getChatComponent().getUnformattedText();
                  if (message.contains("Teaming is not allowed on Solo mode!")) {
                     this.type = "solo";
                  }

                  if (message.contains("Want to play again? Click here!") || message.contains("This game has been recorded.")) {
                     if (message.contains("Want to play again? Click here!") && this.gg.isToggled()) {
                        this.getPlayer().sendChatMessage((String)this.randomgg.get(Randomizer.randomInt(0, this.randomgg.size())));
                     }

                     this.getPlayer().sendChatMessage("/play " + this.type + "_" + this.mode);
                  }
               }
            } else if (e.chatPacket() instanceof S02PacketChat) {
               message = ((S02PacketChat)e.getPacket()).getChatComponent().getUnformattedText();
               if (this.join.isToggled() && (message.contains("Una nueva partida iniciara en") || message.contains("asesino a " + this.getUsername()) || message.contains(this.getUsername() + " ha muerto!"))) {
                  this.getPlayer().sendChatMessage("/join");
               }

               if (this.gg.isToggled() && message.contains("TOP ASESINATOS")) {
                  this.getPlayer().sendChatMessage((String)this.randomgg.get(Randomizer.randomInt(0, this.randomgg.size())));
               }
            }
         }

      }
   }
}
