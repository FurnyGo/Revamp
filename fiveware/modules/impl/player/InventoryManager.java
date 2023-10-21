package fiveware.modules.impl.player;

import fiveware.events.Event;
import fiveware.events.impl.EventUpdate;
import fiveware.interfaces.Methods;
import fiveware.modules.Category;
import fiveware.modules.Module;
import fiveware.userinterfaces.clickgui.settings.Setting;
import fiveware.utilities.time.Timer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;

public class InventoryManager extends Module implements Methods {
   private List allSwords = new ArrayList();
   private List trash = new ArrayList();
   private List[] allArmors = new List[4];
   private boolean cleaning;
   private int[] bestArmorSlot;
   private int bestSwordSlot;
   private Timer timer = new Timer();
   public Setting delay = new Setting("Delay", 2.5F, 0.0F, 5.0F, false, this);
   public Setting invonly = new Setting("Inventory Only", true, this);

   public InventoryManager() {
      super("Inv Manager", "InvManager", "Manages your inventory.", -1, 0, Category.PLAYER);
   }

   public void onEvent(Event event) {
      if (this.isToggled()) {
         if (event instanceof EventUpdate) {
            if (this.getCurrentScreen() instanceof GuiChat || !(this.getCurrentScreen() instanceof GuiInventory) && this.invonly.isToggled()) {
               return;
            }

            this.collectItems();
            this.collectBestArmor();
            this.collectTrash();
            boolean trashPresent = this.trash.size() > 0;
            int windowId = this.getPlayer().openContainer.windowId;
            if (trashPresent) {
               if (!this.cleaning) {
                  this.cleaning = true;
                  this.sendPacket(new C16PacketClientStatus(C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT));
               }

               for(int i = 0; i < this.trash.size(); ++i) {
                  int slot = (Integer)this.trash.get(i);
                  if (this.checkDelay()) {
                     break;
                  }

                  this.getPlayerController().windowClick(windowId, slot < 9 ? slot + 36 : slot, 1, 4, this.getPlayer());
                  this.timer.reset();
               }

               if (this.cleaning) {
                  this.getPlayer().sendQueue.addToSendQueue(new C0DPacketCloseWindow(windowId));
                  this.cleaning = false;
               }

               if (this.bestSwordSlot != -1 && !this.checkDelay()) {
                  this.getPlayerController().windowClick(windowId, this.bestSwordSlot < 9 ? this.bestSwordSlot + 36 : this.bestSwordSlot, 1, 2, this.getPlayer());
                  this.timer.reset();
               }
            }
         }

      }
   }

   private boolean checkDelay() {
      return !this.timer.hasReached((long)this.delay.getCurrentValue() * 100L);
   }

   public void collectItems() {
      this.bestSwordSlot = -1;
      this.allSwords.clear();
      float bestSwordDamage = -1.0F;

      for(int i = 0; i < 36; ++i) {
         ItemStack itemStack = this.getPlayer().inventory.getStackInSlot(i);
         if (itemStack != null && itemStack.getItem() != null && itemStack.getItem() instanceof ItemSword) {
            this.allSwords.add(i);
            if (bestSwordDamage < this.getDamageLevel(itemStack)) {
               bestSwordDamage = this.getDamageLevel(itemStack);
               this.bestSwordSlot = i;
            }
         }
      }

   }

   private void collectBestArmor() {
      int[] bestArmorDamageReducement = new int[4];
      this.bestArmorSlot = new int[4];
      Arrays.fill(bestArmorDamageReducement, -1);
      Arrays.fill(this.bestArmorSlot, -1);

      int i;
      int armorType;
      ItemStack itemStack;
      ItemArmor armor;
      for(i = 0; i < this.bestArmorSlot.length; ++i) {
         itemStack = this.getPlayer().inventory.armorItemInSlot(i);
         this.allArmors[i] = new ArrayList();
         if (itemStack != null && itemStack.getItem() != null && itemStack.getItem() instanceof ItemArmor) {
            armor = (ItemArmor)itemStack.getItem();
            armorType = armor.damageReduceAmount + EnchantmentHelper.getEnchantmentModifierDamage(new ItemStack[]{itemStack}, DamageSource.generic);
            bestArmorDamageReducement[i] = armorType;
         }
      }

      for(i = 0; i < 36; ++i) {
         itemStack = this.getPlayer().inventory.getStackInSlot(i);
         if (itemStack != null && itemStack.getItem() != null && itemStack.getItem() instanceof ItemArmor) {
            armor = (ItemArmor)itemStack.getItem();
            armorType = 3 - armor.armorType;
            this.allArmors[armorType].add(i);
            int slotProtectionLevel = armor.damageReduceAmount + EnchantmentHelper.getEnchantmentModifierDamage(new ItemStack[]{itemStack}, DamageSource.generic);
            if (bestArmorDamageReducement[armorType] < slotProtectionLevel) {
               bestArmorDamageReducement[armorType] = slotProtectionLevel;
               this.bestArmorSlot[armorType] = i;
            }
         }
      }

   }

   private void collectTrash() {
      this.trash.clear();

      int i;
      for(i = 0; i < 36; ++i) {
         ItemStack itemStack = this.getPlayer().inventory.getStackInSlot(i);
         if (itemStack != null && itemStack.getItem() != null && !this.isValidItem(itemStack)) {
            this.trash.add(i);
         }
      }

      int i1;
      List integers;
      for(i = 0; i < this.allArmors.length; ++i) {
         integers = this.allArmors[i];
         if (integers != null) {
            List integers = this.trash;
            i1 = 0;

            for(int armorItemSize = integers.size(); i1 < armorItemSize; ++i1) {
               Integer slot = (Integer)integers.get(i1);
               if (slot != this.bestArmorSlot[i]) {
                  integers.add(slot);
               }
            }
         }
      }

      integers = this.trash;
      int i1 = 0;

      for(i1 = this.allSwords.size(); i1 < i1; ++i1) {
         Integer slot = (Integer)this.allSwords.get(i1);
         if (slot != this.bestSwordSlot) {
            integers.add(slot);
         }
      }

   }

   private float getDamageLevel(ItemStack stack) {
      if (stack.getItem() instanceof ItemSword) {
         ItemSword sword = (ItemSword)stack.getItem();
         float sharpness = (float)EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, stack) * 1.25F;
         float fireAspect = (float)EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, stack) * 1.5F;
         return sword.getDamageVsEntity() + sharpness + fireAspect;
      } else {
         return 0.0F;
      }
   }

   private boolean isValidItem(ItemStack itemStack) {
      if (itemStack.getDisplayName().startsWith("§a")) {
         return true;
      } else {
         return itemStack.getItem() instanceof ItemArmor || itemStack.getItem() instanceof ItemSword || itemStack.getItem() instanceof ItemTool || itemStack.getItem() instanceof ItemFood || itemStack.getItem() instanceof ItemPotion && !this.isBadPotion(itemStack) || itemStack.getItem() instanceof ItemBlock || itemStack.getDisplayName().contains("Play") || itemStack.getDisplayName().contains("Game") || itemStack.getDisplayName().contains("Right Click") || itemStack.getUnlocalizedName().contains("ender");
      }
   }

   private boolean isBadPotion(ItemStack stack) {
      if (stack != null && stack.getItem() instanceof ItemPotion) {
         ItemPotion potion = (ItemPotion)stack.getItem();
         if (ItemPotion.isSplash(stack.getItemDamage())) {
            Iterator var2 = potion.getEffects(stack).iterator();

            while(var2.hasNext()) {
               Object o = var2.next();
               PotionEffect effect = (PotionEffect)o;
               if (effect.getPotionID() == Potion.poison.getId() || effect.getPotionID() == Potion.harm.getId() || effect.getPotionID() == Potion.moveSlowdown.getId() || effect.getPotionID() == Potion.weakness.getId()) {
                  return true;
               }
            }
         }
      }

      return false;
   }
}
