// 
// Decompiled by Procyon v0.5.36
// 

package revamp.modules.impl.movement;

import net.minecraft.client.entity.EntityPlayerSP;
import revamp.events.impl.EventUpdate;
import revamp.events.Event;
import revamp.modules.Category;
import revamp.userinterfaces.clickgui.settings.Setting;
import revamp.interfaces.Methods;
import revamp.modules.Module;

public class FastLadder extends Module implements Methods
{
    public Setting mode;
    public Setting speed;
    
    public FastLadder() {
        super("FastLadder", "FastLadder", "Speeds you up on ladders.", -1, 0, Category.MOVEMENT);
        this.mode = new Setting("Mode", new String[] { "Vanilla", "AAC 3.3" }, "Vanilla", this);
        this.speed = new Setting("Speed", 0.1f, 0.0f, 1.0f, false, this);
    }
    
    @Override
    public void onEvent(final Event event) {
        if (!this.isToggled()) {
            return;
        }
        if (event instanceof EventUpdate) {
            this.setNameWithOutSpace("FastLadder" + (this.information() ? (" §7" + this.mode.getCurrentMode()) : ""));
            if (!this.isMoving()) {
                return;
            }
            if (this.mode.getCurrentMode().equalsIgnoreCase("Vanilla") && this.isOnLadder()) {
                final EntityPlayerSP player = this.getPlayer();
                player.motionY += this.speed.getCurrentValue();
            }
            if (this.mode.getCurrentMode().equalsIgnoreCase("AAC 3.3") && this.isOnLadder()) {
                this.getPlayer().motionY = 0.17;
            }
        }
    }
}
