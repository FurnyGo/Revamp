// 
// Decompiled by Procyon v0.5.36
// 

package revamp.modules.impl.movement;

import revamp.events.impl.EventUpdate;
import revamp.events.Event;
import revamp.modules.Category;
import revamp.interfaces.Methods;
import revamp.modules.Module;

public class Sprint extends Module implements Methods
{
    public Sprint() {
        super("Sprint", "Sprint", "Automatically sprints for you.", -1, 0, Category.MOVEMENT);
    }
    
    @Override
    public void onEvent(final Event event) {
        if (!this.isToggled()) {
            return;
        }
        if (event instanceof EventUpdate && this.isMoving() && (this.getPlayer().getFoodStats().getFoodLevel() > 6 || this.getCapabilities().isCreativeMode)) {
            this.getPlayer().setSprinting(true);
        }
    }
    
    @Override
    public void onDisable() {
        this.getPlayer().setSprinting(false);
    }
}
