// 
// Decompiled by Procyon v0.5.36
// 

package revamp.modules.impl.world;

import revamp.events.impl.EventUpdate;
import revamp.events.Event;
import revamp.modules.Category;
import revamp.userinterfaces.clickgui.settings.Setting;
import revamp.interfaces.Methods;
import revamp.modules.Module;

public class Timer extends Module implements Methods
{
    public Setting speed;
    
    public Timer() {
        super("Timer", "Timer", "Changes the game speed.", -1, 0, Category.WORLD);
        this.speed = new Setting("Speed", 2.0f, 0.1f, 10.0f, false, this);
    }
    
    @Override
    public void onEvent(final Event event) {
        if (!this.isToggled()) {
            return;
        }
        if (event instanceof EventUpdate) {
            this.setNameWithOutSpace("Timer" + (this.information() ? (" §7" + this.speed.getCurrentValue()) : ""));
            this.getTimer().timerSpeed = this.speed.getCurrentValue();
        }
    }
    
    @Override
    public void onDisable() {
        this.getTimer().timerSpeed = 1.0f;
    }
}
