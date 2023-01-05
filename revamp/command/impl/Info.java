// 
// Decompiled by Procyon v0.5.36
// 

package revamp.command.impl;

import revamp.updater.Checks;
import revamp.modules.ModuleManager;
import revamp.Revamp;
import revamp.command.Command;

public class Info extends Command
{
    public Info() {
        super("Info", "Shows info about Revamp.", new String[] { "i" });
    }
    
    @Override
    public void onCommand(final String[] args, final String command) {
        Revamp.getINSTANCE();
        final ModuleManager moduleManager = Revamp.moduleManager;
        ModuleManager.addChatMessage(false, "§3-§b-§3-§b-§3-§b-§3-§b-§3-");
        Revamp.getINSTANCE();
        final ModuleManager moduleManager2 = Revamp.moduleManager;
        ModuleManager.addChatMessage(false, "§b" + Revamp.getNAME() + " §3" + Revamp.getVERSION() + (Checks.versionCheck() ? " §4[§cOutdated§4]" : " §2[§aUpdated§2]"));
        Revamp.getINSTANCE();
        final ModuleManager moduleManager3 = Revamp.moduleManager;
        ModuleManager.addChatMessage(false, "§bCreated by §3" + Revamp.getAUTHOR());
        Revamp.getINSTANCE();
        final ModuleManager moduleManager4 = Revamp.moduleManager;
        ModuleManager.addChatMessage(false, "§bSigned in as §3" + Revamp.getUSERNAME());
        Revamp.getINSTANCE();
        final ModuleManager moduleManager5 = Revamp.moduleManager;
        ModuleManager.addChatMessage(false, "§bLoaded Modules: §3" + Revamp.moduleManager.getModules().size());
        Revamp.getINSTANCE();
        final ModuleManager moduleManager6 = Revamp.moduleManager;
        ModuleManager.addChatMessage(false, "§bLoaded Settings: §3" + Revamp.settingManager.getSetting().size());
        Revamp.getINSTANCE();
        final ModuleManager moduleManager7 = Revamp.moduleManager;
        ModuleManager.addChatMessage(false, "§bClient Color: §3" + Revamp.getClientCOLOR().toString().substring(15, 30).replace("r=", "red = ").replace("g=", "green = ").replace("b=", "blue = ").replace(",", ", "));
        Revamp.getINSTANCE();
        final ModuleManager moduleManager8 = Revamp.moduleManager;
        ModuleManager.addChatMessage(false, "§b-§3-§b-§3-§b-§3-§b-§3-§b-");
    }
}
