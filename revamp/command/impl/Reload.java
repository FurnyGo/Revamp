// 
// Decompiled by Procyon v0.5.36
// 

package revamp.command.impl;

import revamp.modules.ModuleManager;
import revamp.Revamp;
import revamp.command.Command;

public class Reload extends Command
{
    public Reload() {
        super("Reload", "Reloads the client.", new String[] { "rl" });
    }
    
    @Override
    public void onCommand(final String[] args, final String command) {
        Revamp.getINSTANCE().shutdown();
        Revamp.getINSTANCE().setup();
        Revamp.getModuleManager();
        ModuleManager.addChatMessage(false, "§aReloaded! §c[Please restart if this client becomes buggy.]");
    }
}
