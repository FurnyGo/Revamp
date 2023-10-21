package fiveware.modules.impl.player;

import fiveware.events.Event;
import fiveware.events.impl.EventUpdate;
import fiveware.interfaces.Methods;
import fiveware.modules.Category;
import fiveware.modules.Module;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

public class InventoryMove extends Module implements Methods {
   public InventoryMove() {
      super("Inventory Move", "InventoryMove", "Allows you to move in GUI's.", -1, 0, Category.PLAYER);
   }

   public void onEvent(Event event) {
      if (this.isToggled()) {
         if (event instanceof EventUpdate) {
            if (this.getCurrentScreen() == null || this.getCurrentScreen() instanceof GuiChat) {
               return;
            }

            KeyBinding[] var5;
            int var4 = (var5 = new KeyBinding[]{this.getGameSettings().keyBindRight, this.getGameSettings().keyBindLeft, this.getGameSettings().keyBindBack, this.getGameSettings().keyBindForward, this.getGameSettings().keyBindJump}).length;

            for(int var3 = 0; var3 < var4; ++var3) {
               KeyBinding keyBinding = var5[var3];
               keyBinding.pressed = Keyboard.isKeyDown(keyBinding.getKeyCode());
            }
         }

      }
   }
}
