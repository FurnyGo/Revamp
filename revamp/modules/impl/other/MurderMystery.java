// 
// Decompiled by Procyon v0.5.36
// 

package revamp.modules.impl.other;

import java.util.Iterator;
import net.minecraft.item.ItemBow;
import revamp.modules.ModuleManager;
import revamp.Revamp;
import net.minecraft.item.ItemSword;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.Entity;
import revamp.events.impl.EventUpdate;
import revamp.events.Event;
import revamp.modules.Category;
import revamp.userinterfaces.clickgui.settings.Setting;
import revamp.interfaces.Methods;
import revamp.modules.Module;

public class MurderMystery extends Module implements Methods
{
    public Setting murderer;
    public Setting detective;
    
    public MurderMystery() {
        super("MurderMystery", "MurderMystery", "Exploits murder mystery.", -1, 0, Category.OTHER);
        this.murderer = new Setting("Murderer", true, this);
        this.detective = new Setting("Detective", false, this);
    }
    
    @Override
    public void onEvent(final Event event) {
        if (!this.isToggled()) {
            return;
        }
        if (event instanceof EventUpdate) {
            for (final Entity entity : this.getWorld().loadedEntityList) {
                if (entity instanceof EntityPlayer) {
                    final EntityPlayer player = (EntityPlayer)entity;
                    if (player == this.getPlayer() || player.getCurrentEquippedItem() == null) {
                        continue;
                    }
                    if (player.getCurrentEquippedItem().getItem() instanceof ItemSword && this.murderer.isToggled()) {
                        final ModuleManager moduleManager = Revamp.moduleManager;
                        ModuleManager.addChatMessage(true, "§cI found the §4murderer§c! §f| §4§l" + player.getName());
                    }
                    if (!(player.getCurrentEquippedItem().getItem() instanceof ItemBow) || !this.detective.isToggled()) {
                        continue;
                    }
                    final ModuleManager moduleManager2 = Revamp.moduleManager;
                    ModuleManager.addChatMessage(true, "§aI found the §2detective§a! §f| §2§l" + player.getName());
                }
            }
        }
    }
}
