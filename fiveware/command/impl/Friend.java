package fiveware.command.impl;

import fiveware.Fiveware;
import fiveware.command.Command;
import fiveware.friend.FriendManager;
import java.util.Iterator;
import net.minecraft.client.Minecraft;

public class Friend extends Command {
   public Friend() {
      super("Friend", "Manages friends.", "fr");
   }

   public void onCommand(String[] args, String command) {
      if (args.length == 0) {
         Fiveware.sendChatMessage(true, "§4.fr(iend) a(dd) / r(emove) / l(ist) / c(lear) [name]");
      }

      if (args.length == 1) {
         if (!args[0].equalsIgnoreCase("l") && !args[0].equalsIgnoreCase("list")) {
            if (!args[0].equalsIgnoreCase("c") && !args[0].equalsIgnoreCase("clear")) {
               Fiveware.sendChatMessage(true, "§4.fr(iend) a(dd) / r(emove) / l(ist) [name]");
            } else {
               Fiveware.friendManager.clearFriends();
               Fiveware.sendChatMessage(false, "§cCleared friends.");
            }
         } else {
            Iterator var4 = Fiveware.friendManager.getFriends().iterator();

            while(var4.hasNext()) {
               FriendManager.Friend friend = (FriendManager.Friend)var4.next();
               Fiveware.sendChatMessage(false, "§c" + friend.name + " §4| §c" + (Fiveware.getOnlinePlayers().contains(friend.name) ? "Online" : "Offline"));
            }

            if (!Fiveware.friendManager.getFriends().isEmpty()) {
               Fiveware.sendChatMessage(false, "§cTotal Friends: §4" + Fiveware.friendManager.getFriends().size());
            } else {
               Fiveware.sendChatMessage(false, "§cNo friends found.");
            }
         }
      }

      if (args.length > 1) {
         label103: {
            String var5;
            switch((var5 = args[0]).hashCode()) {
            case -934610812:
               if (!var5.equals("remove")) {
                  return;
               }
               break;
            case 97:
               if (!var5.equals("a")) {
                  return;
               }
               break label103;
            case 114:
               if (!var5.equals("r")) {
                  return;
               }
               break;
            case 96417:
               if (!var5.equals("add")) {
                  return;
               }
               break label103;
            default:
               return;
            }

            if (Fiveware.friendManager.isFriend(args[1])) {
               Fiveware.friendManager.removeFriend(args[1]);
               Fiveware.sendChatMessage(false, "§4" + args[1] + "§c is no longer your friend.");
            } else {
               Fiveware.sendChatMessage(true, "§cYou don't have §4" + args[1] + "§c added.");
            }

            return;
         }

         if (!Fiveware.friendManager.isFriend(args[1]) && !args[1].equalsIgnoreCase(Minecraft.getMinecraft().thePlayer.getName())) {
            Fiveware.friendManager.addFriend(args[1]);
            Fiveware.sendChatMessage(false, "§4" + args[1] + "§c is now your friend.");
         } else if (args[1].equalsIgnoreCase(Minecraft.getMinecraft().thePlayer.getName())) {
            Fiveware.sendChatMessage(true, "§cYou can't add yourself!");
         } else {
            Fiveware.sendChatMessage(true, "§cYou have §4" + args[1] + "§c added already.");
         }
      }

   }
}
