package fiveware.modules.impl.combat;

import fiveware.events.Event;
import fiveware.events.impl.EventUpdate;
import fiveware.interfaces.Methods;
import fiveware.modules.Category;
import fiveware.modules.Module;
import fiveware.userinterfaces.clickgui.settings.Setting;
import fiveware.utilities.time.Timer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;

public class Refill extends Module implements Methods {
   private Timer timer = new Timer();
   private Item item;
   public Setting mode = new Setting("Mode", new String[]{"Soup", "Potion"}, "Soup", this);
   public Setting delay = new Setting("Delay", 100.0F, 10.0F, 1000.0F, true, this);

   public Refill() {
      super("Refill", "Refill", "Refills for you.", -1, 0, Category.COMBAT);
   }

   public void onEvent(Event event) {
      if (this.isToggled()) {
         if (event instanceof EventUpdate) {
            if (this.mode.getCurrentMode().equalsIgnoreCase("Soup")) {
               this.item = Items.mushroom_stew;
            } else {
               this.item = ItemPotion.getItemById(373);
            }

            if (this.getCurrentScreen() instanceof GuiInventory && this.isHotbarFull() && this.timer.hasReached((long)this.delay.getCurrentValue())) {
               for(int i = 9; i < 37; ++i) {
                  ItemStack item1 = this.getPlayer().inventoryContainer.getSlot(i).getStack();
                  if (item1 != null && item1.getItem() == this.item) {
                     this.getPlayerController().windowClick(0, i, 0, 1, this.getPlayer());
                     break;
                  }
               }

               this.timer.reset();
            }
         }

      }
   }

   private boolean isHotbarFull() {
      for(int i = 0; i <= 36; ++i) {
         if (this.getPlayer().inventory.getStackInSlot(i) == null) {
            return true;
         }
      }

      return false;
   }
}
