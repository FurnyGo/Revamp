// 
// Decompiled by Procyon v0.5.36
// 

package revamp.modules.impl.movement;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.Vec3i;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import revamp.events.impl.EventUpdate;
import net.minecraft.util.BlockPos;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemBlock;
import revamp.events.impl.EventPre;
import revamp.events.Event;
import revamp.modules.Category;
import revamp.interfaces.Methods;
import revamp.modules.Module;

public class Scaffold extends Module implements Methods
{
    public int mode;
    public int count;
    
    public Scaffold() {
        super("Scaffold", "Scaffold", "Bridges for you.", -1, 0, Category.MOVEMENT);
        this.mode = 0;
        this.count = 0;
    }
    
    @Override
    public void onEvent(final Event event) {
        if (!this.isToggled()) {
            return;
        }
        if (event instanceof EventPre) {
            if (this.getPlayer().getHeldItem().getItem() == null && !(this.getPlayer().getHeldItem().getItem() instanceof ItemBlock) && this.mode == 0) {
                return;
            }
            double x = ((EventPre)event).getX();
            final double y = ((EventPre)event).getY();
            double z = ((EventPre)event).getZ();
            Label_0422: {
                if (this.getPlayer().onGround) {
                    final double increment = 0.05;
                    while (true) {
                        while (x != 0.0) {
                            if (!this.getWorld().getCollidingBoundingBoxes(this.getPlayer(), this.getPlayer().getEntityBoundingBox().offset(x, -1.0, 0.0)).isEmpty()) {
                                while (true) {
                                    while (z != 0.0) {
                                        if (!this.getWorld().getCollidingBoundingBoxes(this.getPlayer(), this.getPlayer().getEntityBoundingBox().offset(0.0, -1.0, z)).isEmpty()) {
                                            while (x != 0.0 && z != 0.0 && this.getWorld().getCollidingBoundingBoxes(this.getPlayer(), this.getPlayer().getEntityBoundingBox().offset(x, -1.0, z)).isEmpty()) {
                                                if (x < 0.05 && x >= -0.05) {
                                                    x = 0.0;
                                                }
                                                else if (x > 0.0) {
                                                    x -= 0.05;
                                                }
                                                else {
                                                    x += 0.05;
                                                }
                                                if (z < 0.05 && z >= -0.05) {
                                                    z = 0.0;
                                                }
                                                else if (z > 0.0) {
                                                    z -= 0.05;
                                                }
                                                else {
                                                    z += 0.05;
                                                }
                                            }
                                            break Label_0422;
                                        }
                                        if (z < 0.05 && z >= -0.05) {
                                            z = 0.0;
                                        }
                                        else if (z > 0.0) {
                                            z -= 0.05;
                                        }
                                        else {
                                            z += 0.05;
                                        }
                                    }
                                    continue;
                                }
                            }
                            if (x < 0.05 && x >= -0.05) {
                                x = 0.0;
                            }
                            else if (x > 0.0) {
                                x -= 0.05;
                            }
                            else {
                                x += 0.05;
                            }
                        }
                        continue;
                    }
                }
            }
            switch (this.mode = 1) {
                case 0:
                case 1: {
                    ((EventPre)event).setPitch(90.0f);
                    final BlockPos belowPlayer = new BlockPos(this.getPlayer()).offsetDown();
                    if (!this.getMaterial(belowPlayer).isReplaceable()) {
                        return;
                    }
                    int newSlot = -1;
                    for (int i = 0; i < 9; ++i) {
                        final ItemStack stack = this.getPlayer().inventory.getStackInSlot(i);
                        if (!this.isEmptySlot(stack) && stack.getItem() instanceof ItemBlock) {
                            newSlot = i;
                            break;
                        }
                    }
                    if (newSlot == -1) {
                        return;
                    }
                    final int oldSlot = this.getPlayer().inventory.currentItem;
                    this.getPlayer().inventory.currentItem = newSlot;
                    this.placeBlockLegit(belowPlayer, (EventPre)event);
                    this.getPlayer().inventory.currentItem = oldSlot;
                    break;
                }
            }
        }
        if (event instanceof EventUpdate && this.getPlayer().fallDistance > 2.0f && !this.getPlayer().onGround) {
            this.getPlayer().sendQueue.addToSendQueue(new C03PacketPlayer(true));
            this.getPlayer().onGround = true;
        }
    }
    
    public boolean isEmptySlot(final ItemStack slot) {
        return slot == null;
    }
    
    public Material getMaterial(final BlockPos pos) {
        return this.getState(pos).getBlock().getMaterial();
    }
    
    public boolean placeBlockLegit(final BlockPos pos, final EventPre event) {
        final Vec3 eyesPos = new Vec3(this.getX(), this.getY() + this.getPlayer().getEyeHeight(), this.getZ());
        EnumFacing[] values;
        for (int length = (values = EnumFacing.values()).length, i = 0; i < length; ++i) {
            final EnumFacing side = values[i];
            final BlockPos neighbor = pos.offset(side);
            final EnumFacing side2 = side.getOpposite();
            if (eyesPos.squareDistanceTo(new Vec3(pos).addVector(0.5, 0.5, 0.5)) < eyesPos.squareDistanceTo(new Vec3(neighbor).addVector(0.5, 0.5, 0.5)) && this.getBlock(neighbor).canCollideCheck(this.getWorld().getBlockState(neighbor), false)) {
                final Vec3 hitVec = new Vec3(neighbor).addVector(0.5, 0.5, 0.5).add(new Vec3(side2.getDirectionVec()).scale(0.5));
                if (eyesPos.squareDistanceTo(hitVec) <= 18.0625) {
                    this.getPlayerController().onPlayerRightClick(this.getPlayer(), this.getWorld(), this.getPlayer().getCurrentEquippedItem(), neighbor, side2, hitVec);
                    this.getPlayer().swingItem();
                    this.setRightClickDelay(4);
                    return true;
                }
            }
        }
        return false;
    }
    
    public IBlockState getState(final BlockPos pos) {
        return this.getWorld().getBlockState(pos);
    }
    
    public Block getBlock(final BlockPos pos) {
        return this.getState(pos).getBlock();
    }
}
