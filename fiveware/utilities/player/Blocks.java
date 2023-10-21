package fiveware.utilities.player;

import fiveware.utilities.Utilities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;

public class Blocks extends Utilities {
   public static float[] getRotations(int x, int y, int z, EnumFacing facing) {
      Entity e = new EntitySnowball(getWorld());
      e.posX = (double)x + 0.5D;
      e.posY = (double)y + 0.5D;
      e.posZ = (double)z + 0.5D;
      e.posX += (double)facing.getDirectionVec().getX() * 0.25D;
      e.posY += (double)facing.getDirectionVec().getY() * 0.25D;
      e.posZ += (double)facing.getDirectionVec().getZ() * 0.25D;
      return getAngles(e);
   }

   public static float getYaw(Entity e) {
      double x = e.posX - getPlayer().posX;
      double z = e.posZ - getPlayer().posZ;
      double yaw;
      if (z < 0.0D && x < 0.0D) {
         yaw = 90.0D + Math.toDegrees(Math.atan(z / x));
      } else if (z < 0.0D && x > 0.0D) {
         yaw = -90.0D + Math.toDegrees(Math.atan(z / x));
      } else {
         yaw = Math.toDegrees(-Math.atan(x / z));
      }

      return MathHelper.wrapAngleTo180_float((float)(-((double)getPlayer().rotationYaw - yaw)));
   }

   public static float getPitch(Entity e) {
      double x = e.posX - getPlayer().posX;
      double y = e.posY - 1.6D + (double)e.getEyeHeight() - getPlayer().posY;
      double z = e.posZ - getPlayer().posZ;
      return -MathHelper.wrapAngleTo180_float(getPlayer().rotationPitch - (float)(-Math.toDegrees(Math.atan(y / (double)MathHelper.sqrt_double(x * x + z * z)))));
   }

   public static float[] getAngles(Entity e) {
      return new float[]{getYaw(e) + getPlayer().rotationYaw, getPitch(e) + getPlayer().rotationPitch};
   }
}
