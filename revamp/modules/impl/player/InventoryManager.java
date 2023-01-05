// 
// Decompiled by Procyon v0.5.36
// 

package revamp.modules.impl.player;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemPickaxe;
import java.util.Iterator;
import java.util.Arrays;
import net.minecraft.item.ItemTool;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.Item;
import net.minecraft.inventory.Slot;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C16PacketClientStatus;
import revamp.events.impl.EventPacket;
import net.minecraft.client.gui.inventory.GuiContainer;
import revamp.events.impl.EventUpdate;
import net.minecraft.client.gui.inventory.GuiChest;
import revamp.events.Event;
import revamp.modules.Category;
import revamp.userinterfaces.clickgui.settings.Setting;
import revamp.utilities.impl.time.Timer;
import revamp.interfaces.Methods;
import revamp.modules.Module;

public class InventoryManager extends Module implements Methods
{
    private boolean inventoryOpen;
    private Timer timer;
    public Setting spoof;
    public Setting delay;
    
    public InventoryManager() {
        super("InvManager", "InvManager", "Manages your inventory.", -1, 0, Category.PLAYER);
        this.timer = new Timer();
        this.spoof = new Setting("Spoof Inv", true, this);
        this.delay = new Setting("Delay", 150.0f, 20.0f, 300.0f, true, this);
    }
    
    @Override
    public void onEvent(final Event event) {
        if (!this.isToggled()) {
            return;
        }
        if (this.getCurrentScreen() instanceof GuiChest) {
            this.toggle();
        }
        if (event instanceof EventUpdate && (this.getCurrentScreen() == null || (this.getCurrentScreen() instanceof GuiContainer && ((GuiContainer)this.getCurrentScreen()).inventorySlots == this.getPlayer().inventoryContainer)) && this.purgeUnusedArmor() && this.purgeUnusedTools() && this.purgeJunk() && this.manageSword() && this.hotbarHasSpace()) {
            this.manageHotbar();
        }
        if (event instanceof EventPacket) {
            final Packet packet = ((EventPacket)event).getPacket();
            if (packet instanceof C16PacketClientStatus) {
                final C16PacketClientStatus open = (C16PacketClientStatus)packet;
                if (open.getStatus() == C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT) {
                    this.inventoryOpen = true;
                }
            }
            if (packet instanceof C0DPacketCloseWindow) {
                this.inventoryOpen = false;
            }
        }
    }
    
    public static float getSwordStrength(final ItemStack stack) {
        return ((stack.getItem() instanceof ItemSword) ? (EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, stack) * 1.25f) : 0.0f) + ((stack.getItem() instanceof ItemSword) ? ((float)EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, stack)) : 0.0f);
    }
    
    private boolean manageSword() {
        for (int i = 9; i < 45; ++i) {
            final Slot slot = this.getPlayer().inventoryContainer.getSlot(i);
            final ItemStack stack = slot.getStack();
            final boolean hotbar = i >= 36;
            if (stack != null) {
                final Item item = stack.getItem();
                if (!stack.getDisplayName().toLowerCase().contains("(right click)") && item instanceof ItemSword && this.timer.hasReached((long)this.delay.getCurrentValue())) {
                    this.moveToHotbarSlot1(i);
                    return false;
                }
            }
        }
        return true;
    }
    
    public boolean purgeUnusedArmor() {
        for (int i = 9; i < 45; ++i) {
            final Slot slot = this.getPlayer().inventoryContainer.getSlot(i);
            final ItemStack stack = slot.getStack();
            final boolean hotbar = i >= 36;
            if (stack != null) {
                final Item item = stack.getItem();
                if (item instanceof ItemArmor && !this.isBestArmor(stack) && this.timer.hasReached((long)this.delay.getCurrentValue())) {
                    this.purge(i);
                    return false;
                }
            }
        }
        return true;
    }
    
    public boolean purgeUnusedTools() {
        for (int i = 9; i < 45; ++i) {
            final Slot slot = this.getPlayer().inventoryContainer.getSlot(i);
            final ItemStack stack = slot.getStack();
            final boolean hotbar = i >= 36;
            if (stack != null) {
                final Item item = stack.getItem();
                if (item instanceof ItemTool && !stack.getDisplayName().toLowerCase().contains("(right click)") && !this.isBestTool(stack) && this.timer.hasReached((long)this.delay.getCurrentValue())) {
                    this.purge(i);
                    return false;
                }
                if (item instanceof ItemSword && !stack.getDisplayName().toLowerCase().contains("(right click)") && !this.isBestSword(stack) && this.timer.hasReached((long)this.delay.getCurrentValue())) {
                    this.purge(i);
                    return false;
                }
            }
        }
        return true;
    }
    
    public boolean purgeJunk() {
        for (int i = 9; i < 45; ++i) {
            final Slot slot = this.getPlayer().inventoryContainer.getSlot(i);
            final ItemStack stack = slot.getStack();
            final boolean hotbar = i >= 36;
            if (stack != null) {
                final Item item = stack.getItem();
                for (final String junk : Arrays.asList("stick", "egg", "string", "cake", "mushroom", "flint", "dyePowder", "feather", "chest", "snowball", "fish", "enchant", "exp", "shears", "anvil", "torch", "seeds", "leather", "reeds", "skull", "record", "piston", "snow", "poison", "tnt", "sugar")) {
                    if (!stack.getDisplayName().toLowerCase().contains("(right click)") && item.getUnlocalizedName().contains(junk) && this.timer.hasReached((long)this.delay.getCurrentValue())) {
                        this.purge(i);
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    public void manageHotbar() {
        for (int i = 9; i < 45; ++i) {
            final Slot slot = this.getPlayer().inventoryContainer.getSlot(i);
            final ItemStack stack = slot.getStack();
            final boolean hotbar = i >= 36;
            if (stack != null) {
                final Item item = stack.getItem();
                if (!stack.getDisplayName().toLowerCase().contains("(right click)") && ((item instanceof ItemPickaxe && this.hotbarNeedsItem(ItemPickaxe.class)) || (item instanceof ItemAxe && this.hotbarNeedsItem(ItemAxe.class)) || (item instanceof ItemSword && this.hotbarNeedsItem(ItemSword.class)) || (item instanceof ItemAppleGold && this.hotbarNeedsItem(ItemAppleGold.class)) || (item instanceof ItemEnderPearl && this.hotbarNeedsItem(ItemEnderPearl.class)) || (item instanceof ItemBlock && ((ItemBlock)item).getBlock().isFullCube() && !this.hotbarHasBlocks())) && !hotbar && this.timer.hasReached((long)this.delay.getCurrentValue())) {
                    this.moveToHotbar(i);
                    return;
                }
            }
        }
    }
    
    public boolean hotbarHasSpace() {
        for (int i = 36; i < 45; ++i) {
            final Slot slot = this.getPlayer().inventoryContainer.getSlot(i);
            if (slot.getStack() == null) {
                return true;
            }
        }
        return false;
    }
    
    public boolean hotbarNeedsItem(final Class<?> type) {
        for (int i = 36; i < 45; ++i) {
            final Slot slot = this.getPlayer().inventoryContainer.getSlot(i);
            if (type.isInstance(slot.getStack())) {
                return false;
            }
        }
        return true;
    }
    
    public boolean hotbarHasBlocks() {
        for (int i = 36; i < 45; ++i) {
            final Slot slot = this.getPlayer().inventoryContainer.getSlot(i);
            if (slot.getStack() != null && slot.getStack().getItem() instanceof ItemBlock && ((ItemBlock)slot.getStack().getItem()).getBlock().isFullCube()) {
                return true;
            }
        }
        return false;
    }
    
    public boolean isBestTool(final ItemStack compareStack) {
        for (int i = 9; i < 45; ++i) {
            final Slot slot = this.getPlayer().inventoryContainer.getSlot(i);
            final ItemStack stack = slot.getStack();
            final boolean hotbar = i >= 36;
            if (stack != null && compareStack != stack && stack.getItem() instanceof ItemTool) {
                final ItemTool item = (ItemTool)stack.getItem();
                final ItemTool compare = (ItemTool)compareStack.getItem();
                if (item.getClass() == compare.getClass() && compare.getStrVsBlock(stack, this.preferredBlock(item.getClass())) <= item.getStrVsBlock(compareStack, this.preferredBlock(compare.getClass()))) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public boolean isBestSword(final ItemStack compareStack) {
        for (int i = 9; i < 45; ++i) {
            final Slot slot = this.getPlayer().inventoryContainer.getSlot(i);
            final ItemStack stack = slot.getStack();
            final boolean hotbar = i >= 36;
            if (stack != null && compareStack != stack && stack.getItem() instanceof ItemSword) {
                final ItemSword item = (ItemSword)stack.getItem();
                final ItemSword compare = (ItemSword)compareStack.getItem();
                if (item.getClass() == compare.getClass() && compare.attackDamage + getSwordStrength(compareStack) <= item.attackDamage + getSwordStrength(stack)) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public Block preferredBlock(final Class clazz) {
        return (clazz == ItemPickaxe.class) ? Blocks.cobblestone : ((clazz == ItemAxe.class) ? Blocks.log : Blocks.dirt);
    }
    
    public boolean isBestArmor(final ItemStack compareStack) {
        for (int i = 0; i < 45; ++i) {
            final Slot slot = this.getPlayer().inventoryContainer.getSlot(i);
            final ItemStack stack = slot.getStack();
            final boolean hotbar = i >= 36;
            if (stack != null && compareStack != stack && stack.getItem() instanceof ItemArmor) {
                final ItemArmor item = (ItemArmor)stack.getItem();
                final ItemArmor compare = (ItemArmor)compareStack.getItem();
                if (item.armorType == compare.armorType && getProtectionValue(compareStack) <= getProtectionValue(stack)) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public boolean has(final Item item, int count) {
        for (int i = 9; i < 45; ++i) {
            final Slot slot = this.getPlayer().inventoryContainer.getSlot(i);
            if (slot.getStack() != null && slot.getStack().getItem().equals(item)) {
                count -= slot.getStack().stackSize;
            }
        }
        return count >= 0;
    }
    
    public void moveToHotbar(final int slot) {
        if (this.spoof.isToggled()) {
            this.openInvPacket();
        }
        this.getPlayerController().windowClick(this.getPlayer().inventoryContainer.windowId, slot, 0, 1, this.getPlayer());
        if (this.spoof.isToggled()) {
            this.closeInvPacket();
        }
    }
    
    public void moveToHotbarSlot1(final int slot) {
        if (this.spoof.isToggled()) {
            this.openInvPacket();
        }
        this.getPlayerController().windowClick(this.getPlayer().inventoryContainer.windowId, slot, 0, 2, this.getPlayer());
        if (this.spoof.isToggled()) {
            this.closeInvPacket();
        }
    }
    
    public void purge(final int slot) {
        if (this.spoof.isToggled()) {
            this.openInvPacket();
        }
        this.getPlayerController().windowClick(this.getPlayer().inventoryContainer.windowId, slot, 1, 4, this.getPlayer());
        if (this.spoof.isToggled()) {
            this.closeInvPacket();
        }
    }
    
    public void openInvPacket() {
        if (!this.inventoryOpen) {
            this.sendPacket(new C16PacketClientStatus(C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT));
        }
        this.inventoryOpen = true;
    }
    
    public void closeInvPacket() {
        if (this.inventoryOpen) {
            this.sendPacket(new C0DPacketCloseWindow(this.getPlayer().inventoryContainer.windowId));
        }
        this.inventoryOpen = false;
    }
    
    public static double getProtectionValue(final ItemStack stack) {
        return (stack.getItem() instanceof ItemArmor) ? (((ItemArmor)stack.getItem()).damageReduceAmount + (100 - ((ItemArmor)stack.getItem()).damageReduceAmount * 4) * EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, stack) * 4 * 0.0075) : 0.0;
    }
}
