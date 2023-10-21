package fiveware.modules.impl.other;

import fiveware.Fiveware;
import fiveware.events.Event;
import fiveware.events.impl.EventUpdate;
import fiveware.interfaces.Methods;
import fiveware.modules.Category;
import fiveware.modules.Module;
import fiveware.userinterfaces.clickgui.settings.Setting;
import java.util.Iterator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemSword;

public class MurderMystery extends Module implements Methods {
   public Setting murderer = new Setting("Murderer", true, this);
   public Setting detective = new Setting("Detective", false, this);

   public MurderMystery() {
      super("Murder Mystery", "MurderMystery", "Exploits murder mystery.", -1, 0, Category.OTHER);
   }

   public void onEvent(Event event) {
      if (this.isToggled()) {
         if (event instanceof EventUpdate) {
            Iterator var3 = this.getWorld().loadedEntityList.iterator();

            while(var3.hasNext()) {
               Entity entity = (Entity)var3.next();
               if (entity instanceof EntityPlayer) {
                  EntityPlayer player = (EntityPlayer)entity;
                  if (player != this.getPlayer() && player.getCurrentEquippedItem() != null) {
                     if (player.getCurrentEquippedItem().getItem() instanceof ItemSword && this.murderer.isToggled()) {
                        Fiveware.sendChatMessage(true, "§cI found the §4murderer§c! §f| §4§l" + player.getName());
                     }

                     if (player.getCurrentEquippedItem().getItem() instanceof ItemBow && this.detective.isToggled()) {
                        Fiveware.sendChatMessage(true, "§aI found the §2detective§a! §f| §2§l" + player.getName());
                     }
                  }
               }
            }
         }

      }
   }
}
