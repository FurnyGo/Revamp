// 
// Decompiled by Procyon v0.5.36
// 

package revamp.modules.impl.render;

import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.Potion;
import revamp.events.impl.EventUpdate;
import revamp.events.Event;
import revamp.modules.Category;
import revamp.interfaces.Methods;
import revamp.modules.Module;

public class Fullbright extends Module implements Methods
{
    public Fullbright() {
        super("Fullbright", "Fullbright", "Brightens the world.", -1, 0, Category.RENDER);
    }
    
    @Override
    public void onEvent(final Event event) {
        if (!this.isToggled()) {
            return;
        }
        if (event instanceof EventUpdate) {
            this.getPlayer().addPotionEffect(new PotionEffect(Potion.nightVision.getId(), 420, 1));
        }
    }
    
    @Override
    public void onDisable() {
        this.getPlayer().removePotionEffect(Potion.nightVision.getId());
    }
}
