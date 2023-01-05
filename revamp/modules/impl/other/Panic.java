// 
// Decompiled by Procyon v0.5.36
// 

package revamp.modules.impl.other;

import java.util.Iterator;
import revamp.Revamp;
import revamp.modules.Category;
import revamp.modules.Module;

public class Panic extends Module
{
    public Panic() {
        super("Panic", "Panic", "Turns off all modules.", -1, 0, Category.OTHER);
    }
    
    @Override
    public void onEnable() {
        Revamp.getINSTANCE();
        for (final Module module : Revamp.moduleManager.getModules()) {
            module.onDisable();
            module.setToggled(false);
        }
    }
}
