package fiveware.modules.impl.combat;

import fiveware.Fiveware;
import fiveware.events.Event;
import fiveware.events.impl.EventPre;
import fiveware.events.impl.EventRender3D;
import fiveware.interfaces.Methods;
import fiveware.modules.Category;
import fiveware.modules.Module;
import fiveware.userinterfaces.clickgui.settings.Setting;
import fiveware.utilities.render.Rainbow;
import java.awt.Color;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import org.lwjgl.opengl.GL11;

public class TargetStrafe extends Module implements Methods {
   private Entity target;
   private float distance;
   private boolean direction;
   public Setting reach = new Setting("Reach", 3.0F, 1.0F, 6.0F, false, this);
   public Setting jump = new Setting("Jump Only", false, this);
   public Setting render = new Setting("Render Circle", true, this);

   public TargetStrafe() {
      super("Target Strafe", "TargetStrafe", "Strafes around your target.", -1, 0, Category.COMBAT);
   }

   public void onEvent(Event event) {
      if (this.isToggled()) {
         Aura aura = (Aura)Fiveware.moduleManager.getModuleByName("Aura");
         if (event instanceof EventPre) {
            if (!aura.isToggled()) {
               EntityPlayer.movementYaw = null;
               return;
            }

            if (!this.getGameSettings().keyBindJump.isKeyDown() && this.jump.isToggled()) {
               EntityPlayer.movementYaw = null;
               return;
            }

            this.target = aura.target;
            if (this.target == null) {
               EntityPlayer.movementYaw = null;
               return;
            }

            if (this.target.isDead) {
               EntityPlayer.movementYaw = null;
               return;
            }

            float yaw = this.getTargetYaw();
            this.distance = this.getPlayer().getDistanceToEntity(this.target);
            double moveDirection = this.getDirection(EntityPlayer.movementYaw == null ? yaw : EntityPlayer.movementYaw);
            double x = -Math.sin(moveDirection) * (double)this.getPlayer().getSpeed() * 5.0D;
            double z = Math.cos(moveDirection) * (double)this.getPlayer().getSpeed() * 5.0D;

            for(int i = (int)Math.ceil(this.getPlayer().posY); i >= 0; --i) {
               if (this.getWorld().getBlockState(new BlockPos(this.getPlayer().posX, (double)i, this.getPlayer().posZ)).getBlock() == Blocks.air || this.getPlayer().isCollidedHorizontally) {
                  this.direction = !this.direction;
               }
            }

            if (this.distance > this.reach.getCurrentValue()) {
               EntityPlayer.movementYaw = yaw;
            } else if (this.direction) {
               EntityPlayer.movementYaw = yaw + 78.0F + (this.distance - this.reach.getCurrentValue()) * 2.0F;
            } else {
               EntityPlayer.movementYaw = yaw - 78.0F - (this.distance - this.reach.getCurrentValue()) * 2.0F;
            }
         }

         if (event instanceof EventRender3D) {
            if (!aura.isToggled() || this.target == null) {
               return;
            }

            if (this.target.isDead) {
               EntityPlayer.movementYaw = null;
               return;
            }

            if (this.render.isToggled() && (this.getGameSettings().keyBindJump.isKeyDown() || !this.jump.isToggled())) {
               this.circle(this.target, (double)this.reach.getCurrentValue() - 0.5D, Rainbow.mixedColor(new Color(this.getMixed(1, "R"), this.getMixed(1, "G"), this.getMixed(1, "B")), new Color(this.getMixed(2, "R"), this.getMixed(2, "G"), this.getMixed(2, "B")), 1.0F));
            }
         }

      }
   }

   private float getTargetYaw() {
      double x = this.target.posX - (this.target.lastTickPosX - this.target.posX) - this.getPlayer().posX;
      double z = this.target.posZ - (this.target.lastTickPosZ - this.target.posZ) - this.getPlayer().posZ;
      return (float)(Math.toDegrees(Math.atan2(z, x)) - 90.0D);
   }

   public double getDirection(float yaw) {
      float rotationYaw = yaw;
      if (EntityPlayer.movementYaw != null) {
         rotationYaw = EntityPlayer.movementYaw;
      }

      if (this.getPlayer().moveForward < 0.0F) {
         rotationYaw += 180.0F;
      }

      float forward = 1.0F;
      if (this.getPlayer().moveForward < 0.0F) {
         forward = -0.5F;
      } else if (this.getPlayer().moveForward > 0.0F) {
         forward = 0.5F;
      }

      if (this.getPlayer().moveStrafing > 0.0F) {
         rotationYaw -= 90.0F * forward;
      }

      if (this.getPlayer().moveStrafing < 0.0F) {
         rotationYaw += 90.0F * forward;
      }

      return Math.toRadians((double)rotationYaw);
   }

   public Block getBlockRelativeToPlayer(double offsetX, double offsetY, double offsetZ) {
      return this.getWorld().getBlockState(new BlockPos(this.getPlayer().posX + offsetX, this.getPlayer().posY + offsetY, this.getPlayer().posZ + offsetZ)).getBlock();
   }

   private void circle(Entity entity, double rad, Color color) {
      GL11.glPushMatrix();
      GL11.glDisable(3553);
      GL11.glEnable(2848);
      GL11.glEnable(2832);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glHint(3154, 4354);
      GL11.glHint(3155, 4354);
      GL11.glHint(3153, 4354);
      GL11.glDepthMask(false);
      GlStateManager.disableCull();
      GL11.glBegin(5);
      double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)this.getTimer().renderPartialTicks - this.getRenderManager().viewerPosX;
      double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)this.getTimer().renderPartialTicks - this.getRenderManager().viewerPosY + 0.01D;
      double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)this.getTimer().renderPartialTicks - this.getRenderManager().viewerPosZ;

      for(int i = 0; i <= 90; ++i) {
         GL11.glColor3f((float)(color.getRed() / 255), (float)(color.getGreen() / 255), (float)(color.getBlue() / 255));
         GL11.glVertex3d(x + rad * Math.cos((double)i * 6.283185307179586D / 45.0D), y, z + rad * Math.sin((double)i * 6.283185307179586D / 45.0D));
      }

      GL11.glEnd();
      GL11.glDepthMask(true);
      GL11.glEnable(2929);
      GlStateManager.enableCull();
      GL11.glDisable(2848);
      GL11.glDisable(2848);
      GL11.glEnable(2832);
      GL11.glEnable(3553);
      GL11.glPopMatrix();
      GL11.glColor3f(255.0F, 255.0F, 255.0F);
   }

   private float getMixed(int number, String rgb) {
      return Fiveware.settingManager.getSetting(Fiveware.moduleManager.getModuleByName("Mixed"), "Color " + number + " " + rgb).getCurrentValue();
   }
}
