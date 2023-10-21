package fiveware.modules.impl.player;

import fiveware.events.Event;
import fiveware.events.impl.EventPost;
import fiveware.events.impl.EventPre;
import fiveware.events.impl.EventUpdate;
import fiveware.interfaces.Methods;
import fiveware.modules.Category;
import fiveware.modules.Module;
import fiveware.userinterfaces.clickgui.settings.Setting;
import net.minecraft.item.ItemBow;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class NoSlowdown extends Module implements Methods {
   public Setting mode = new Setting("Mode", new String[]{"Vanilla", "AAC", "NCP"}, "Vanilla", this);

   public NoSlowdown() {
      super("No Slowdown", "NoSlowdown", "Prevents you from being slow down.", -1, 0, Category.PLAYER);
   }

   public void onEvent(Event event) {
      if (this.isToggled()) {
         if (event instanceof EventUpdate) {
            this.setInfo(this.information() ? "§7" + this.mode.getCurrentMode() + (this.leftSide() ? " §r" : "") : "");
            if (this.getPlayer().getCurrentEquippedItem() != null && this.mode.getCurrentMode().equalsIgnoreCase("AAC")) {
               if (!this.isMoving()) {
                  return;
               }

               if (this.getPlayer().isBlocking() || this.getPlayer().isEating() || this.getPlayer().isUsingItem() && this.getPlayer().getCurrentEquippedItem().getItem() instanceof ItemBow && this.getPlayer().onGround) {
                  this.getPlayer().setSpeed(0.08F);
               }
            }
         }

         if (event instanceof EventPre && this.getPlayer().isBlocking() && this.mode.getCurrentMode().equalsIgnoreCase("NCP")) {
            this.sendPacket(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
         }

         if (event instanceof EventPost && this.getPlayer().isBlocking() && this.mode.getCurrentMode().equalsIgnoreCase("NCP")) {
            this.sendPacket(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, this.getPlayer().getHeldItem(), 0.0F, 0.0F, 0.0F));
         }

      }
   }
}
