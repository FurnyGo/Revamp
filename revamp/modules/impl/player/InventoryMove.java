// 
// Decompiled by Procyon v0.5.36
// 

package revamp.modules.impl.player;

import org.lwjgl.input.Keyboard;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.gui.GuiChat;
import revamp.events.impl.EventUpdate;
import revamp.events.Event;
import revamp.modules.Category;
import revamp.interfaces.Methods;
import revamp.modules.Module;

public class InventoryMove extends Module implements Methods
{
    public InventoryMove() {
        super("InventoryMove", "InventoryMove", "Allows you to move in GUI's.", -1, 0, Category.PLAYER);
    }
    
    @Override
    public void onEvent(final Event event) {
        if (!this.isToggled()) {
            return;
        }
        if (event instanceof EventUpdate) {
            if (this.getCurrentScreen() == null || this.getCurrentScreen() instanceof GuiChat) {
                return;
            }
            KeyBinding[] array;
            for (int length = (array = new KeyBinding[] { this.getGameSettings().keyBindRight, this.getGameSettings().keyBindLeft, this.getGameSettings().keyBindBack, this.getGameSettings().keyBindForward, this.getGameSettings().keyBindJump }).length, i = 0; i < length; ++i) {
                final KeyBinding keyBinding = array[i];
                keyBinding.pressed = Keyboard.isKeyDown(keyBinding.getKeyCode());
            }
        }
    }
}
