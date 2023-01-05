// 
// Decompiled by Procyon v0.5.36
// 

package revamp.events.impl;

import revamp.events.Event;

public class EventRender extends Event
{
    private float partialTicks;
    
    public EventRender(final float partialTicks) {
        this.partialTicks = partialTicks;
    }
    
    public float getPartialTicks() {
        return this.partialTicks;
    }
}
