// 
// Decompiled by Procyon v0.5.36
// 

package revamp.modules.impl.combat;

import revamp.events.impl.EventUpdate;
import revamp.events.Event;
import revamp.modules.Category;
import revamp.userinterfaces.clickgui.settings.Setting;
import revamp.interfaces.Methods;
import revamp.modules.Module;

public class Reach extends Module implements Methods
{
    public Setting reach;
    
    public Reach() {
        super("Reach", "Reach", "Extends your hit range.", -1, 0, Category.COMBAT);
        this.reach = new Setting("Reach", 3.0f, 1.0f, 6.0f, false, this);
    }
    
    @Override
    public void onEvent(final Event event) {
        if (!this.isToggled()) {
            return;
        }
        if (event instanceof EventUpdate) {
            this.setNameWithOutSpace("Reach" + (this.information() ? (" §7" + this.reach.getCurrentValue()) : ""));
        }
    }
}
