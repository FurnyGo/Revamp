// 
// Decompiled by Procyon v0.5.36
// 

package revamp.modules.impl.render;

import revamp.modules.Category;
import revamp.userinterfaces.clickgui.settings.Setting;
import revamp.interfaces.Methods;
import revamp.modules.Module;

public class TargetHUD extends Module implements Methods
{
    public Setting mode;
    public Setting posx;
    public Setting posy;
    public Setting opacity;
    public Setting health;
    public Setting armor;
    public Setting bar;
    public Setting ping;
    
    public TargetHUD() {
        super("TargetHUD", "TargetHUD", "Shows a overlay of the target.", -1, 0, Category.RENDER);
        this.mode = new Setting("Mode", new String[] { "Revamp", "Dev" }, "Revamp", this);
        this.posx = new Setting("X Pos", 140.0f, 1.0f, 1000.0f, false, this);
        this.posy = new Setting("Y Pos", 280.0f, 1.0f, 1000.0f, false, this);
        this.opacity = new Setting("Opacity", 80.0f, 0.0f, 150.0f, true, this);
        this.health = new Setting("Show Health", false, this);
        this.armor = new Setting("Show Armor", false, this);
        this.bar = new Setting("Show Bar", false, this);
        this.ping = new Setting("Show Ping", false, this);
    }
}
