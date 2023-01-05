// 
// Decompiled by Procyon v0.5.36
// 

package revamp.command.impl;

import java.util.Iterator;
import net.minecraft.client.Minecraft;
import revamp.friend.FriendManager;
import revamp.modules.ModuleManager;
import revamp.Revamp;
import revamp.command.Command;

public class Friend extends Command
{
    public Friend() {
        super("Friend", "Manages friends.", new String[] { "fr" });
    }
    
    @Override
    public void onCommand(final String[] args, final String command) {
        if (args.length == 0) {
            Revamp.getINSTANCE();
            final ModuleManager moduleManager = Revamp.moduleManager;
            ModuleManager.addChatMessage(true, "§cInvalid! Try §7.fr(iend) a(dd)/r(emove)/l(ist) §7<name> §cto add / remove a friend.");
        }
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("l") || args[0].equalsIgnoreCase("list")) {
                for (final revamp.friend.Friend friend : FriendManager.friends) {
                    Revamp.getINSTANCE();
                    final ModuleManager moduleManager2 = Revamp.moduleManager;
                    ModuleManager.addChatMessage(false, "§2" + friend.name + " §7| " + (Revamp.getOnlinePlayers().contains(friend.name) ? "§aOnline" : "§cOffline"));
                }
                Revamp.getINSTANCE();
                final ModuleManager moduleManager3 = Revamp.moduleManager;
                ModuleManager.addChatMessage(false, "§7Total Friends: §2" + FriendManager.friends.size());
            }
            else {
                Revamp.getINSTANCE();
                final ModuleManager moduleManager4 = Revamp.moduleManager;
                ModuleManager.addChatMessage(true, "§cInvalid! Try §7.fr(iend) a(dd)/r(emove)/l(ist) §7<name> §cto add / remove a friend.");
            }
        }
        if (args.length > 1) {
            Label_0384: {
                final String s;
                switch (s = args[0]) {
                    case "remove": {
                        break Label_0384;
                    }
                    case "a": {
                        break;
                    }
                    case "r": {
                        break Label_0384;
                    }
                    case "add": {
                        break;
                    }
                }
                if (!FriendManager.isFriend(args[1]) && !args[1].equalsIgnoreCase(Minecraft.getMinecraft().thePlayer.getName())) {
                    FriendManager.addFriend(args[1]);
                    Revamp.getINSTANCE();
                    final ModuleManager moduleManager5 = Revamp.moduleManager;
                    ModuleManager.addChatMessage(false, "§aSuccess! §2" + args[1] + "§f is now your friend.");
                    return;
                }
                Revamp.getINSTANCE();
                final ModuleManager moduleManager6 = Revamp.moduleManager;
                ModuleManager.addChatMessage(true, "§cThis person is already your friend!");
                return;
            }
            if (FriendManager.isFriend(args[1])) {
                FriendManager.removeFriend(args[1]);
                Revamp.getINSTANCE();
                final ModuleManager moduleManager7 = Revamp.moduleManager;
                ModuleManager.addChatMessage(false, "§aSuccess! §2" + args[1] + "§f is no longer your friend.");
            }
            else {
                Revamp.getINSTANCE();
                final ModuleManager moduleManager8 = Revamp.moduleManager;
                ModuleManager.addChatMessage(true, "§cThis person isn't your friend!");
            }
        }
    }
}
