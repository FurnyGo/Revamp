// 
// Decompiled by Procyon v0.5.36
// 

package revamp.modules.impl.render;

import revamp.modules.Category;
import revamp.userinterfaces.clickgui.settings.Setting;
import revamp.modules.Module;

public class ScoreboardModule extends Module
{
    public Setting background;
    public Setting hide;
    public Setting change;
    
    public ScoreboardModule() {
        super("Scoreboard", "Scoreboard", "Modifies the scoreboard.", -1, 0, Category.RENDER);
        this.background = new Setting("Background", true, this);
        this.hide = new Setting("Hide Numbers", true, this);
        this.change = new Setting("Change URL", true, this);
    }
}
