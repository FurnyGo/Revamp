package fiveware.modules.impl.player;

import fiveware.events.Event;
import fiveware.events.impl.EventPre;
import fiveware.interfaces.Methods;
import fiveware.modules.Category;
import fiveware.modules.Module;
import fiveware.userinterfaces.clickgui.settings.Setting;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;

public class NoFall extends Module implements Methods {
   private float fallDist;
   public Setting mode = new Setting("Mode", new String[]{"Packet", "Tick", "Edit"}, "Packet", this);
   public Setting voidCheck = new Setting("Void Check", true, this);

   public NoFall() {
      super("No Fall", "NoFall", "Prevents fall damage.", -1, 0, Category.PLAYER);
   }

   public void onEvent(Event event) {
      if (this.isToggled()) {
         if (event instanceof EventPre) {
            this.setInfo(this.information() ? "§7" + this.mode.getCurrentMode() + (this.leftSide() ? " §r" : "") : "");
            if (this.voidCheck.isToggled() && this.getPlayer().fallDistance > 2.5F && this.voidUnder()) {
               return;
            }

            if (this.getPlayer().fallDistance > 2.5F) {
               if (this.mode.getCurrentMode().equalsIgnoreCase("Packet")) {
                  this.sendPacket(new C03PacketPlayer(true));
               } else if (this.mode.getCurrentMode().equalsIgnoreCase("Tick")) {
                  if (this.getPlayer().ticksExisted % 2 == 0) {
                     ((EventPre)event).setGround(true);
                  }
               } else if (this.mode.getCurrentMode().equalsIgnoreCase("Edit")) {
                  if (this.fallDist > this.getPlayer().fallDistance) {
                     this.fallDist = 0.0F;
                  }

                  if (this.getPlayer().motionY < 0.0D && !this.getPlayer().isSpectator()) {
                     double fallingDist = (double)(this.getPlayer().fallDistance - this.fallDist);
                     if (fallingDist + -((this.getPlayer().motionY - 0.08D) * 0.9800000190734863D) >= 3.0D) {
                        ((EventPre)event).setGround(true);
                     }
                  }
               }
            }
         }

      }
   }

   private Block getBlockUnderPlayer(int i) {
      return this.getWorld().getBlockState(new BlockPos(this.getX(), this.getY() - (double)i, this.getZ())).getBlock();
   }

   private boolean voidUnder() {
      for(int i = (int)Math.ceil(this.getPlayer().posY); i >= 0; --i) {
         if (this.getWorld().getBlockState(new BlockPos(this.getPlayer().posX, (double)i, this.getPlayer().posZ)).getBlock() != Blocks.air) {
            return false;
         }
      }

      return true;
   }
}
