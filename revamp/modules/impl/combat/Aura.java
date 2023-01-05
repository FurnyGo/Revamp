// 
// Decompiled by Procyon v0.5.36
// 

package revamp.modules.impl.combat;

import revamp.friend.FriendManager;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import revamp.Revamp;
import java.util.Iterator;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.BlockPos;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import revamp.events.impl.EventPost;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.entity.Entity;
import revamp.events.impl.EventPre;
import revamp.events.Event;
import revamp.modules.Category;
import revamp.userinterfaces.clickgui.settings.Setting;
import net.minecraft.entity.EntityLivingBase;
import revamp.interfaces.Methods;
import revamp.modules.Module;

public class Aura extends Module implements Methods
{
    public EntityLivingBase target;
    protected long current;
    protected long last;
    protected float yaw;
    protected float pitch;
    public Setting particles;
    public Setting reach;
    public Setting aps;
    public Setting autoblock;
    public Setting blockmode;
    public Setting rotations;
    public Setting rotationmode;
    public Setting thruwalls;
    public Setting noswing;
    
    public Aura() {
        super("Aura", "Aura", "Kills people for you automatically.", -1, 19, Category.COMBAT);
        this.particles = new Setting("Crack Size", 7.0f, 0.0f, 10.0f, true, this);
        this.reach = new Setting("Reach", 4.0f, 3.0f, 6.0f, false, this);
        this.aps = new Setting("APS", 8.0f, 1.0f, 20.0f, true, this);
        this.autoblock = new Setting("AutoBlock", true, this);
        this.blockmode = new Setting("Block Mode", new String[] { "Vanilla", "AAC", "Fake" }, "Vanilla", this);
        this.rotations = new Setting("Rotations", true, this);
        this.rotationmode = new Setting("Rotations Mode", new String[] { "Client", "Server" }, "Server", this);
        this.thruwalls = new Setting("Through Walls", true, this);
        this.noswing = new Setting("No Swing", true, this);
    }
    
    @Override
    public void onEvent(final Event event) {
        if (!this.isToggled()) {
            return;
        }
        if (event instanceof EventPre) {
            this.setNameWithOutSpace("Aura" + (this.information() ? (" §7" + this.reach.getCurrentValue()) : ""));
            this.target = this.getClosest(this.reach.getCurrentValue());
            if (this.target == null) {
                return;
            }
            if (!this.thruwalls.isToggled() && !this.getPlayer().canEntityBeSeen(this.target)) {
                return;
            }
            this.update();
            final float[] rotation = this.getRotations(this.target, (EventPre)event);
            if (this.rotations.isToggled() && this.rotationmode.getCurrentMode().equalsIgnoreCase("Server")) {
                ((EventPre)event).setYaw(rotation[0]);
                ((EventPre)event).setPitch(rotation[1]);
                this.getPlayer().renderYawOffset = rotation[0];
                this.getPlayer().rotationYawHead = rotation[0];
            }
            else if (this.rotations.isToggled() && this.rotationmode.getCurrentMode().equalsIgnoreCase("Client")) {
                this.getPlayer().rotationYaw = rotation[0];
                this.getPlayer().rotationPitch = rotation[1];
            }
            if (!this.noswing.isToggled() && this.autoblock.isToggled() && this.target.getDistanceToEntity(this.getPlayer()) < this.reach.getCurrentValue()) {
                this.blockMode(event);
            }
            if (this.current - this.last > 1000.0f / this.aps.getCurrentValue()) {
                for (int i = 0; i < this.particles.getCurrentValue(); ++i) {
                    this.getPlayer().onCriticalHit(this.target);
                }
                if (this.noswing.isToggled()) {
                    this.sendPacket(new C0APacketAnimation());
                }
                else {
                    this.getPlayer().swingItem();
                }
                this.getPlayerController().attackEntity(this.getPlayer(), this.target);
                this.reset();
            }
        }
        if (event instanceof EventPost) {
            if (this.target == null) {
                return;
            }
            if (this.autoblock.isToggled()) {
                this.blockMode(event);
            }
        }
    }
    
    private void update() {
        this.current = System.nanoTime() / 1000000L;
    }
    
    private void reset() {
        this.last = System.nanoTime() / 1000000L;
    }
    
    private void blockMode(final Event e) {
        if (this.getPlayer().inventory.getCurrentItem() == null) {
            return;
        }
        if (this.blockmode.getCurrentMode().equalsIgnoreCase("Vanilla")) {
            this.getPlayer().setItemInUse(this.getPlayer().inventory.getCurrentItem(), 140);
        }
        else if (this.blockmode.getCurrentMode().equalsIgnoreCase("AAC")) {
            if (this.getPlayer().ticksExisted % 2 == 0) {
                this.getPlayer().setItemInUse(this.getPlayer().inventory.getCurrentItem(), 140);
            }
        }
        else if (this.blockmode.getCurrentMode().equalsIgnoreCase("Fake")) {
            if (e instanceof EventPre) {
                this.getPlayer().setItemInUse(this.getPlayer().inventory.getCurrentItem(), 140);
            }
            else {
                this.sendPacket(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.UP));
            }
        }
    }
    
    private EntityLivingBase getClosest(final double range) {
        double dist = range;
        EntityLivingBase target = null;
        for (final Object object : this.getWorld().loadedEntityList) {
            final Entity entity = (Entity)object;
            if (entity instanceof EntityLivingBase) {
                final EntityLivingBase player = (EntityLivingBase)entity;
                if (!this.canAttackEntity(player)) {
                    continue;
                }
                final double currentDist = this.getPlayer().getDistanceToEntity(player);
                if (currentDist > dist) {
                    continue;
                }
                dist = currentDist;
                target = player;
            }
        }
        return target;
    }
    
    private boolean canAttackEntity(final EntityLivingBase entity) {
        return (!(entity instanceof EntityPlayer) || Revamp.moduleManager.getModuleByName("Players").isToggled()) && (!(entity instanceof EntityAnimal) || Revamp.moduleManager.getModuleByName("Animals").isToggled()) && (!(entity instanceof EntityMob) || Revamp.moduleManager.getModuleByName("Mobs").isToggled()) && (!entity.isOnSameTeam(this.getPlayer()) || Revamp.moduleManager.getModuleByName("Teams").isToggled()) && (!entity.isInvisible() || Revamp.moduleManager.getModuleByName("Invisibles").isToggled()) && !(entity instanceof EntityGhast) && !(entity instanceof EntityBat) && !(entity instanceof EntitySquid) && (!(entity instanceof EntityVillager) || Revamp.moduleManager.getModuleByName("Others").isToggled()) && !(entity instanceof EntityArmorStand) && (!FriendManager.isFriend(entity.getName()) || Revamp.moduleManager.getModuleByName("Friends").isToggled()) && (entity != this.getPlayer() && entity.isEntityAlive() && this.getPlayer().getDistanceToEntity(this.getPlayer()) <= this.reach.getCurrentValue());
    }
    
    private float[] getRotations(final EntityLivingBase entity, final EventPre target) {
        final double x = entity.posX + (entity.posX - entity.lastTickPosX) - target.x;
        final double y = entity.posY + entity.getEyeHeight() - (target.y + this.getPlayer().getEyeHeight()) - 0.1;
        final double z = entity.posZ + (entity.posZ - entity.lastTickPosZ) - target.z;
        float yaw = (float)Math.toDegrees(-Math.atan(x / z));
        float pitch = (float)(-Math.toDegrees(Math.atan(y / Math.sqrt(Math.pow(x, 2.0) + Math.pow(z, 2.0)))));
        if (x > 0.0 && z < 0.0) {
            yaw = -90.0f + (float)Math.toDegrees(Math.atan(z / x));
        }
        else if (x < 0.0 && z < 0.0) {
            yaw = 90.0f + (float)Math.toDegrees(Math.atan(z / x));
        }
        final float gcd = (float)(this.getGameSettings().mouseSensitivity * 0.6 + 0.2 * this.getGameSettings().mouseSensitivity * 0.6 + 0.2 * this.getGameSettings().mouseSensitivity * 0.6 + 0.24);
        yaw -= yaw % gcd;
        pitch -= pitch % gcd;
        yaw += (float)(Math.random() * (Math.random() * 6.0) - Math.random());
        pitch += (float)(Math.random() * (Math.random() * (6.0 - Math.random())) - Math.random());
        if (pitch > 90.0f) {
            pitch = 90.0f;
        }
        if (pitch < -90.0f) {
            pitch = -90.0f;
        }
        if (yaw > 180.0f) {
            yaw = 180.0f;
        }
        if (yaw < -180.0f) {
            yaw = -180.0f;
        }
        return new float[] { yaw, pitch };
    }
}
