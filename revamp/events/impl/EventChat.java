// 
// Decompiled by Procyon v0.5.36
// 

package revamp.events.impl;

import revamp.events.CMDEvent;

public class EventChat extends CMDEvent
{
    public String message;
    
    public EventChat(final String message) {
        this.message = message;
    }
    
    public String getMessage() {
        return this.message;
    }
    
    public void setMessage(final String message) {
        this.message = message;
    }
}
