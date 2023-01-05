// 
// Decompiled by Procyon v0.5.36
// 

package revamp.modules.impl.render;

import net.minecraft.client.gui.GuiScreen;
import revamp.Revamp;
import revamp.modules.Category;
import revamp.userinterfaces.clickgui.settings.Setting;
import revamp.interfaces.Methods;
import revamp.modules.Module;

public class ClickGUI extends Module implements Methods
{
    public Setting rainbow;
    public Setting blur;
    public Setting anime;
    public Setting mode;
    
    public ClickGUI() {
        super("ClickGUI", "ClickGUI", "Renders the ClickGUI.", -1, 54, Category.RENDER);
        this.rainbow = new Setting("Rainbow", true, this);
        this.blur = new Setting("Blur", true, this);
        this.anime = new Setting("Anime", false, this);
        this.mode = new Setting("Character", new String[] { "Astolfo", "Killua", "Levi", "Eren", "Rias", "ZeroTwo", "Yor", "Esdeath" }, "Astolfo", this);
    }
    
    @Override
    public void onEnable() {
        if (this.getCurrentScreen() == null) {
            Revamp.getINSTANCE();
            this.displayScreen(Revamp.clickGUI);
        }
    }
}
