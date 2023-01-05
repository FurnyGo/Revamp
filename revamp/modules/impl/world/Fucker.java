// 
// Decompiled by Procyon v0.5.36
// 

package revamp.modules.impl.world;

import net.minecraft.network.Packet;
import net.minecraft.util.EnumFacing;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;
import revamp.events.impl.EventUpdate;
import revamp.events.Event;
import revamp.modules.Category;
import revamp.userinterfaces.clickgui.settings.Setting;
import revamp.interfaces.Methods;
import revamp.modules.Module;

public class Fucker extends Module implements Methods
{
    private double x;
    private double y;
    private double z;
    public Setting beds;
    public Setting eggs;
    public Setting radius;
    
    public Fucker() {
        super("Fucker", "Fucker", "Breaks beds for you.", -1, 0, Category.WORLD);
        this.beds = new Setting("Beds", true, this);
        this.eggs = new Setting("Eggs", false, this);
        this.radius = new Setting("Radius", 3.0f, 1.0f, 6.0f, false, this);
    }
    
    @Override
    public void onEvent(final Event event) {
        if (!this.isToggled()) {
            return;
        }
        if (event instanceof EventUpdate) {
            this.setNameWithOutSpace("Fucker" + (this.information() ? (" §7" + this.radius.getCurrentValue()) : ""));
            for (double x = -this.radius.getCurrentValue(); x < this.radius.getCurrentValue(); ++x) {
                for (double y = this.radius.getCurrentValue(); y > -this.radius.getCurrentValue(); --y) {
                    for (double z = -this.radius.getCurrentValue(); z < this.radius.getCurrentValue(); ++z) {
                        this.x = this.getX() + x;
                        this.y = this.getY() + y;
                        this.z = this.getZ() + z;
                        final BlockPos pos = new BlockPos(this.x, this.y, this.z);
                        final Block block = this.getWorld().getBlockState(pos).getBlock();
                        if ((block.getBlockState().getBlock() == Block.getBlockById(26) && this.beds.isToggled()) || (block.getBlockState().getBlock() == Block.getBlockById(122) && this.eggs.isToggled())) {
                            this.sendPacket(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, pos, EnumFacing.NORTH));
                            this.sendPacket(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, pos, EnumFacing.NORTH));
                            this.getPlayer().swingItem();
                        }
                    }
                }
            }
        }
    }
}
