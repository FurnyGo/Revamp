// 
// Decompiled by Procyon v0.5.36
// 

package revamp.modules.impl.world;

import net.minecraft.item.ItemBlock;
import revamp.events.impl.EventUpdate;
import revamp.events.Event;
import revamp.modules.Category;
import revamp.userinterfaces.clickgui.settings.Setting;
import revamp.interfaces.Methods;
import revamp.modules.Module;

public class FastPlace extends Module implements Methods
{
    public Setting blocksOnly;
    
    public FastPlace() {
        super("FastPlace", "FastPlace", "Removes placing delay.", -1, 0, Category.WORLD);
        this.blocksOnly = new Setting("Blocks Only", false, this);
    }
    
    @Override
    public void onEvent(final Event event) {
        if (!this.isToggled()) {
            return;
        }
        if (event instanceof EventUpdate) {
            if (this.blocksOnly.isToggled() && (this.getPlayer().getHeldItem() == null || this.getPlayer().getHeldItem().getItem() == null || !(this.getPlayer().getHeldItem().getItem() instanceof ItemBlock))) {
                return;
            }
            this.setRightClickDelay(0);
        }
    }
}
