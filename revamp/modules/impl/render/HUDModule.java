// 
// Decompiled by Procyon v0.5.36
// 

package revamp.modules.impl.render;

import revamp.modules.Category;
import revamp.userinterfaces.clickgui.settings.Setting;
import revamp.modules.Module;

public class HUDModule extends Module
{
    public Setting title;
    public Setting titlemode;
    public Setting arraylist;
    public Setting listmode;
    public Setting showrender;
    public Setting showinfo;
    public Setting lowercase;
    public Setting animationsmode;
    public Setting hotbar;
    public Setting showfps;
    public Setting showxyz;
    public Setting showbps;
    public Setting rainbow;
    public Setting rainbowmode;
    
    public HUDModule() {
        super("HUD", "HUD", "Shows title, arraylist and more.", -1, 0, Category.RENDER);
        this.title = new Setting("Title", true, this);
        this.titlemode = new Setting("Title Mode", new String[] { "Revamp", "Skeet", "Jello" }, "Revamp", this);
        this.arraylist = new Setting("Arraylist", true, this);
        this.listmode = new Setting("Arraylist Mode", new String[] { "Revamp", "Clean", "Stacking" }, "Revamp", this);
        this.showrender = new Setting("Show Render Modules", true, this);
        this.showinfo = new Setting("Show Information", true, this);
        this.lowercase = new Setting("Lowercase", true, this);
        this.animationsmode = new Setting("Animations Mode", new String[] { "None", "Revamp" }, "Revamp", this);
        this.hotbar = new Setting("Hotbar", true, this);
        this.showfps = new Setting("Show FPS", true, this);
        this.showxyz = new Setting("Show XYZ", true, this);
        this.showbps = new Setting("Show BPS", true, this);
        this.rainbow = new Setting("Rainbow", true, this);
        this.rainbowmode = new Setting("Rainbow Mode", new String[] { "Regular", "Breathing" }, "Regular", this);
    }
}
