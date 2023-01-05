// 
// Decompiled by Procyon v0.5.36
// 

package revamp.events;

public class CMDEvent<T>
{
    public boolean cancelled;
    
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    public void setCancelled(final boolean cancelled) {
        this.cancelled = cancelled;
    }
}
