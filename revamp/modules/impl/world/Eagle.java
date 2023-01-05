// 
// Decompiled by Procyon v0.5.36
// 

package revamp.modules.impl.world;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.BlockPos;
import revamp.events.impl.EventUpdate;
import revamp.events.Event;
import revamp.modules.Category;
import revamp.interfaces.Methods;
import revamp.modules.Module;

public class Eagle extends Module implements Methods
{
    public Eagle() {
        super("Eagle", "Eagle", "Sneaks when bridging.", -1, 0, Category.WORLD);
    }
    
    @Override
    public void onEvent(final Event event) {
        if (!this.isToggled()) {
            return;
        }
        if (event instanceof EventUpdate) {
            if (!this.isIngame()) {
                return;
            }
            final BlockPos position = new BlockPos(this.getX(), this.getY() - 1.0, this.getZ());
            if (this.getCurrentEquippedItem() != null && this.getCurrentEquippedItem().getItem() instanceof ItemBlock) {
                this.getGameSettings().keyBindSneak.pressed = false;
                if (this.getWorld().getBlockState(position).getBlock() == Blocks.air) {
                    this.getGameSettings().keyBindSneak.pressed = true;
                }
            }
        }
    }
    
    @Override
    public void onDisable() {
        this.getGameSettings().keyBindSneak.pressed = false;
    }
}
