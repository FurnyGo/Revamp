// 
// Decompiled by Procyon v0.5.36
// 

package revamp.modules.impl.movement;

import net.minecraft.potion.Potion;
import revamp.events.impl.EventUpdate;
import revamp.events.Event;
import revamp.modules.Category;
import revamp.userinterfaces.clickgui.settings.Setting;
import revamp.interfaces.Methods;
import revamp.modules.Module;

public class Speed extends Module implements Methods
{
    public Setting mode;
    public Setting speed;
    
    public Speed() {
        super("Speed", "Speed", "Allows you to move faster.", -1, 0, Category.MOVEMENT);
        this.mode = new Setting("Mode", new String[] { "Vanilla", "B-Hop" }, "Vanilla", this);
        this.speed = new Setting("Movement Speed", 2.5f, 1.0f, 5.0f, false, this);
    }
    
    @Override
    public void onEvent(final Event event) {
        if (!this.isToggled()) {
            return;
        }
        if (event instanceof EventUpdate) {
            this.setNameWithOutSpace("Speed" + (this.information() ? (" §7" + this.mode.getCurrentMode()) : ""));
            if (this.mode.getCurrentMode().equalsIgnoreCase("Vanilla") && this.getPlayer().moveForward > 0.0f) {
                this.getPlayer().setSpeed(this.speed.getCurrentValue() / 4.0f);
            }
            if (this.mode.getCurrentMode().equalsIgnoreCase("B-Hop") && this.isMoving()) {
                this.getPlayer().setSpeed(this.speed.getCurrentValue() / 4.0f);
                if (this.onGround()) {
                    this.jump();
                }
                this.getPlayer().setSprinting(true);
            }
        }
    }
    
    private double getSpeed() {
        double baseSpeed = 0.2873;
        if (this.getPlayer().isPotionActive(Potion.moveSpeed)) {
            baseSpeed *= 1.0 + 0.2 * (this.getPlayer().getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1);
        }
        return baseSpeed;
    }
}
