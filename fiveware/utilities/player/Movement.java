package fiveware.utilities.player;

import fiveware.utilities.Utilities;
import net.minecraft.util.AxisAlignedBB;

public class Movement extends Utilities {
   public static boolean canStep(float height) {
      if (!getPlayer().isCollidedHorizontally) {
         return false;
      } else {
         return !getWorld().getCollidingBoundingBoxes(getPlayer(), getBox().expand(0.1D, 0.0D, 0.0D).offset(0.0D, (double)height - 0.1D, 0.0D)).isEmpty() && getWorld().getCollidingBoundingBoxes(getPlayer(), getBox().expand(0.1D, 0.0D, 0.0D).offset(0.0D, (double)height + 0.1D, 0.0D)).isEmpty() || !getWorld().getCollidingBoundingBoxes(getPlayer(), getBox().expand(-0.1D, 0.0D, 0.0D).offset(0.0D, (double)height - 0.1D, 0.0D)).isEmpty() && getWorld().getCollidingBoundingBoxes(getPlayer(), getBox().expand(-0.1D, 0.0D, 0.0D).offset(0.0D, (double)height + 0.1D, 0.0D)).isEmpty() || !getWorld().getCollidingBoundingBoxes(getPlayer(), getBox().expand(0.0D, 0.0D, 0.1D).offset(0.0D, (double)height - 0.1D, 0.0D)).isEmpty() && getWorld().getCollidingBoundingBoxes(getPlayer(), getBox().expand(0.0D, 0.0D, 0.1D).offset(0.0D, (double)height + 0.1D, 0.0D)).isEmpty() || !getWorld().getCollidingBoundingBoxes(getPlayer(), getBox().expand(0.0D, 0.0D, -0.1D).offset(0.0D, (double)height - 0.1D, 0.0D)).isEmpty() && getWorld().getCollidingBoundingBoxes(getPlayer(), getBox().expand(0.0D, 0.0D, -0.1D).offset(0.0D, (double)height + 0.1D, 0.0D)).isEmpty();
      }
   }

   private static AxisAlignedBB getBox() {
      return getPlayer().getEntityBoundingBox();
   }
}
