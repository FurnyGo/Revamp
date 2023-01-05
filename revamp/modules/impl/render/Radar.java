// 
// Decompiled by Procyon v0.5.36
// 

package revamp.modules.impl.render;

import revamp.modules.Category;
import revamp.userinterfaces.clickgui.settings.Setting;
import revamp.modules.Module;

public class Radar extends Module
{
    public Setting posx;
    public Setting posy;
    public Setting opacity;
    
    public Radar() {
        super("Radar", "Radar", "Shows near players.", -1, 0, Category.RENDER);
        this.posx = new Setting("X Pos", 140.0f, 1.0f, 1000.0f, false, this);
        this.posy = new Setting("Y Pos", 280.0f, 1.0f, 1000.0f, false, this);
        this.opacity = new Setting("Opacity", 80.0f, 0.0f, 150.0f, true, this);
    }
}
