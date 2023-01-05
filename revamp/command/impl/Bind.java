// 
// Decompiled by Procyon v0.5.36
// 

package revamp.command.impl;

import java.util.Iterator;
import org.lwjgl.input.Keyboard;
import revamp.modules.Module;
import revamp.modules.ModuleManager;
import revamp.Revamp;
import revamp.command.Command;

public class Bind extends Command
{
    public Bind() {
        super("Bind", "Binds modules into a key.", new String[] { "b" });
    }
    
    @Override
    public void onCommand(final String[] args, final String command) {
        if (args.length == 0) {
            Revamp.getINSTANCE();
            final ModuleManager moduleManager = Revamp.moduleManager;
            ModuleManager.addChatMessage(true, "§cInvalid! Try §7.b(ind) <module> <key> §cto bind a §cmodule or §7.b(ind) clear §cto clear all binds.");
        }
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("clear")) {
                Revamp.getINSTANCE();
                for (final Module m : Revamp.moduleManager.getModules()) {
                    m.setKey(0);
                }
                Revamp.getINSTANCE();
                Revamp.moduleManager.getModuleByName("ClickGUI").setKey(54);
                Revamp.getINSTANCE();
                final ModuleManager moduleManager2 = Revamp.moduleManager;
                ModuleManager.addChatMessage(false, "§aCleared all binds!");
            }
            else {
                Revamp.getINSTANCE();
                final ModuleManager moduleManager3 = Revamp.moduleManager;
                ModuleManager.addChatMessage(true, "§cInvalid! Try §7.b(ind) <module> <key> §cto bind a §cmodule or §7.b(ind) clear §cto clear all binds.");
            }
        }
        if (args.length == 2) {
            boolean module = false;
            Revamp.getINSTANCE();
            for (final Module i : Revamp.moduleManager.getModules()) {
                if (i.getName().equalsIgnoreCase(args[0])) {
                    i.setKey(Keyboard.getKeyIndex(args[1].toUpperCase()));
                    Revamp.getINSTANCE();
                    final ModuleManager moduleManager4 = Revamp.moduleManager;
                    ModuleManager.addChatMessage(false, "§aSuccess! §7" + i.getName() + " §fis now binded to §7" + Keyboard.getKeyName(i.getKey()) + "§f.");
                    module = true;
                    break;
                }
            }
            if (!module) {
                Revamp.getINSTANCE();
                final ModuleManager moduleManager5 = Revamp.moduleManager;
                ModuleManager.addChatMessage(true, "§cInvalid Module!");
            }
        }
    }
}
