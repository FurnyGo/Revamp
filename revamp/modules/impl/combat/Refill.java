// 
// Decompiled by Procyon v0.5.36
// 

package revamp.modules.impl.combat;

import java.util.Iterator;
import net.minecraft.potion.PotionEffect;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemSoup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import revamp.events.impl.EventPre;
import revamp.events.Event;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.gui.inventory.GuiInventory;
import revamp.modules.Category;
import revamp.userinterfaces.clickgui.settings.Setting;
import revamp.utilities.impl.time.Timer;
import revamp.interfaces.Methods;
import revamp.modules.Module;

public class Refill extends Module implements Methods
{
    private Timer timer;
    public Setting mode;
    public Setting delay;
    
    public Refill() {
        super("Refill", "Refill", "Refills pots for you.", -1, 0, Category.COMBAT);
        this.timer = new Timer();
        this.mode = new Setting("Mode", new String[] { "Soups", "Potions" }, "Soups", this);
        this.delay = new Setting("Delay", 100.0f, 0.0f, 1000.0f, true, this);
    }
    
    @Override
    public void onEnable() {
        this.displayScreen(new GuiInventory(this.getPlayer()));
    }
    
    @Override
    public void onEvent(final Event event) {
        if (!this.isToggled()) {
            return;
        }
        if (event instanceof EventPre) {
            this.setNameWithOutSpace("Refill" + (this.information() ? (" §7" + this.delay.getCurrentValue()) : ""));
            for (int i = 0; i < 9; ++i) {
                if (this.mode.getCurrentMode().equalsIgnoreCase("Potions")) {
                    if (!this.hotbarFull() && this.timer.hasReached((long)this.delay.getCurrentValue() * 2L) && this.inventoryHasPot(Potion.heal)) {
                        this.placePot(Potion.heal);
                        this.timer.reset();
                        if (!this.inventoryHasPot(Potion.heal)) {
                            this.toggle();
                        }
                    }
                    if (this.hotbarFull()) {
                        this.toggle();
                    }
                    if (!this.inventoryHasPot(Potion.heal)) {
                        this.toggle();
                    }
                }
                else if (this.mode.getCurrentMode().equalsIgnoreCase("Soups")) {
                    if (!this.hotbarFull() && this.timer.hasReached((long)this.delay.getCurrentValue()) && this.inventoryHasItem(Item.getItemById(282))) {
                        this.placeSoup();
                        this.timer.reset();
                        if (!this.inventoryHasItem(Item.getItemById(282))) {
                            this.toggle();
                        }
                    }
                    if (this.hotbarFull()) {
                        this.toggle();
                    }
                    if (!this.inventoryHasItem(Item.getItemById(282))) {
                        this.toggle();
                    }
                }
            }
        }
    }
    
    @Override
    public void onDisable() {
        this.displayScreen(null);
    }
    
    public boolean inventoryHasItem(final Item item) {
        for (int index = 0; index <= 36; ++index) {
            final ItemStack stack = this.getPlayer().inventory.getStackInSlot(index);
            if (stack != null && stack.getItem() == item) {
                return true;
            }
        }
        return false;
    }
    
    public boolean hotbarFull() {
        for (int index = 0; index <= 8; ++index) {
            final ItemStack stack = this.getPlayer().inventory.getStackInSlot(index);
            if (stack == null) {
                return false;
            }
        }
        return true;
    }
    
    public void placeSoup() {
        for (int index = 9; index <= 36; ++index) {
            final ItemStack stack = this.getPlayer().inventoryContainer.getSlot(index).getStack();
            if (stack != null && stack.getItem() instanceof ItemSoup) {
                this.getPlayerController().windowClick(0, index, 0, 1, this.getPlayer());
                break;
            }
        }
    }
    
    public boolean inventoryHasPot(final Potion effect) {
        for (int index = 0; index <= 36; ++index) {
            final ItemStack stack = this.getPlayer().inventory.getStackInSlot(index);
            if (stack != null && this.isPotion(stack, effect, true)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean isPotion(final ItemStack stack, final Potion potion, final boolean splash) {
        if (stack == null) {
            return false;
        }
        if (!(stack.getItem() instanceof ItemPotion)) {
            return false;
        }
        final ItemPotion potionItem = (ItemPotion)stack.getItem();
        if (splash && !ItemPotion.isSplash(stack.getItemDamage())) {
            return false;
        }
        if (potionItem.getEffects(stack) == null) {
            return potion == null;
        }
        if (potion == null) {
            return false;
        }
        for (final Object o : potionItem.getEffects(stack)) {
            final PotionEffect effect = (PotionEffect)o;
            if (effect.getPotionID() == potion.getId()) {
                return true;
            }
        }
        return false;
    }
    
    public void placePot(final Potion effect) {
        for (int index = 9; index <= 36; ++index) {
            final ItemStack stack = this.getPlayer().inventoryContainer.getSlot(index).getStack();
            if (stack != null && this.isPotion(stack, effect, true)) {
                this.getPlayerController().windowClick(this.getPlayer().openContainer.windowId, index, 0, 1, this.getPlayer());
                break;
            }
        }
    }
}
