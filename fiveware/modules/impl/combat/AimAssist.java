package fiveware.modules.impl.combat;

import fiveware.events.Event;
import fiveware.events.impl.EventUpdate;
import fiveware.interfaces.Methods;
import fiveware.modules.Category;
import fiveware.modules.Module;
import fiveware.userinterfaces.clickgui.settings.Setting;
import java.util.Iterator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import org.lwjgl.input.Mouse;

public class AimAssist extends Module implements Methods {
   public Setting fov = new Setting("FOV", 70.0F, 30.0F, 180.0F, false, this);
   public Setting reach = new Setting("Reach", 4.0F, 3.0F, 6.0F, false, this);
   public Setting speed = new Setting("Speed Divider", 35.0F, 1.0F, 75.0F, true, this);
   public Setting sword = new Setting("Sword Only", true, this);
   public Setting thruwalls = new Setting("Through Walls", true, this);
   public Setting click = new Setting("Click Aim", true, this);

   public AimAssist() {
      super("Aim Assist", "AimAssist", "Aims at people for you automatically.", -1, 0, Category.COMBAT);
   }

   public void onEvent(Event event) {
      if (this.isToggled()) {
         if (event instanceof EventUpdate && this.getWorld() != null) {
            if (!this.holdingSword() && this.sword.isToggled()) {
               return;
            }

            if (!Mouse.isButtonDown(0) && this.click.isToggled()) {
               return;
            }

            Entity target = null;
            int fov = (int)this.fov.getCurrentValue();
            Iterator var5 = this.getWorld().loadedEntityList.iterator();

            while(var5.hasNext()) {
               Entity entity = (Entity)var5.next();
               if (entity.isEntityAlive() && !entity.isInvisible() && entity != this.getPlayer() && this.getPlayer().getDistanceToEntity(entity) < this.reach.getCurrentValue() && entity instanceof EntityLivingBase && this.isInFOV(entity, (float)fov)) {
                  if (!this.thruwalls.isToggled() && !this.getPlayer().canEntityBeSeen(entity)) {
                     return;
                  }

                  target = entity;
                  fov = (int)this.getDistanceToCrosshair(entity);
               }
            }

            if (target != null && (this.getDistanceToCrosshair(target) > 1.0D || this.getDistanceToCrosshair(target) < -1.0D)) {
               this.getPlayer().rotationYaw = (float)((double)this.getPlayer().rotationYaw + (this.getDistanceToCrosshair(target) > 0.0D ? -(Math.abs(this.getDistanceToCrosshair(target)) / (double)this.speed.getCurrentValue()) : Math.abs(this.getDistanceToCrosshair(target)) / (double)this.speed.getCurrentValue()));
            }
         }

      }
   }

   private float getRot(Entity entity) {
      double x = entity.posX - this.getPlayer().posX;
      double y = entity.posY - this.getPlayer().posY;
      double z = entity.posZ - this.getPlayer().posZ;
      double yaw = Math.atan2(x, z) * 57.29577951308232D;
      double pitch = Math.asin(y / Math.sqrt(x * x + y * y + z * z)) * 57.29577951308232D;
      yaw = -yaw;
      pitch = -pitch;
      return (float)yaw;
   }

   private double getDistanceToCrosshair(Entity en) {
      return (double)(((this.getPlayer().rotationYaw - this.getRot(en)) % 360.0F + 540.0F) % 360.0F - 180.0F);
   }

   private boolean isInFOV(Entity entity, float angle) {
      angle = (float)((double)angle * 0.5D);
      double angleDifference = (double)(((this.getPlayer().rotationYaw - this.getRot(entity)) % 360.0F + 540.0F) % 360.0F - 180.0F);
      return angleDifference > 0.0D && angleDifference < (double)angle || (double)(-angle) < angleDifference && angleDifference < 0.0D;
   }
}
