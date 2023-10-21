package fiveware.modules.impl.combat;

import fiveware.events.Event;
import fiveware.events.impl.EventPacket;
import fiveware.events.impl.EventUpdate;
import fiveware.interfaces.Methods;
import fiveware.modules.Category;
import fiveware.modules.Module;
import fiveware.userinterfaces.clickgui.settings.Setting;
import net.minecraft.network.play.server.S12PacketEntityVelocity;

public class Velocity extends Module implements Methods {
   private double x;
   private double z;
   public Setting mode = new Setting("Mode", new String[]{"Simple", "AAC33", "Custom"}, "Hypixel", this);
   public Setting horizontal = new Setting("Horizontal", 80.0F, 0.0F, 100.0F, true, this);
   public Setting vertical = new Setting("Vertical", 80.0F, 0.0F, 100.0F, true, this);

   public Velocity() {
      super("Velocity", "Velocity", "Reduces knockback.", -1, 0, Category.COMBAT);
   }

   public void onEvent(Event event) {
      if (!this.mode.getCurrentMode().equalsIgnoreCase("Custom")) {
         this.horizontal.visible = false;
         this.vertical.visible = false;
      } else {
         this.horizontal.visible = true;
         this.vertical.visible = true;
      }

      if (this.isToggled()) {
         if (event instanceof EventUpdate) {
            this.setInfo(this.information() ? "§7" + this.mode.getCurrentMode() + (this.leftSide() ? " §r" : "") : "");
            if (this.mode.getCurrentMode().equalsIgnoreCase("Simple")) {
               if (this.getHurtTime() == 9) {
                  this.x = this.getPlayer().motionX;
                  this.z = this.getPlayer().motionZ;
               } else if (this.getHurtTime() == 8) {
                  this.getPlayer().motionX = -this.x * 0.45D;
                  this.getPlayer().motionZ = -this.z * 0.45D;
               }
            }

            if (this.mode.getCurrentMode().equalsIgnoreCase("AAC33") && this.hurtTimeIsnt9()) {
               this.getPlayer().motionX = -Math.sin(Math.toRadians((double)this.getPlayer().rotationYawHead)) * 0.05D;
               this.getPlayer().motionZ = -Math.cos(Math.toRadians((double)this.getPlayer().rotationYawHead)) * 0.05D;
            }
         }

         if (event instanceof EventPacket && this.mode.getCurrentMode().equalsIgnoreCase("Custom") && ((EventPacket)event).getPacket() instanceof S12PacketEntityVelocity) {
            S12PacketEntityVelocity packet = (S12PacketEntityVelocity)((EventPacket)event).getPacket();
            if (this.getWorld().getEntityByID(packet.getEntityID()) == this.getPlayer()) {
               packet.motionX = (int)((float)packet.motionX * (this.horizontal.getCurrentValue() / 100.0F));
               packet.motionY = (int)((float)packet.motionY * (this.vertical.getCurrentValue() / 100.0F));
               packet.motionZ = (int)((float)packet.motionZ * (this.horizontal.getCurrentValue() / 100.0F));
            }
         }

      }
   }
}
