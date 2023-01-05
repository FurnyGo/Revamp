// 
// Decompiled by Procyon v0.5.36
// 

package revamp.modules.impl.render;

import net.minecraft.util.ResourceLocation;
import revamp.events.impl.EventUpdate;
import revamp.events.Event;
import revamp.modules.Category;
import revamp.userinterfaces.clickgui.settings.Setting;
import revamp.interfaces.Methods;
import revamp.modules.Module;

public class Cape extends Module implements Methods
{
    public Setting mode;
    
    public Cape() {
        super("Cape", "Cape", "Renders a client-side cape.", -1, 0, Category.RENDER);
        this.mode = new Setting("Mode", new String[] { "Starry Night", "Raindrops", "Mountains", "Moon" }, "Starry Night", this);
    }
    
    @Override
    public void onEvent(final Event event) {
        if (!this.isToggled()) {
            return;
        }
        if (event instanceof EventUpdate) {
            this.setCape(new ResourceLocation("revamp/capes/" + this.mode.getCurrentMode().replace(" ", "").toLowerCase() + ".png"));
        }
    }
    
    @Override
    public void onDisable() {
        this.setCape(null);
    }
}
