// 
// Decompiled by Procyon v0.5.36
// 

package revamp.modules.impl.render;

import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.Potion;
import revamp.events.impl.EventUpdate;
import revamp.events.Event;
import revamp.modules.Category;
import revamp.userinterfaces.clickgui.settings.Setting;
import revamp.interfaces.Methods;
import revamp.modules.Module;

public class Fullbright extends Module implements Methods
{
    public Setting mode;
    
    public Fullbright() {
        super("Fullbright", "Fullbright", "Brightens the world.", -1, 0, Category.RENDER);
        this.mode = new Setting("Mode", new String[] { "Potion", "Gamma" }, "Potion", this);
    }
    
    @Override
    public void onEvent(final Event event) {
        if (!this.isToggled()) {
            return;
        }
        if (event instanceof EventUpdate) {
            if (this.mode.getCurrentMode().equalsIgnoreCase("Potion")) {
                this.getPlayer().addPotionEffect(new PotionEffect(Potion.nightVision.getId(), 5210, 3));
                this.getGameSettings().gammaSetting = 1.0f;
            }
            else if (this.mode.getCurrentMode().equalsIgnoreCase("Gamma")) {
                this.getGameSettings().gammaSetting = 10.0f;
                this.getPlayer().removePotionEffect(Potion.nightVision.getId());
            }
        }
    }
    
    @Override
    public void onDisable() {
        this.getPlayer().removePotionEffect(Potion.nightVision.getId());
        this.getGameSettings().gammaSetting = 1.0f;
    }
}
