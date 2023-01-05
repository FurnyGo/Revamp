// 
// Decompiled by Procyon v0.5.36
// 

package revamp.events;

import java.util.Iterator;
import revamp.modules.Module;
import revamp.Revamp;

public class Event
{
    public static boolean cancelled;
    
    public static boolean isCancelled() {
        return Event.cancelled;
    }
    
    public void setCancelled(final boolean cancelled) {
        Event.cancelled = cancelled;
    }
    
    public void onFire() {
        Revamp.getINSTANCE();
        for (final Module module : Revamp.moduleManager.getModules()) {
            module.onEvent(this);
        }
    }
}
