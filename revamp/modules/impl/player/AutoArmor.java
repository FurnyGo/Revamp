// 
// Decompiled by Procyon v0.5.36
// 

package revamp.modules.impl.player;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.gui.inventory.GuiInventory;
import revamp.events.impl.EventUpdate;
import revamp.events.Event;
import revamp.modules.Category;
import revamp.userinterfaces.clickgui.settings.Setting;
import revamp.interfaces.Methods;
import revamp.modules.Module;

public class AutoArmor extends Module implements Methods
{
    int[] chestplate;
    int[] leggings;
    int[] boots;
    int[] helmet;
    int modifiedDelay;
    public Setting delay;
    public Setting openinv;
    
    public AutoArmor() {
        super("AutoArmor", "AutoArmor", "Automatically puts your armor on.", -1, 0, Category.PLAYER);
        this.chestplate = new int[] { 311, 307, 315, 303, 299 };
        this.leggings = new int[] { 312, 308, 316, 304, 300 };
        this.boots = new int[] { 313, 309, 317, 305, 301 };
        this.helmet = new int[] { 310, 306, 314, 302, 298 };
        this.modifiedDelay = 0;
        this.delay = new Setting("Delay", 1.0f, 1.0f, 10.0f, true, this);
        this.openinv = new Setting("Open Inv", false, this);
    }
    
    @Override
    public void onEvent(final Event event) {
        if (!this.isToggled()) {
            return;
        }
        if (event instanceof EventUpdate) {
            this.setNameWithOutSpace("AutoArmor" + (this.information() ? (" §7" + this.delay.getCurrentValue()) : ""));
            if (this.getCurrentScreen() == null && this.openinv.isToggled() && !(this.getCurrentScreen() instanceof GuiInventory)) {
                return;
            }
            this.autoArmor();
            this.betterArmor();
        }
    }
    
    private void autoArmor() {
        int item = -1;
        this.modifiedDelay += (int)this.delay.getCurrentValue();
        if (this.modifiedDelay >= 10) {
            if (this.getPlayer().inventory.armorInventory[0] == null) {
                int[] boots1;
                for (int length = (boots1 = this.boots).length, i = 0; i < length; ++i) {
                    final int id = boots1[i];
                    if (this.getItem(id) != -1) {
                        item = this.getItem(id);
                        break;
                    }
                }
            }
            if (this.getPlayer().inventory.armorInventory[1] == null) {
                int[] leggings1;
                for (int length = (leggings1 = this.leggings).length, i = 0; i < length; ++i) {
                    final int id = leggings1[i];
                    if (this.getItem(id) != -1) {
                        item = this.getItem(id);
                        break;
                    }
                }
            }
            if (this.getPlayer().inventory.armorInventory[2] == null) {
                int[] chestplate1;
                for (int length = (chestplate1 = this.chestplate).length, i = 0; i < length; ++i) {
                    final int id = chestplate1[i];
                    if (this.getItem(id) != -1) {
                        item = this.getItem(id);
                        break;
                    }
                }
            }
            if (this.getPlayer().inventory.armorInventory[3] == null) {
                int[] helmet1;
                for (int length = (helmet1 = this.helmet).length, i = 0; i < length; ++i) {
                    final int id = helmet1[i];
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
        this.modifiedDelay += (int)this.delay.getCurrentValue();
        if (this.modifiedDelay >= 10 && (this.getPlayer().openContainer == null || this.getPlayer().openContainer.windowId == 0)) {
            boolean switchArmor = false;
            int item1 = -1;
            if (this.getPlayer().inventory.armorInventory[0] == null) {
                int[] array;
                for (int j = (array = this.boots).length, i = 0; i < j; ++i) {
                    final int id = array[i];
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
                int[] array;
                for (int j = (array = this.helmet).length, i = 0; i < j; ++i) {
                    final int id = array[i];
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
                int[] array;
                for (int j = (array = this.leggings).length, i = 0; i < j; ++i) {
                    final int id = array[i];
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
                int[] array;
                for (int j = (array = this.chestplate).length, i = 0; i < j; ++i) {
                    final int id = array[i];
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
            for (int k = (stackArray = this.getPlayer().inventory.mainInventory).length, l = 0; l < k; ++l) {
                final ItemStack stack = stackArray[l];
                if (stack == null) {
                    b = true;
                    break;
                }
            }
            switchArmor = (switchArmor && !b);
            if (item1 != -1) {
                this.getPlayerController().windowClick(0, item1, 0, switchArmor ? 4 : 1, this.getPlayer());
                this.modifiedDelay = 0;
            }
        }
    }
    
    private boolean isBetterArmor(final int slot, final int[] armorType) {
        if (this.getPlayer().inventory.armorInventory[slot] != null) {
            int currentIndex = 0;
            int invIndex = 0;
            int finalCurrentIndex = -1;
            int finalInvIndex = -1;
            int[] array = armorType;
            for (int j = armorType.length, i = 0; i < j; ++i) {
                final int armor = array[i];
                if (Item.getIdFromItem(this.getPlayer().inventory.armorInventory[slot].getItem()) == armor) {
                    finalCurrentIndex = currentIndex;
                    break;
                }
                ++currentIndex;
            }
            array = armorType;
            for (int j = armorType.length, i = 0; i < j; ++i) {
                final int armor = array[i];
                if (this.getItem(armor) != -1) {
                    finalInvIndex = invIndex;
                    break;
                }
                ++invIndex;
            }
            if (finalInvIndex > -1) {
                return finalInvIndex < finalCurrentIndex;
            }
        }
        return false;
    }
    
    private int getItem(final int id) {
        for (int i = 9; i < 45; ++i) {
            if (this.getPlayer().inventoryContainer.getSlot(i).getStack() != null && Item.getIdFromItem(this.getPlayer().inventoryContainer.getSlot(i).getStack().getItem()) == id) {
                return i;
            }
        }
        return -1;
    }
}
