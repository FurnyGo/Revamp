// 
// Decompiled by Procyon v0.5.36
// 

package revamp.command.impl;

import java.util.Iterator;
import revamp.modules.Module;
import revamp.modules.ModuleManager;
import revamp.Revamp;
import revamp.command.Command;

public class Toggle extends Command
{
    public Toggle() {
        super("Toggle", "Enable/Disable a module.", new String[] { "t" });
    }
    
    @Override
    public void onCommand(final String[] args, final String command) {
        if (args.length == 0) {
            Revamp.getINSTANCE();
            final ModuleManager moduleManager = Revamp.moduleManager;
            ModuleManager.addChatMessage(true, "§cInvalid! Try §7.t(oggle) <name> §cto toggle a module.");
        }
        if (args.length > 0) {
            boolean foundModule = false;
            Revamp.getINSTANCE();
            for (final Module module : Revamp.moduleManager.getModules()) {
                if (module.getName().equalsIgnoreCase(args[0])) {
                    foundModule = true;
                    module.toggle();
                    Revamp.getINSTANCE();
                    final ModuleManager moduleManager2 = Revamp.moduleManager;
                    ModuleManager.addChatMessage(false, "§bToggled §3" + module.getName() + (module.isToggled() ? " §2[§aEnabled§2]" : " §4[§cDisabled§4]"));
                    break;
                }
            }
            if (!foundModule) {
                Revamp.getINSTANCE();
                final ModuleManager moduleManager3 = Revamp.moduleManager;
                ModuleManager.addChatMessage(true, "§cInvalid Module!");
            }
        }
    }
}
