// 
// Decompiled by Procyon v0.5.36
// 

package revamp.modules.impl.player;

import java.util.Iterator;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemEgg;
import net.minecraft.item.ItemGlassBottle;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.client.gui.inventory.GuiInventory;
import revamp.events.impl.EventPre;
import revamp.events.Event;
import revamp.modules.Category;
import revamp.userinterfaces.clickgui.settings.Setting;
import revamp.utilities.impl.time.Timer;
import revamp.interfaces.Methods;
import revamp.modules.Module;

public class InventoryManager extends Module implements Methods
{
    private boolean cleaning;
    private boolean equipping;
    private Timer timer;
    public Setting delay;
    
    public InventoryManager() {
        super("InvManager", "InvManager", "Manages your inventory.", -1, 0, Category.PLAYER);
        this.timer = new Timer();
        this.delay = new Setting("Delay", 150.0f, 1.0f, 500.0f, true, this);
    }
    
    @Override
    public void onEvent(final Event event) {
        if (!this.isToggled()) {
            return;
        }
        if (event instanceof EventPre) {
            this.setNameWithOutSpace("InvManager" + (this.information() ? (" §7" + this.delay.getCurrentValue()) : ""));
            int slotID = -1;
            double maxProt = -1.0;
            int switchArmor = -1;
            if (this.getCurrentScreen() == null || this.getCurrentScreen() instanceof GuiInventory) {
                for (int i = 9; i < 45; ++i) {
                    if (this.getPlayer().inventoryContainer.getSlot(i).getHasStack()) {
                        final ItemStack is = this.getPlayer().inventoryContainer.getSlot(i).getStack();
                        if (this.isTrash(is) && this.timer.hasReached((long)this.delay.getCurrentValue())) {
                            if (!this.cleaning) {
                                this.cleaning = true;
                                this.getPlayer().sendQueue.addToSendQueue(new C16PacketClientStatus(C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT));
                            }
                            this.getPlayerController().windowClick(this.getPlayer().inventoryContainer.windowId, i, 0, 0, this.getPlayer());
                            this.getPlayerController().windowClick(this.getPlayer().inventoryContainer.windowId, -999, 0, 0, this.getPlayer());
                            this.timer.reset();
                            break;
                        }
                    }
                    if (i == 44 && this.cleaning) {
                        this.getPlayer().sendQueue.addToSendQueue(new C0DPacketCloseWindow(0));
                        this.cleaning = false;
                    }
                    final ItemStack stack = this.getPlayer().inventoryContainer.getSlot(i).getStack();
                    if (stack != null && (this.canEquip(stack) || (this.isBestArmor(stack) && !this.canEquip(stack)))) {
                        if (this.isBestArmor(stack) && switchArmor == -1) {
                            switchArmor = this.getSlotToSwap(stack);
                        }
                        final double protValue = this.getProtectionValue(stack);
                        if (protValue >= maxProt) {
                            slotID = i;
                            maxProt = protValue;
                        }
                    }
                }
                if (slotID != -1) {
                    if (this.timer.hasReached((long)this.delay.getCurrentValue())) {
                        if (!this.equipping) {
                            this.getPlayer().sendQueue.addToSendQueue(new C16PacketClientStatus(C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT));
                            this.equipping = true;
                        }
                        if (switchArmor != -1) {
                            this.getPlayerController().windowClick(this.getPlayer().inventoryContainer.windowId, 4 + switchArmor, 0, 4, this.getPlayer());
                        }
                        else {
                            this.getPlayerController().windowClick(this.getPlayer().inventoryContainer.windowId, slotID, 0, 1, this.getPlayer());
                        }
                        this.timer.reset();
                    }
                }
                else if (this.equipping) {
                    this.getPlayer().sendQueue.addToSendQueue(new C0DPacketCloseWindow(0));
                    this.equipping = false;
                }
            }
        }
    }
    
    private boolean canEquip(final ItemStack stack) {
        return stack.getItem() instanceof ItemArmor && ((this.getPlayer().getEquipmentInSlot(1) == null && stack.getUnlocalizedName().contains("boots")) || (this.getPlayer().getEquipmentInSlot(2) == null && stack.getUnlocalizedName().contains("leggings")) || (this.getPlayer().getEquipmentInSlot(3) == null && stack.getUnlocalizedName().contains("chestplate")) || (this.getPlayer().getEquipmentInSlot(4) == null && stack.getUnlocalizedName().contains("helmet")));
    }
    
    private double getProtectionValue(final ItemStack stack) {
        return (stack.getItem() instanceof ItemArmor) ? (((ItemArmor)stack.getItem()).damageReduceAmount + (100 - ((ItemArmor)stack.getItem()).damageReduceAmount) * EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, stack) * 0.0075) : 0.0;
    }
    
    private boolean isBestArmor(final ItemStack stack) {
        try {
            if (stack.getItem() instanceof ItemArmor) {
                if (this.getPlayer().getEquipmentInSlot(1) != null && stack.getUnlocalizedName().contains("boots")) {
                    assert this.getPlayer().getEquipmentInSlot(1).getItem() instanceof ItemArmor;
                    if (this.getProtectionValue(stack) + ((ItemArmor)stack.getItem()).damageReduceAmount > this.getProtectionValue(this.getPlayer().getEquipmentInSlot(1)) + ((ItemArmor)this.getPlayer().getEquipmentInSlot(1).getItem()).damageReduceAmount) {
                        return true;
                    }
                }
                if (this.getPlayer().getEquipmentInSlot(2) != null && stack.getUnlocalizedName().contains("leggings")) {
                    assert this.getPlayer().getEquipmentInSlot(2).getItem() instanceof ItemArmor;
                    if (this.getProtectionValue(stack) + ((ItemArmor)stack.getItem()).damageReduceAmount > this.getProtectionValue(this.getPlayer().getEquipmentInSlot(2)) + ((ItemArmor)this.getPlayer().getEquipmentInSlot(2).getItem()).damageReduceAmount) {
                        return true;
                    }
                }
                if (this.getPlayer().getEquipmentInSlot(3) != null && stack.getUnlocalizedName().contains("chestplate")) {
                    assert this.getPlayer().getEquipmentInSlot(3).getItem() instanceof ItemArmor;
                    if (this.getProtectionValue(stack) + ((ItemArmor)stack.getItem()).damageReduceAmount > this.getProtectionValue(this.getPlayer().getEquipmentInSlot(3)) + ((ItemArmor)this.getPlayer().getEquipmentInSlot(3).getItem()).damageReduceAmount) {
                        return true;
                    }
                }
                if (this.getPlayer().getEquipmentInSlot(4) != null && stack.getUnlocalizedName().contains("helmet")) {
                    assert this.getPlayer().getEquipmentInSlot(4).getItem() instanceof ItemArmor;
                    return this.getProtectionValue(stack) + ((ItemArmor)stack.getItem()).damageReduceAmount > this.getProtectionValue(this.getPlayer().getEquipmentInSlot(4)) + ((ItemArmor)this.getPlayer().getEquipmentInSlot(4).getItem()).damageReduceAmount;
                }
            }
            return false;
        }
        catch (Exception var3) {
            return false;
        }
    }
    
    private int getSlotToSwap(final ItemStack stack) {
        if (stack.getItem() instanceof ItemArmor) {
            if (this.getPlayer().getEquipmentInSlot(1) == null) {
                return 4;
            }
            if (this.getPlayer().getEquipmentInSlot(1) != null && stack.getUnlocalizedName().contains("boots")) {
                assert this.getPlayer().getEquipmentInSlot(1).getItem() instanceof ItemArmor;
                if (this.getProtectionValue(stack) + ((ItemArmor)stack.getItem()).damageReduceAmount > this.getProtectionValue(this.getPlayer().getEquipmentInSlot(1)) + ((ItemArmor)this.getPlayer().getEquipmentInSlot(1).getItem()).damageReduceAmount) {
                    return 4;
                }
            }
            if (this.getPlayer().getEquipmentInSlot(1) == null) {
                return 3;
            }
            if (this.getPlayer().getEquipmentInSlot(2) != null && stack.getUnlocalizedName().contains("leggings")) {
                assert this.getPlayer().getEquipmentInSlot(2).getItem() instanceof ItemArmor;
                if (this.getProtectionValue(stack) + ((ItemArmor)stack.getItem()).damageReduceAmount > this.getProtectionValue(this.getPlayer().getEquipmentInSlot(2)) + ((ItemArmor)this.getPlayer().getEquipmentInSlot(2).getItem()).damageReduceAmount) {
                    return 3;
                }
            }
            if (this.getPlayer().getEquipmentInSlot(1) == null) {
                return 2;
            }
            if (this.getPlayer().getEquipmentInSlot(3) != null && stack.getUnlocalizedName().contains("chestplate") && this.getProtectionValue(stack) + ((ItemArmor)stack.getItem()).damageReduceAmount > this.getProtectionValue(this.getPlayer().getEquipmentInSlot(3)) + ((ItemArmor)this.getPlayer().getEquipmentInSlot(3).getItem()).damageReduceAmount) {
                return 2;
            }
            if (this.getPlayer().getEquipmentInSlot(1) == null) {
                return 1;
            }
            if (this.getPlayer().getEquipmentInSlot(4) != null && stack.getUnlocalizedName().contains("helmet")) {
                assert this.getPlayer().getEquipmentInSlot(1).getItem() instanceof ItemArmor;
                if (this.getProtectionValue(stack) + ((ItemArmor)stack.getItem()).damageReduceAmount > this.getProtectionValue(this.getPlayer().getEquipmentInSlot(4)) + ((ItemArmor)this.getPlayer().getEquipmentInSlot(4).getItem()).damageReduceAmount) {
                    return 1;
                }
            }
        }
        return -1;
    }
    
    private boolean isTrash(final ItemStack item) {
        return item.getItem().getUnlocalizedName().contains("tnt") || item.getDisplayName().contains("frog") || item.getItem().getUnlocalizedName().contains("stick") || item.getItem().getUnlocalizedName().contains("string") || item.getItem().getUnlocalizedName().contains("flint") || item.getItem().getUnlocalizedName().contains("feather") || item.getItem().getUnlocalizedName().contains("bucket") || item.getItem().getUnlocalizedName().contains("snow") || item.getItem().getUnlocalizedName().contains("enchant") || item.getItem().getUnlocalizedName().contains("exp") || item.getItem().getUnlocalizedName().contains("shears") || item.getItem().getUnlocalizedName().contains("arrow") || item.getItem().getUnlocalizedName().contains("anvil") || item.getItem().getUnlocalizedName().contains("torch") || item.getItem().getUnlocalizedName().contains("seeds") || item.getItem().getUnlocalizedName().contains("leather") || item.getItem().getUnlocalizedName().contains("boat") || item.getItem().getUnlocalizedName().contains("fishing") || item.getItem().getUnlocalizedName().contains("wheat") || item.getItem().getUnlocalizedName().contains("flower") || item.getItem().getUnlocalizedName().contains("record") || item.getItem().getUnlocalizedName().contains("note") || item.getItem().getUnlocalizedName().contains("sugar") || item.getItem().getUnlocalizedName().contains("wire") || item.getItem().getUnlocalizedName().contains("trip") || item.getItem().getUnlocalizedName().contains("slime") || item.getItem().getUnlocalizedName().contains("web") || item.getItem() instanceof ItemGlassBottle || item.getItem().getUnlocalizedName().contains("piston") || (item.getItem().getUnlocalizedName().contains("potion") && this.isBadPotion(item)) || item.getItem() instanceof ItemEgg || (item.getItem().getUnlocalizedName().contains("bow") && !item.getDisplayName().contains("Kit")) || item.getItem().getUnlocalizedName().contains("Raw") || item.getItem().getUnlocalizedName().contains("milk");
    }
    
    private boolean isBadPotion(final ItemStack stack) {
        if (stack != null && stack.getItem() instanceof ItemPotion) {
            final ItemPotion potion = (ItemPotion)stack.getItem();
            if (ItemPotion.isSplash(stack.getItemDamage())) {
                for (final Object o : potion.getEffects(stack)) {
                    final PotionEffect effect = (PotionEffect)o;
                    if (effect.getPotionID() == Potion.poison.getId() || effect.getPotionID() == Potion.harm.getId() || effect.getPotionID() == Potion.moveSlowdown.getId() || effect.getPotionID() == Potion.weakness.getId()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
