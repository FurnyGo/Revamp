package fiveware.modules.impl.player;

import fiveware.events.Event;
import fiveware.events.impl.EventUpdate;
import fiveware.interfaces.Methods;
import fiveware.modules.Category;
import fiveware.modules.Module;
import fiveware.userinterfaces.clickgui.settings.Setting;
import fiveware.utilities.time.Timer;
import java.util.ArrayList;
import java.util.stream.StreamSupport;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class Stealer extends Module implements Methods {
   private Timer timer = new Timer();
   public Setting delay = new Setting("Delay", 100.0F, 1.0F, 200.0F, false, this);
   public Setting close = new Setting("Close Chest", true, this);
   public Setting check = new Setting("Name Check", true, this);

   public Stealer() {
      super("Stealer", "Stealer", "Steals items in chests.", -1, 0, Category.PLAYER);
   }

   public void onEvent(Event event) {
      if (this.isToggled()) {
         if (event instanceof EventUpdate && this.getContainer() != null && this.getContainer() instanceof ContainerChest) {
            ContainerChest chest = (ContainerChest)this.getContainer();
            if (!chest.getLowerChestInventory().getDisplayName().getUnformattedText().equalsIgnoreCase("Large Chest") && !chest.getLowerChestInventory().getDisplayName().getUnformattedText().equalsIgnoreCase("Chest") && this.check.isToggled()) {
               return;
            }

            ArrayList<ItemStack> set = new ArrayList();

            for(int i = 0; i < chest.getLowerChestInventory().getSizeInventory(); ++i) {
               if (this.timer.hasReached((long)this.delay.getCurrentValue())) {
                  set.add(((Slot)chest.inventorySlots.get(i)).getStack());
                  if (chest.getLowerChestInventory().getStackInSlot(i) != null) {
                     this.getPlayerController().windowClick(chest.windowId, i, 0, 1, this.getPlayer());
                     this.timer.reset();
                  }
               }
            }

            if (this.close.isToggled() && set.size() >= chest.getInventory().size() - 36 && StreamSupport.stream(set.spliterator(), true).allMatch((o) -> {
               return o == null;
            })) {
               this.getPlayer().closeScreen();
            }
         }

      }
   }
}
