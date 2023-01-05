// 
// Decompiled by Procyon v0.5.36
// 

package revamp.modules.impl.movement;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.item.ItemBlock;
import revamp.events.impl.EventPre;
import revamp.events.Event;
import revamp.modules.Category;
import revamp.userinterfaces.clickgui.settings.Setting;
import revamp.interfaces.Methods;
import revamp.modules.Module;

public class Scaffold extends Module implements Methods
{
    private int prevSlot;
    private int currentSlot;
    public Setting rotations;
    public Setting rotationsMode;
    public Setting tower;
    public Setting towerMode;
    public Setting towerTimer;
    public Setting towerMotion;
    public Setting extend;
    public Setting extendval;
    public Setting noswing;
    
    public Scaffold() {
        super("Scaffold", "Scaffold", "Bridges for you.", -1, 0, Category.MOVEMENT);
        this.rotations = new Setting("Rotations", true, this);
        this.rotationsMode = new Setting("Rotations Mode", new String[] { "Client", "Server" }, "Server", this);
        this.tower = new Setting("Tower", true, this);
        this.towerMode = new Setting("Tower Mode", new String[] { "Motion", "Timer" }, "Motion", this);
        this.towerTimer = new Setting("Tower Timer", 2.0f, 0.1f, 10.0f, false, this);
        this.towerMotion = new Setting("Tower Motion", 1.0f, 1.0f, 10.0f, true, this);
        this.extend = new Setting("Extend", false, this);
        this.extendval = new Setting("Extend Value", 2.0f, 1.0f, 5.0f, true, this);
        this.noswing = new Setting("No Swing", false, this);
    }
    
    @Override
    public void onEnable() {
        this.prevSlot = this.getPlayer().inventory.currentItem;
    }
    
    @Override
    public void onEvent(final Event event) {
        if (!this.isToggled()) {
            return;
        }
        if (event instanceof EventPre) {
            int blockCount = 0;
            for (int i = 0; i < 9; ++i) {
                final ItemStack item = this.getPlayer().inventory.getStackInSlot(i);
                if (item != null && item.getItem() instanceof ItemBlock && item.stackSize > blockCount) {
                    blockCount = item.stackSize;
                    this.getPlayer().inventory.currentItem = i;
                }
            }
            if (blockCount == 0) {
                return;
            }
            final EnumFacing face = EnumFacing.getHorizontal(MathHelper.floor_double(this.getPlayer().rotationYaw * 4.0f / 360.0f + 0.5) & 0x3);
            int direction = face.getHorizontalIndex();
            if (this.getPlayer().moveForward < 0.0f) {
                direction = face.getOpposite().getHorizontalIndex();
            }
            final BlockPos pos = new BlockPos(this.getX(), this.getPlayer().getEntityBoundingBox().minY, this.getZ());
            if (this.tower.isToggled() && this.getGameSettings().keyBindJump.pressed && this.getPlayer().motionY > 0.0) {
                if (this.getPlayer().fallDistance > 0.5f) {
                    return;
                }
                this.placeBlock(pos.add(0.0, -0.05, 0.0), EnumFacing.UP);
                if (this.rotations.isToggled() && this.rotationsMode.getCurrentMode().equalsIgnoreCase("Server")) {
                    ((EventPre)event).setPitch(90.0f);
                    ((EventPre)event).setYaw(98.0f);
                    this.getPlayer().rotationYawHead = 98.0f;
                    this.getPlayer().renderYawOffset = 98.0f;
                }
                else if (this.rotations.isToggled() && this.rotationsMode.getCurrentMode().equalsIgnoreCase("Client")) {
                    this.getPlayer().rotationPitch = 90.0f;
                    this.getPlayer().rotationYaw = 98.0f;
                }
                if (this.towerMode.getCurrentMode().equalsIgnoreCase("Motion")) {
                    final EntityPlayerSP player = this.getPlayer();
                    player.motionY += this.towerMotion.getCurrentValue() / 100.0f;
                    this.getTimer().timerSpeed = 1.0f;
                }
                else if (this.towerMode.getCurrentMode().equalsIgnoreCase("Timer")) {
                    this.getTimer().timerSpeed = this.towerTimer.getCurrentValue();
                }
                if (this.noswing.isToggled()) {
                    this.sendPacket(new C0APacketAnimation());
                }
                else {
                    this.getPlayer().swingItem();
                }
            }
            if (this.getPlayer().fallDistance > 0.5f) {
                return;
            }
            if (this.getPlayer().motionY <= 0.05 || this.getTimer().timerSpeed != 1.0) {
                if (this.tower.isToggled() && this.getGameSettings().keyBindJump.pressed) {
                    return;
                }
                if (!this.isWalking()) {
                    return;
                }
                this.getTimer().timerSpeed = 1.0f;
                final double blockPlacement = -0.05;
                if (direction == 0) {
                    if (this.rotations.isToggled() && this.rotationsMode.getCurrentMode().equalsIgnoreCase("Server")) {
                        ((EventPre)event).setPitch(90.0f);
                        ((EventPre)event).setYaw(540.0f);
                        this.getPlayer().rotationYawHead = 540.0f;
                        this.getPlayer().renderYawOffset = 540.0f;
                    }
                    else if (this.rotations.isToggled() && this.rotationsMode.getCurrentMode().equalsIgnoreCase("Client")) {
                        this.getPlayer().rotationPitch = 90.0f;
                        this.getPlayer().rotationYaw = 540.0f;
                    }
                    this.placeBlock(pos.add(0.0, blockPlacement, -1.0 + (this.extend.isToggled() ? this.extendval.getCurrentValue() : 0.0f)), EnumFacing.SOUTH);
                }
                else if (direction == 1) {
                    if (this.rotations.isToggled() && this.rotationsMode.getCurrentMode().equalsIgnoreCase("Server")) {
                        ((EventPre)event).setPitch(90.0f);
                        ((EventPre)event).setYaw(630.0f);
                        this.getPlayer().rotationYawHead = 630.0f;
                        this.getPlayer().renderYawOffset = 630.0f;
                    }
                    else if (this.rotations.isToggled() && this.rotationsMode.getCurrentMode().equalsIgnoreCase("Client")) {
                        this.getPlayer().rotationPitch = 90.0f;
                        this.getPlayer().rotationYaw = 630.0f;
                    }
                    this.placeBlock(pos.add(1.0 - (this.extend.isToggled() ? this.extendval.getCurrentValue() : 0.0f), blockPlacement, 0.0), EnumFacing.WEST);
                }
                else if (direction == 2) {
                    if (this.rotations.isToggled() && this.rotationsMode.getCurrentMode().equalsIgnoreCase("Server")) {
                        ((EventPre)event).setPitch(90.0f);
                        ((EventPre)event).setYaw(720.0f);
                        this.getPlayer().rotationYawHead = 720.0f;
                        this.getPlayer().renderYawOffset = 720.0f;
                    }
                    else if (this.rotations.isToggled() && this.rotationsMode.getCurrentMode().equalsIgnoreCase("Client")) {
                        this.getPlayer().rotationPitch = 90.0f;
                        this.getPlayer().rotationYaw = 720.0f;
                    }
                    this.placeBlock(pos.add(0.0, blockPlacement, 1.0 - (this.extend.isToggled() ? this.extendval.getCurrentValue() : 0.0f)), EnumFacing.NORTH);
                }
                else if (direction == 3) {
                    if (this.rotations.isToggled() && this.rotationsMode.getCurrentMode().equalsIgnoreCase("Server")) {
                        ((EventPre)event).setPitch(90.0f);
                        ((EventPre)event).setYaw(450.0f);
                        this.getPlayer().rotationYawHead = 450.0f;
                        this.getPlayer().renderYawOffset = 450.0f;
                    }
                    else if (this.rotations.isToggled() && this.rotationsMode.getCurrentMode().equalsIgnoreCase("Client")) {
                        this.getPlayer().rotationPitch = 90.0f;
                        this.getPlayer().rotationYaw = 450.0f;
                    }
                    this.placeBlock(pos.add(-1.0 + (this.extend.isToggled() ? this.extendval.getCurrentValue() : 0.0f), blockPlacement, 0.0), EnumFacing.EAST);
                }
                if (this.noswing.isToggled()) {
                    this.sendPacket(new C0APacketAnimation());
                }
                else {
                    this.getPlayer().swingItem();
                }
            }
        }
    }
    
    @Override
    public void onDisable() {
        this.getTimer().timerSpeed = 1.0f;
        this.getPlayer().inventory.currentItem = this.prevSlot;
    }
}
