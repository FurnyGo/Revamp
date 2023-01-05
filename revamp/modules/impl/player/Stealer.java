// 
// Decompiled by Procyon v0.5.36
// 

package revamp.modules.impl.player;

import java.util.stream.StreamSupport;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import java.util.ArrayList;
import net.minecraft.inventory.ContainerChest;
import revamp.events.impl.EventUpdate;
import revamp.events.Event;
import revamp.modules.Category;
import revamp.utilities.impl.time.Timer;
import revamp.userinterfaces.clickgui.settings.Setting;
import revamp.interfaces.Methods;
import revamp.modules.Module;

public class Stealer extends Module implements Methods
{
    public Setting delay;
    public Setting close;
    public Setting check;
    Timer timer;
    
    public Stealer() {
        super("Stealer", "Stealer", "Steals items in chests.", -1, 0, Category.PLAYER);
        this.delay = new Setting("Delay", 100.0f, 1.0f, 200.0f, false, this);
        this.close = new Setting("Close Chest", true, this);
        this.check = new Setting("Name Check", true, this);
        this.timer = new Timer();
    }
    
    @Override
    public void onEvent(final Event event) {
        if (!this.isToggled()) {
            return;
        }
        if (event instanceof EventUpdate) {
            this.setNameWithOutSpace("Stealer" + (this.information() ? (" §7" + this.delay.getCurrentValue()) : ""));
            if (this.getContainer() != null && this.getContainer() instanceof ContainerChest) {
                final ContainerChest chestContainer = (ContainerChest)this.getContainer();
                if (!chestContainer.getLowerChestInventory().getDisplayName().getUnformattedText().equalsIgnoreCase("Large Chest") && !chestContainer.getLowerChestInventory().getDisplayName().getUnformattedText().equalsIgnoreCase("Chest") && this.check.isToggled()) {
                    return;
                }
                final ArrayList<ItemStack> set = new ArrayList<ItemStack>();
                for (int i = 0; i < chestContainer.getLowerChestInventory().getSizeInventory(); ++i) {
                    if (this.timer.hasReached((long)this.delay.getCurrentValue())) {
                        set.add(chestContainer.inventorySlots.get(i).getStack());
                        if (chestContainer.getLowerChestInventory().getStackInSlot(i) != null) {
                            this.getPlayerController().windowClick(chestContainer.windowId, i, 0, 1, this.getPlayer());
                            this.timer.reset();
                        }
                    }
                }
                if (this.close.isToggled() && set.size() >= chestContainer.getInventory().size() - 36 && StreamSupport.stream(set.spliterator(), true).allMatch(o -> o == null)) {
                    this.getPlayer().closeScreen();
                }
            }
        }
    }
}
