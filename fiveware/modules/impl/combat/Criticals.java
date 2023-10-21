package fiveware.modules.impl.combat;

import fiveware.events.Event;
import fiveware.events.impl.EventPacketSend;
import fiveware.interfaces.Methods;
import fiveware.modules.Category;
import fiveware.modules.Module;
import fiveware.userinterfaces.clickgui.settings.Setting;
import fiveware.utilities.time.Timer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;

public class Criticals extends Module implements Methods {
   Timer timer = new Timer();
   public Setting mode = new Setting("Mode", new String[]{"Packet", "MiniJump"}, "Packet", this);

   public Criticals() {
      super("Criticals", "Criticals", "Hits players with more damage.", -1, 0, Category.COMBAT);
   }

   public void onEvent(Event event) {
      if (this.isToggled()) {
         if (event instanceof EventPacketSend) {
            this.setInfo(this.information() ? "§7" + this.mode.getCurrentMode() + (this.leftSide() ? " §r" : "") : "");
            if (!this.isIngame() && this.getPlayer() != null && !this.isInLiquid() && !this.onGround()) {
               return;
            }

            Packet packet = ((EventPacketSend)event).getPacket();
            if (packet instanceof C02PacketUseEntity && ((C02PacketUseEntity)packet).getAction() == C02PacketUseEntity.Action.ATTACK) {
               if (this.mode.getCurrentMode().equalsIgnoreCase("Packet")) {
                  this.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(this.getX(), this.getY() + 0.0625D, this.getZ(), true));
                  this.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(this.getX(), this.getY(), this.getZ(), false));
                  this.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(this.getX(), this.getY() + 1.1E-5D, this.getZ(), false));
                  this.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(this.getX(), this.getY(), this.getZ(), false));
               } else if (this.mode.getCurrentMode().equalsIgnoreCase("MiniJump") && this.onGround()) {
                  this.jump();
                  EntityPlayerSP var10000 = this.getPlayer();
                  var10000.motionY -= 0.30000001192092896D;
               }
            }
         }

      }
   }
}
