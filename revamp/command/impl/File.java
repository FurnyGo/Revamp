// 
// Decompiled by Procyon v0.5.36
// 

package revamp.command.impl;

import revamp.modules.ModuleManager;
import revamp.Revamp;
import revamp.command.Command;

public class File extends Command
{
    public File() {
        super("File", "Saves / Loads your saved files.", new String[] { "f" });
    }
    
    @Override
    public void onCommand(final String[] args, final String command) {
        if (args.length == 0) {
            Revamp.getINSTANCE();
            final ModuleManager moduleManager = Revamp.moduleManager;
            ModuleManager.addChatMessage(true, "§cInvalid! Try §7.f(ile) s(ave) / l(oad) §cto save / load files.");
        }
        if (args.length > 0) {
            Label_0147: {
                final String s;
                switch (s = args[0]) {
                    case "l": {
                        break;
                    }
                    case "s": {
                        break Label_0147;
                    }
                    case "load": {
                        break;
                    }
                    case "save": {
                        break Label_0147;
                    }
                }
                Revamp.fileManager.readAllFiles();
                Revamp.getINSTANCE();
                final ModuleManager moduleManager2 = Revamp.moduleManager;
                ModuleManager.addChatMessage(false, "§aSuccess! §fI have loaded your files.");
                return;
            }
            Revamp.fileManager.writeAllFiles();
            Revamp.getINSTANCE();
            final ModuleManager moduleManager3 = Revamp.moduleManager;
            ModuleManager.addChatMessage(false, "§aSuccess! §fI have saved your files.");
        }
    }
}
