// 
// Decompiled by Procyon v0.5.36
// 

package revamp.modules.impl.combat;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C02PacketUseEntity;
import revamp.events.impl.EventPacket;
import revamp.events.Event;
import revamp.modules.Category;
import revamp.userinterfaces.clickgui.settings.Setting;
import revamp.interfaces.Methods;
import revamp.modules.Module;

public class Criticals extends Module implements Methods
{
    public Setting mode;
    
    public Criticals() {
        super("Criticals", "Criticals", "Hits players with more damage.", -1, 0, Category.COMBAT);
        this.mode = new Setting("Mode", new String[] { "Packet", "MiniJump" }, "Packet", this);
    }
    
    @Override
    public void onEvent(final Event event) {
        if (!this.isToggled()) {
            return;
        }
        if (event instanceof EventPacket) {
            this.setNameWithOutSpace("Criticals" + (this.information() ? (" §7" + this.mode.getCurrentMode()) : ""));
            if (this.isIngame() && !this.isInLiquid() && this.onGround() && ((EventPacket)event).getPacket() instanceof C02PacketUseEntity && ((C02PacketUseEntity)((EventPacket)event).getPacket()).getAction() == C02PacketUseEntity.Action.ATTACK) {
                if (this.mode.getCurrentMode().equalsIgnoreCase("Packet")) {
                    this.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(this.getX(), this.getY() + 0.0625, this.getZ(), true));
                    this.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(this.getX(), this.getY(), this.getZ(), false));
                    this.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(this.getX(), this.getY() + 1.1E-5, this.getZ(), false));
                    this.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(this.getX(), this.getY(), this.getZ(), false));
                }
                else if (this.mode.getCurrentMode().equalsIgnoreCase("MiniJump")) {
                    this.jump();
                    final EntityPlayerSP player = this.getPlayer();
                    player.motionY -= 0.30000001192092896;
                }
            }
        }
    }
}
