package fiveware.modules.impl.player;

import fiveware.events.Event;
import fiveware.events.impl.EventUpdate;
import fiveware.interfaces.Methods;
import fiveware.modules.Category;
import fiveware.modules.Module;
import fiveware.userinterfaces.clickgui.settings.Setting;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class AutoArmor extends Module implements Methods {
   int[] chestplate = new int[]{311, 307, 315, 303, 299};
   int[] leggings = new int[]{312, 308, 316, 304, 300};
   int[] boots = new int[]{313, 309, 317, 305, 301};
   int[] helmet = new int[]{310, 306, 314, 302, 298};
   int modifiedDelay = 0;
   public Setting delay = new Setting("Delay", 1.0F, 1.0F, 10.0F, true, this);
   public Setting openinv = new Setting("Open Inv", false, this);

   public AutoArmor() {
      super("Auto Armor", "AutoArmor", "Automatically puts your armor on.", -1, 0, Category.PLAYER);
   }

   public void onEvent(Event event) {
      if (this.isToggled()) {
         if (event instanceof EventUpdate) {
            if (this.getCurrentScreen() == null && this.openinv.isToggled() && !(this.getCurrentScreen() instanceof GuiInventory)) {
               return;
            }

            this.autoArmor();
            this.betterArmor();
         }

      }
   }

   private void autoArmor() {
      int item = -1;
      this.modifiedDelay = (int)((float)this.modifiedDelay + this.delay.getCurrentValue());
      if (this.modifiedDelay >= 10) {
         int[] helmet1;
         int length;
         int i;
         int id;
         if (this.getPlayer().inventory.armorInventory[0] == null) {
            length = (helmet1 = this.boots).length;

            for(i = 0; i < length; ++i) {
               id = helmet1[i];
               if (this.getItem(id) != -1) {
                  item = this.getItem(id);
                  break;
               }
            }
         }

         if (this.getPlayer().inventory.armorInventory[1] == null) {
            length = (helmet1 = this.leggings).length;

            for(i = 0; i < length; ++i) {
               id = helmet1[i];
               if (this.getItem(id) != -1) {
                  item = this.getItem(id);
                  break;
               }
            }
         }

         if (this.getPlayer().inventory.armorInventory[2] == null) {
            length = (helmet1 = this.chestplate).length;

            for(i = 0; i < length; ++i) {
               id = helmet1[i];
               if (this.getItem(id) != -1) {
                  item = this.getItem(id);
                  break;
               }
            }
         }

         if (this.getPlayer().inventory.armorInventory[3] == null) {
            length = (helmet1 = this.helmet).length;

            for(i = 0; i < length; ++i) {
               id = helmet1[i];
               if (this.getItem(id) != -1) {
                  item = this.getItem(id);
                  break;
               }
            }
         }

         if (item != -1) {
            this.getPlayerController().windowClick(0, item, 0, 1, this.getPlayer());
            this.modifiedDelay = 0;
         }
      }

   }

   private void betterArmor() {
      this.modifiedDelay = (int)((float)this.modifiedDelay + this.delay.getCurrentValue());
      if (this.modifiedDelay >= 10 && (this.getPlayer().openContainer == null || this.getPlayer().openContainer.windowId == 0)) {
         boolean switchArmor = false;
         int item1 = -1;
         int[] array;
         int i;
         int j;
         int id;
         if (this.getPlayer().inventory.armorInventory[0] == null) {
            j = (array = this.boots).length;

            for(i = 0; i < j; ++i) {
               id = array[i];
               if (this.getItem(id) != -1) {
                  item1 = this.getItem(id);
                  break;
               }
            }
         }

         if (this.isBetterArmor(0, this.boots)) {
            item1 = 8;
            switchArmor = true;
         }

         if (this.getPlayer().inventory.armorInventory[3] == null) {
            j = (array = this.helmet).length;

            for(i = 0; i < j; ++i) {
               id = array[i];
               if (this.getItem(id) != -1) {
                  item1 = this.getItem(id);
                  break;
               }
            }
         }

         if (this.isBetterArmor(3, this.helmet)) {
            item1 = 5;
            switchArmor = true;
         }

         if (this.getPlayer().inventory.armorInventory[1] == null) {
            j = (array = this.leggings).length;

            for(i = 0; i < j; ++i) {
               id = array[i];
               if (this.getItem(id) != -1) {
                  item1 = this.getItem(id);
                  break;
               }
            }
         }

         if (this.isBetterArmor(1, this.leggings)) {
            item1 = 7;
            switchArmor = true;
         }

         if (this.getPlayer().inventory.armorInventory[2] == null) {
            j = (array = this.chestplate).length;

            for(i = 0; i < j; ++i) {
               id = array[i];
               if (this.getItem(id) != -1) {
                  item1 = this.getItem(id);
                  break;
               }
            }
         }

         if (this.isBetterArmor(2, this.chestplate)) {
            item1 = 6;
            switchArmor = true;
         }

         boolean b = false;
         ItemStack[] stackArray;
         int k = (stackArray = this.getPlayer().inventory.mainInventory).length;

         for(int j = 0; j < k; ++j) {
            ItemStack stack = stackArray[j];
            if (stack == null) {
               b = true;
               break;
            }
         }

         switchArmor = switchArmor && !b;
         if (item1 != -1) {
            this.getPlayerController().windowClick(0, item1, 0, switchArmor ? 4 : 1, this.getPlayer());
            this.modifiedDelay = 0;
         }
      }

   }

   private boolean isBetterArmor(int slot, int[] armorType) {
      if (this.getPlayer().inventory.armorInventory[slot] != null) {
         int currentIndex = 0;
         int invIndex = 0;
         int finalCurrentIndex = -1;
         int finalInvIndex = -1;
         int[] array = armorType;
         int j = armorType.length;

         int i;
         int armor;
         for(i = 0; i < j; ++i) {
            armor = array[i];
            if (Item.getIdFromItem(this.getPlayer().inventory.armorInventory[slot].getItem()) == armor) {
               finalCurrentIndex = currentIndex;
               break;
            }

            ++currentIndex;
         }

         array = armorType;
         j = armorType.length;

         for(i = 0; i < j; ++i) {
            armor = array[i];
            if (this.getItem(armor) != -1) {
               finalInvIndex = invIndex;
               break;
            }

            ++invIndex;
         }

         if (finalInvIndex > -1) {
            if (finalInvIndex < finalCurrentIndex) {
               return true;
            }

            return false;
         }
      }

      return false;
   }

   private int getItem(int id) {
      for(int i = 9; i < 45; ++i) {
         if (this.getPlayer().inventoryContainer.getSlot(i).getStack() != null && Item.getIdFromItem(this.getPlayer().inventoryContainer.getSlot(i).getStack().getItem()) == id) {
            return i;
         }
      }

      return -1;
   }
}
