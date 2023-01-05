// 
// Decompiled by Procyon v0.5.36
// 

package revamp.events.impl;

import revamp.events.Event;

public class EventKeyPress extends Event
{
    private int key;
    
    public EventKeyPress(final int key) {
        this.key = key;
    }
    
    public int getKey() {
        return this.key;
    }
}
