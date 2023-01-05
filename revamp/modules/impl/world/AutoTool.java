// 
// Decompiled by Procyon v0.5.36
// 

package revamp.modules.impl.world;

import net.minecraft.item.ItemStack;
import org.lwjgl.input.Mouse;
import revamp.events.impl.EventUpdate;
import revamp.events.Event;
import revamp.modules.Category;
import revamp.interfaces.Methods;
import revamp.modules.Module;

public class AutoTool extends Module implements Methods
{
    public AutoTool() {
        super("AutoTool", "AutoTool", "Switches to a compatible tool for you.", -1, 0, Category.WORLD);
    }
    
    @Override
    public void onEvent(final Event event) {
        if (!this.isToggled()) {
            return;
        }
        if (event instanceof EventUpdate) {
            boolean breaking = false;
            int prevSlot = -1;
            int bestSlot = -1;
            float bestSpeed = 1.0f;
            if (this.getCurrentScreen() == null && this.isIngame() && this.getObjectMouseOver() != null && this.getObjectMouseOver().getBlockPos() != null && this.getObjectMouseOver().entityHit == null && Mouse.isButtonDown(0)) {
                for (int k = 0; k < 9; ++k) {
                    final ItemStack item = this.getPlayer().inventory.getStackInSlot(k);
                    if (item != null) {
                        final float speed = item.getStrVsBlock(this.getWorld().getBlockState(this.getObjectMouseOver().getBlockPos()).getBlock());
                        if (speed > bestSpeed) {
                            bestSpeed = speed;
                            bestSlot = k;
                        }
                    }
                }
                if (bestSlot != -1 && this.getPlayer().inventory.currentItem != bestSlot) {
                    this.getPlayer().inventory.currentItem = bestSlot;
                    breaking = true;
                }
                else if (bestSlot == -1) {
                    if (breaking) {
                        this.getPlayer().inventory.currentItem = prevSlot;
                        breaking = false;
                    }
                    prevSlot = this.getPlayer().inventory.currentItem;
                }
            }
        }
    }
}
