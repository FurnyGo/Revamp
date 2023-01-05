// 
// Decompiled by Procyon v0.5.36
// 

package revamp.modules.impl.render;

import revamp.events.impl.EventUpdate;
import revamp.events.Event;
import revamp.modules.Category;
import revamp.userinterfaces.clickgui.settings.Setting;
import revamp.interfaces.Methods;
import revamp.modules.Module;

public class Animations extends Module implements Methods
{
    public Setting mode;
    
    public Animations() {
        super("Animations", "Animations", "Adds animations when autoblocking.", -1, 0, Category.RENDER);
        this.mode = new Setting("Mode", new String[] { "Revamp", "Old Revamp", "Down", "Exhibition", "1.7" }, "Revamp", this);
    }
    
    @Override
    public void onEvent(final Event event) {
        if (!this.isToggled()) {
            return;
        }
        if (event instanceof EventUpdate) {
            this.setNameWithOutSpace("Animations" + (this.information() ? (" §7" + this.mode.getCurrentMode()) : ""));
        }
    }
}
