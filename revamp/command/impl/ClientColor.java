// 
// Decompiled by Procyon v0.5.36
// 

package revamp.command.impl;

import java.awt.Color;
import revamp.modules.ModuleManager;
import revamp.Revamp;
import revamp.command.Command;

public class ClientColor extends Command
{
    public ClientColor() {
        super("ClientColor", "Changes the client color.", new String[] { "cc" });
    }
    
    @Override
    public void onCommand(final String[] args, final String command) {
        if (args.length == 0) {
            Revamp.getINSTANCE();
            final ModuleManager moduleManager = Revamp.moduleManager;
            ModuleManager.addChatMessage(true, "§cInvalid! Try §7.cc / .clientcolor <red> <green> <blue> §cto change the client color.");
        }
        if (args.length < 3) {
            Revamp.getINSTANCE();
            final ModuleManager moduleManager2 = Revamp.moduleManager;
            ModuleManager.addChatMessage(true, "§cInvalid! Try §7.cc / .clientcolor <red> <green> <blue> §cto change the client color.");
        }
        if (args.length == 3) {
            Revamp.setClientCOLOR(new Color(Integer.parseInt(args[0].replace(",", "")), Integer.parseInt(args[1].replace(",", "")), Integer.parseInt(args[2].replace(",", ""))));
            Revamp.getINSTANCE();
            final ModuleManager moduleManager3 = Revamp.moduleManager;
            ModuleManager.addChatMessage(false, "§aSuccess! §fI have changed the client color to: §c" + args[0].replace(",", "") + " §a" + args[1].replace(",", "") + " §b" + args[2].replace(",", ""));
        }
    }
}
