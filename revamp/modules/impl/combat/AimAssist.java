// 
// Decompiled by Procyon v0.5.36
// 

package revamp.modules.impl.combat;

import java.util.Iterator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import org.lwjgl.input.Mouse;
import revamp.events.impl.EventUpdate;
import revamp.events.Event;
import revamp.modules.Category;
import revamp.userinterfaces.clickgui.settings.Setting;
import revamp.interfaces.Methods;
import revamp.modules.Module;

public class AimAssist extends Module implements Methods
{
    public Setting fov;
    public Setting speed;
    public Setting sword;
    public Setting click;
    
    public AimAssist() {
        super("AimAssist", "AimAssist", "Aims at people for you automatically.", -1, 0, Category.COMBAT);
        this.fov = new Setting("FOV", 70.0f, 30.0f, 180.0f, false, this);
        this.speed = new Setting("Speed Divider", 35.0f, 1.0f, 75.0f, true, this);
        this.sword = new Setting("Sword Only", true, this);
        this.click = new Setting("Click Aim", true, this);
    }
    
    @Override
    public void onEvent(final Event event) {
        if (!this.isToggled()) {
            return;
        }
        if (event instanceof EventUpdate) {
            this.setNameWithOutSpace("AimAssist" + (this.information() ? (" §7" + this.speed.getCurrentValue()) : ""));
            if (this.getWorld() != null) {
                if (!this.holdingSword() && this.sword.isToggled()) {
                    return;
                }
                if (!Mouse.isButtonDown(0) && this.click.isToggled()) {
                    return;
                }
                Entity target = null;
                int fov = (int)this.fov.getCurrentValue();
                for (final Entity entity : this.getWorld().loadedEntityList) {
                    if (entity.isEntityAlive() && !entity.isInvisible() && entity != this.getPlayer() && this.getPlayer().getDistanceToEntity(entity) < 6.0f && entity instanceof EntityLivingBase && this.getPlayer().canEntityBeSeen(entity) && this.isInFOV(entity, (float)fov)) {
                        target = entity;
                        fov = (int)this.getDistanceToCrosshair(entity);
                    }
                }
                if (target != null && (this.getDistanceToCrosshair(target) > 1.0 || this.getDistanceToCrosshair(target) < -1.0)) {
                    this.getPlayer().rotationYaw += (float)((this.getDistanceToCrosshair(target) > 0.0) ? (-(Math.abs(this.getDistanceToCrosshair(target)) / this.speed.getCurrentValue())) : (Math.abs(this.getDistanceToCrosshair(target)) / this.speed.getCurrentValue()));
                }
            }
        }
    }
    
    private float getRot(final Entity entity) {
        final double x = entity.posX - this.getPlayer().posX;
        final double y = entity.posY - this.getPlayer().posY;
        final double z = entity.posZ - this.getPlayer().posZ;
        double yaw = Math.atan2(x, z) * 57.29577951308232;
        double pitch = Math.asin(y / Math.sqrt(x * x + y * y + z * z)) * 57.29577951308232;
        yaw = -yaw;
        pitch = -pitch;
        return (float)yaw;
    }
    
    private double getDistanceToCrosshair(final Entity en) {
        return ((this.getPlayer().rotationYaw - this.getRot(en)) % 360.0 + 540.0) % 360.0 - 180.0;
    }
    
    private boolean isInFOV(final Entity entity, float angle) {
        angle *= 0.5;
        final double angleDifference = ((this.getPlayer().rotationYaw - this.getRot(entity)) % 360.0 + 540.0) % 360.0 - 180.0;
        return (angleDifference > 0.0 && angleDifference < angle) || (-angle < angleDifference && angleDifference < 0.0);
    }
}
