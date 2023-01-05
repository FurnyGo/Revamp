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

public class Hitboxes extends Module implements Methods
{
    public Setting hitbox;
    
    public Hitboxes() {
        super("Hitboxes", "Hitboxes", "Expands the hitbox for entities.", -1, 0, Category.COMBAT);
        this.hitbox = new Setting("Hitbox", 0.3f, 0.0f, 1.0f, false, this);
    }
    
    @Override
    public void onEvent(final Event event) {
        if (!this.isToggled()) {
            return;
        }
        if (event instanceof EventUpdate) {
            this.setNameWithOutSpace("Hitboxes" + (this.information() ? (" §7" + this.hitbox.getCurrentValue()) : ""));
        }
    }
}
