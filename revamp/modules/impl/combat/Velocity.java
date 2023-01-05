// 
// Decompiled by Procyon v0.5.36
// 

package revamp.modules.impl.combat;

import net.minecraft.network.play.server.S12PacketEntityVelocity;
import revamp.events.impl.EventPacket;
import revamp.events.impl.EventUpdate;
import revamp.events.Event;
import revamp.modules.Category;
import revamp.userinterfaces.clickgui.settings.Setting;
import revamp.interfaces.Methods;
import revamp.modules.Module;

public class Velocity extends Module implements Methods
{
    private double motionX;
    private double motionZ;
    public Setting mode;
    public Setting horizontal;
    public Setting vertical;
    
    public Velocity() {
        super("Velocity", "Velocity", "Reduces knockback.", -1, 0, Category.COMBAT);
        this.mode = new Setting("Mode", new String[] { "Hypixel", "AAC 3.3", "Custom" }, "Hypixel", this);
        this.horizontal = new Setting("Horizontal", 95.0f, 0.0f, 100.0f, true, this);
        this.vertical = new Setting("Vertical", 95.0f, 0.0f, 100.0f, true, this);
    }
    
    @Override
    public void onEvent(final Event event) {
        if (!this.isToggled()) {
            return;
        }
        if (event instanceof EventUpdate) {
            this.setNameWithOutSpace("Velocity" + (this.information() ? (" §7" + this.mode.getCurrentMode()) : ""));
            if (this.mode.getCurrentMode().equalsIgnoreCase("Hypixel")) {
                if (this.getHurtTime() == 9) {
                    this.motionX = this.getPlayer().motionX;
                    this.motionZ = this.getPlayer().motionZ;
                }
                else if (this.getHurtTime() == 8) {
                    this.getPlayer().motionX = -this.motionX * 0.45;
                    this.getPlayer().motionZ = -this.motionZ * 0.45;
                }
            }
            if (this.mode.getCurrentMode().equalsIgnoreCase("AAC 3.3") && this.hurtTimeIsnt9()) {
                final double yaw = Math.toRadians(this.getPlayer().rotationYawHead);
                this.getPlayer().motionX = -Math.sin(yaw) * 0.05;
                this.getPlayer().motionZ = -Math.cos(yaw) * 0.05;
            }
        }
        if (event instanceof EventPacket && this.mode.getCurrentMode().equalsIgnoreCase("Custom") && ((EventPacket)event).getPacket() instanceof S12PacketEntityVelocity) {
            final S12PacketEntityVelocity packet = (S12PacketEntityVelocity)((EventPacket)event).getPacket();
            if (this.getWorld().getEntityByID(packet.getEntityID()) == this.getPlayer()) {
                final S12PacketEntityVelocity s12PacketEntityVelocity = packet;
                s12PacketEntityVelocity.motionX *= (int)(this.horizontal.getCurrentValue() / 100.0f * 2.0f);
                final S12PacketEntityVelocity s12PacketEntityVelocity2 = packet;
                s12PacketEntityVelocity2.motionY *= (int)(this.vertical.getCurrentValue() / 100.0f * 2.0f);
                final S12PacketEntityVelocity s12PacketEntityVelocity3 = packet;
                s12PacketEntityVelocity3.motionZ *= (int)(this.horizontal.getCurrentValue() / 100.0f * 2.0f);
            }
        }
    }
}
