// 
// Decompiled by Procyon v0.5.36
// 

package revamp.command.impl;

import java.util.Iterator;
import revamp.modules.Module;
import revamp.modules.ModuleManager;
import revamp.Revamp;
import revamp.command.Command;

public class Description extends Command
{
    public Description() {
        super("Description", "Shows a desc. for any module.", new String[] { "desc" });
    }
    
    @Override
    public void onCommand(final String[] args, final String command) {
        if (args.length == 0) {
            Revamp.getINSTANCE();
            final ModuleManager moduleManager = Revamp.moduleManager;
            ModuleManager.addChatMessage(true, "§cInvalid! Try §7.desc(ription) <name> §cto show a description of a module.");
        }
        if (args.length > 0) {
            boolean module = false;
            Revamp.getINSTANCE();
            for (final Module m : Revamp.moduleManager.getModules()) {
                if (m.getName().equalsIgnoreCase(args[0])) {
                    Revamp.getINSTANCE();
                    final ModuleManager moduleManager2 = Revamp.moduleManager;
                    ModuleManager.addChatMessage(false, "§9" + m.getName() + " §r- §1" + m.getDescription());
                    module = true;
                }
            }
            if (!module) {
                Revamp.getINSTANCE();
                final ModuleManager moduleManager3 = Revamp.moduleManager;
                ModuleManager.addChatMessage(true, "§cInvalid Module!");
            }
        }
    }
}
