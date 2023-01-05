// 
// Decompiled by Procyon v0.5.36
// 

package revamp.events.impl;

import revamp.events.Event;

public class EventText extends Event
{
    public String text;
    
    public EventText(final String text) {
        this.text = text;
    }
    
    public String getText() {
        return this.text;
    }
    
    public void setText(final String text) {
        this.text = text;
    }
}
