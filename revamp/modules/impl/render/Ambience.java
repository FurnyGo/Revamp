// 
// Decompiled by Procyon v0.5.36
// 

package revamp.modules.impl.render;

import net.minecraft.network.play.server.S03PacketTimeUpdate;
import revamp.events.impl.EventPacket;
import revamp.events.Event;
import revamp.modules.Category;
import revamp.userinterfaces.clickgui.settings.Setting;
import revamp.interfaces.Methods;
import revamp.modules.Module;

public class Ambience extends Module implements Methods
{
    public Setting time;
    
    public Ambience() {
        super("Ambience", "Ambience", "Changes the time client-side.", -1, 0, Category.RENDER);
        this.time = new Setting("New Time  ", 5000.0f, 0.0f, 24000.0f, true, this);
    }
    
    @Override
    public void onEvent(final Event event) {
        if (!this.isToggled()) {
            return;
        }
        if (event instanceof EventPacket) {
            this.setNameWithOutSpace("Ambience" + (this.information() ? (" §7" + this.time.getCurrentValue()) : ""));
            if (this.getWorld() == null) {
                return;
            }
            if (((EventPacket)event).getPacket() instanceof S03PacketTimeUpdate) {
                ((S03PacketTimeUpdate)((EventPacket)event).getPacket()).worldTime = (long)this.time.getCurrentValue();
            }
            this.getWorld().setWorldTime((long)this.time.getCurrentValue());
        }
    }
}
