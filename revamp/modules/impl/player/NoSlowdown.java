// 
// Decompiled by Procyon v0.5.36
// 

package revamp.modules.impl.player;

import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import revamp.events.impl.EventPost;
import net.minecraft.network.Packet;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.BlockPos;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import revamp.events.impl.EventPre;
import net.minecraft.item.ItemBow;
import revamp.events.impl.EventUpdate;
import revamp.events.Event;
import revamp.modules.Category;
import revamp.userinterfaces.clickgui.settings.Setting;
import revamp.interfaces.Methods;
import revamp.modules.Module;

public class NoSlowdown extends Module implements Methods
{
    public Setting mode;
    
    public NoSlowdown() {
        super("NoSlowdown", "NoSlowdown", "Prevents you from being slow down.", -1, 0, Category.PLAYER);
        this.mode = new Setting("Mode", new String[] { "Vanilla", "AAC", "NCP" }, "Vanilla", this);
    }
    
    @Override
    public void onEvent(final Event event) {
        if (!this.isToggled()) {
            return;
        }
        if (event instanceof EventUpdate) {
            this.setNameWithOutSpace("NoSlowdown" + (this.information() ? (" §7" + this.mode.getCurrentMode()) : ""));
            if (this.getPlayer().getCurrentEquippedItem() != null && this.mode.getCurrentMode().equalsIgnoreCase("AAC")) {
                if (!this.isMoving()) {
                    return;
                }
                if (this.getPlayer().isBlocking() || this.getPlayer().isEating() || (this.getPlayer().isUsingItem() && this.getPlayer().getCurrentEquippedItem().getItem() instanceof ItemBow && this.getPlayer().onGround)) {
                    this.getPlayer().setSpeed(0.08f);
                }
            }
        }
        if (event instanceof EventPre && this.getPlayer().isBlocking() && this.mode.getCurrentMode().equalsIgnoreCase("NCP")) {
            this.sendPacket(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
        }
        if (event instanceof EventPost && this.getPlayer().isBlocking() && this.mode.getCurrentMode().equalsIgnoreCase("NCP")) {
            this.sendPacket(new C08PacketPlayerBlockPlacement(this.getPlayer().getHeldItem()));
        }
    }
}
