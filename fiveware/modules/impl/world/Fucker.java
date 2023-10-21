package fiveware.modules.impl.world;

import fiveware.events.Event;
import fiveware.events.impl.EventPre;
import fiveware.interfaces.Methods;
import fiveware.modules.Category;
import fiveware.modules.Module;
import fiveware.userinterfaces.clickgui.settings.Setting;
import net.minecraft.block.Block;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class Fucker extends Module implements Methods {
   private double x;
   private double y;
   private double z;
   public Setting beds = new Setting("Beds", true, this);
   public Setting eggs = new Setting("Eggs", false, this);
   public Setting radius = new Setting("Radius", 3.0F, 1.0F, 6.0F, false, this);
   public Setting rotations = new Setting("Rotations", false, this);

   public Fucker() {
      super("Fucker", "Fucker", "Breaks beds for you.", -1, 0, Category.WORLD);
   }

   public void onEvent(Event event) {
      if (this.isToggled()) {
         if (event instanceof EventPre) {
            for(double x = (double)(-this.radius.getCurrentValue()); x < (double)this.radius.getCurrentValue(); ++x) {
               for(double y = (double)this.radius.getCurrentValue(); y > (double)(-this.radius.getCurrentValue()); --y) {
                  for(double z = (double)(-this.radius.getCurrentValue()); z < (double)this.radius.getCurrentValue(); ++z) {
                     this.x = this.getX() + x;
                     this.y = this.getY() + y;
                     this.z = this.getZ() + z;
                     BlockPos pos = new BlockPos(this.x, this.y, this.z);
                     Block block = this.getWorld().getBlockState(pos).getBlock();
                     if (block.getBlockState().getBlock() == Block.getBlockById(26) && this.beds.isToggled() || block.getBlockState().getBlock() == Block.getBlockById(122) && this.eggs.isToggled()) {
                        this.sendPacket(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, pos, EnumFacing.NORTH));
                        this.sendPacket(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, pos, EnumFacing.NORTH));
                        float[] rotation = this.getRotations(this.x, this.y, this.z, event);
                        if (this.rotations.isToggled()) {
                           ((EventPre)event).setYaw(rotation[0]);
                           ((EventPre)event).setPitch(rotation[1]);
                           this.getPlayer().renderYawOffset = rotation[0];
                           this.getPlayer().rotationYawHead = rotation[0];
                        }

                        this.getPlayer().swingItem();
                     }
                  }
               }
            }
         }

      }
   }

   private float[] getRotations(double x, double y, double z, Event event) {
      double posx = this.getPlayer().posX - x;
      double posy = this.getPlayer().posY - y;
      double posz = this.getPlayer().posZ - z;
      posx /= Math.sqrt(posx * posx + posy * posy + posz * posz);
      posy /= Math.sqrt(posx * posx + posy * posy + posz * posz);
      posz /= Math.sqrt(posx * posx + posy * posy + posz * posz);
      float yaw = (float)Math.atan2(posz, posx);
      float pitch = (float)Math.asin(posy);
      float f2 = this.getGameSettings().mouseSensitivity * 0.6F + 0.2F;
      float f3 = f2 * f2 * f2 * 1.2F;
      pitch = (float)((double)(pitch * 180.0F) / 3.141592653589793D);
      yaw = (float)((double)(yaw * 180.0F) / 3.141592653589793D);
      yaw += 90.0F;
      yaw -= yaw % f3;
      pitch -= pitch % f3 * f2;
      return new float[]{yaw, pitch};
   }
}
