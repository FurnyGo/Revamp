package fiveware.modules.impl.combat;

import fiveware.Fiveware;
import fiveware.events.Event;
import fiveware.events.impl.EventPacket;
import fiveware.events.impl.EventUpdate;
import fiveware.interfaces.Methods;
import fiveware.modules.Category;
import fiveware.modules.Module;
import fiveware.userinterfaces.clickgui.settings.Setting;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.S01PacketJoinGame;
import net.minecraft.network.play.server.S0CPacketSpawnPlayer;

public class AntiBot extends Module implements Methods {
   private List<Integer> bots = new ArrayList();
   public Setting mode = new Setting("Mode", new String[]{"Watchdog", "Matrix", "AAC"}, "Hypixel", this);
   public Setting debug = new Setting("Debug", false, this);

   public AntiBot() {
      super("Anti Bot", "AntiBot", "Removes bots.", -1, 0, Category.COMBAT);
   }

   public void onEvent(Event event) {
      if (this.isToggled()) {
         if (event instanceof EventUpdate) {
            this.setInfo(this.information() ? "§7" + this.mode.getCurrentMode() + (this.leftSide() ? " §r" : "") : "");
            Iterator var3 = this.getWorld().loadedEntityList.iterator();

            label85:
            while(true) {
               Entity entity;
               while(true) {
                  if (!var3.hasNext()) {
                     break label85;
                  }

                  entity = (Entity)var3.next();
                  if (!this.mode.getCurrentMode().equalsIgnoreCase("Watchdog") || Fiveware.getOnlinePlayers().contains(entity.getName()) || entity.ticksExisted < 20 || entity == this.getPlayer()) {
                     break;
                  }

                  if (!(entity instanceof EntityMob) && !(entity instanceof EntityAnimal)) {
                     this.getWorld().removeEntity(entity);
                     if (this.debug.isToggled()) {
                        Fiveware.sendChatMessage(true, "Removed " + entity.getName());
                     }
                     break;
                  }
               }

               if (this.mode.getCurrentMode().equalsIgnoreCase("Matrix") && this.bots.contains(entity.getEntityId()) && entity != this.getPlayer()) {
                  this.getWorld().removeEntity(entity);
                  if (this.debug.isToggled()) {
                     Fiveware.sendChatMessage(true, "Removed " + entity.getName());
                  }
               }

               if (this.mode.getCurrentMode().equalsIgnoreCase("AAC") && entity instanceof EntityPlayer && entity != this.getPlayer() && !entity.isInvisible() && entity.getCustomNameTag() == "") {
                  this.getWorld().removeEntity(entity);
                  if (this.debug.isToggled()) {
                     Fiveware.sendChatMessage(true, "Removed " + entity.getName());
                  }
               }
            }
         }

         if (event instanceof EventPacket && this.mode.getCurrentMode().equalsIgnoreCase("Matrix")) {
            if (((EventPacket)event).getPacket() instanceof S0CPacketSpawnPlayer) {
               S0CPacketSpawnPlayer packet = (S0CPacketSpawnPlayer)((EventPacket)event).getPacket();
               if (packet.func_148944_c().size() < 10) {
                  this.bots.add(packet.getEntityID());
               }
            } else if (((EventPacket)event).getPacket() instanceof S01PacketJoinGame) {
               this.bots.clear();
            }
         }

      }
   }
}
