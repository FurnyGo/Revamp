// 
// Decompiled by Procyon v0.5.36
// 

package revamp.events.impl;

import net.minecraft.network.Packet;
import revamp.events.Event;

public class EventPacket extends Event
{
    private Packet packet;
    
    public EventPacket(final Packet packet) {
        this.packet = packet;
    }
    
    public Packet getPacket() {
        return this.packet;
    }
    
    public void setPacket(final Packet packet) {
        this.packet = packet;
    }
    
    public <T extends Packet> T chatPacket() {
        return (T)this.packet;
    }
}
