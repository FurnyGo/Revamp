// 
// Decompiled by Procyon v0.5.36
// 

package revamp.command.impl;

import java.util.Iterator;
import revamp.command.CommandManager;
import revamp.modules.ModuleManager;
import revamp.Revamp;
import revamp.command.Command;

public class Help extends Command
{
    public Help() {
        super("Help", "Shows a list of commands.", new String[] { "h" });
    }
    
    @Override
    public void onCommand(final String[] args, final String command) {
        Revamp.getINSTANCE();
        final ModuleManager moduleManager = Revamp.moduleManager;
        ModuleManager.addChatMessage(false, "§3-§b-§3-§b-§3-§b-§3-§b-§3-");
        for (final Command cmd : CommandManager.commands) {
            Revamp.getINSTANCE();
            final ModuleManager moduleManager2 = Revamp.moduleManager;
            ModuleManager.addChatMessage(false, "§b" + cmd.getName() + " | §3" + cmd.getDescription() + " §1" + cmd.getAlias());
        }
        Revamp.getINSTANCE();
        final ModuleManager moduleManager3 = Revamp.moduleManager;
        ModuleManager.addChatMessage(false, "§b-§3-§b-§3-§b-§3-§b-§3-§b-");
    }
}
