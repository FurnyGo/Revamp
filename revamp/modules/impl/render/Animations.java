// 
// Decompiled by Procyon v0.5.36
// 

package revamp.modules.impl.render;

import revamp.modules.Category;
import revamp.userinterfaces.clickgui.settings.Setting;
import revamp.modules.Module;

public class Animations extends Module
{
    public Setting mode;
    
    public Animations() {
        super("Animations", "Animations", "Adds animations when autoblocking.", -1, 0, Category.RENDER);
        this.mode = new Setting("Mode", new String[] { "Revamp", "Old Revamp", "Spin", "Down", "Exhibition" }, "Revamp", this);
    }
}
