// 
// Decompiled by Procyon v0.5.36
// 

package revamp.modules.impl.player;

import net.minecraft.util.BlockPos;
import net.minecraft.block.BlockAir;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import revamp.events.impl.EventUpdate;
import revamp.events.Event;
import revamp.modules.Category;
import revamp.userinterfaces.clickgui.settings.Setting;
import revamp.interfaces.Methods;
import revamp.modules.Module;

public class NoFall extends Module implements Methods
{
    private boolean blockUnder;
    public Setting mode;
    
    public NoFall() {
        super("NoFall", "NoFall", "Prevents fall damage.", -1, 0, Category.PLAYER);
        this.mode = new Setting("Mode", new String[] { "Packet", "AAC 3.3", "Spoof" }, "Vanilla", this);
    }
    
    @Override
    public void onEvent(final Event event) {
        if (!this.isToggled()) {
            return;
        }
        if (event instanceof EventUpdate) {
            this.setNameWithOutSpace("NoFall" + (this.information() ? (" §7" + this.mode.getCurrentMode()) : ""));
            if (this.mode.getCurrentMode().equalsIgnoreCase("Packet")) {
                if (this.getCapabilities().isFlying) {
                    return;
                }
                if (this.getPlayer().fallDistance > 2.5f) {
                    this.sendPacket(new C03PacketPlayer(true));
                }
            }
            if (this.mode.getCurrentMode().equalsIgnoreCase("AAC 3.3") && this.getPlayer().fallDistance > 2.5f) {
                this.getPlayer().motionZ = 0.0;
                this.getPlayer().motionX = 0.0;
                this.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(this.getX(), this.getY() - 0.001, this.getZ(), this.onGround()));
                this.sendPacket(new C03PacketPlayer(true));
            }
            if (this.mode.getCurrentMode().equalsIgnoreCase("Spoof")) {
                if (this.getCapabilities().isFlying || this.getGameSettings().keyBindJump.isKeyDown()) {
                    return;
                }
                for (int i = (int)(this.getY() - 1.0); i > 0; --i) {
                    if (!(this.getWorld().getBlockState(new BlockPos(this.getX(), i, this.getZ())).getBlock() instanceof BlockAir)) {
                        this.blockUnder = true;
                    }
                    else {
                        this.blockUnder = false;
                    }
                }
                if (this.getPlayer().fallDistance > 2.5f && this.blockUnder) {
                    this.getPlayer().fallDistance = 0.0f;
                    this.getPlayer().onGround = true;
                }
            }
        }
    }
}
