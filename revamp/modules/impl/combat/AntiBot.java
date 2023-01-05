// 
// Decompiled by Procyon v0.5.36
// 

package revamp.modules.impl.combat;

import java.util.Iterator;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.monster.EntityMob;
import revamp.Revamp;
import net.minecraft.entity.Entity;
import revamp.events.impl.EventUpdate;
import revamp.events.Event;
import revamp.modules.Category;
import revamp.userinterfaces.clickgui.settings.Setting;
import revamp.interfaces.Methods;
import revamp.modules.Module;

public class AntiBot extends Module implements Methods
{
    public Setting mode;
    
    public AntiBot() {
        super("AntiBot", "AntiBot", "Removes bots.", -1, 0, Category.COMBAT);
        this.mode = new Setting("Mode", new String[] { "Hypixel 1", "Hypixel 2" }, "Hypixel 1", this);
    }
    
    @Override
    public void onEvent(final Event event) {
        if (!this.isToggled()) {
            return;
        }
        if (event instanceof EventUpdate) {
            this.setNameWithOutSpace("AntiBot" + (this.information() ? (" §7" + this.mode.getCurrentMode()) : ""));
            for (final Object object : this.getWorld().loadedEntityList) {
                final Entity entity = (Entity)object;
                if (this.mode.getCurrentMode().equalsIgnoreCase("Hypixel 1")) {
                    if (!entity.isInvisible() || entity == this.getPlayer()) {
                        continue;
                    }
                    this.getWorld().removeEntity(entity);
                }
                else {
                    if (!this.mode.getCurrentMode().equalsIgnoreCase("Hypixel 2") || Revamp.getOnlinePlayers().contains(entity.getName()) || entity.ticksExisted < 20 || entity == this.getPlayer() || entity instanceof EntityMob) {
                        continue;
                    }
                    if (entity instanceof EntityAnimal) {
                        continue;
                    }
                    this.getWorld().removeEntity(entity);
                }
            }
        }
    }
}
