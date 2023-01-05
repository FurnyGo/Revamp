// 
// Decompiled by Procyon v0.5.36
// 

package revamp.modules.impl.combat;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.Minecraft;
import net.minecraft.util.MovingObjectPosition;
import org.lwjgl.input.Mouse;
import revamp.events.impl.EventUpdate;
import revamp.events.Event;
import io.netty.util.internal.ThreadLocalRandom;
import revamp.modules.Category;
import revamp.userinterfaces.clickgui.settings.Setting;
import revamp.interfaces.Methods;
import revamp.modules.Module;

public class AutoClicker extends Module implements Methods
{
    private long lastClick;
    private long hold;
    private double speed;
    private double holdLength;
    public Setting mincps;
    public Setting maxcps;
    public Setting breakblocks;
    public Setting ncd;
    
    public AutoClicker() {
        super("AutoClicker", "AutoClicker", "Automatically clicks for you.", -1, 0, Category.COMBAT);
        this.mincps = new Setting("Minimum CPS", 8.0f, 1.0f, 20.0f, false, this);
        this.maxcps = new Setting("Maximum CPS", 12.0f, 1.0f, 20.0f, false, this);
        this.breakblocks = new Setting("Break Blocks", true, this);
        this.ncd = new Setting("No Click Delay", true, this);
    }
    
    @Override
    public void onEnable() {
        this.speed = 1.0 / ThreadLocalRandom.current().nextDouble(this.mincps.getCurrentValue() - 0.2, this.maxcps.getCurrentValue());
        this.holdLength = this.speed / ThreadLocalRandom.current().nextDouble(this.mincps.getCurrentValue(), this.maxcps.getCurrentValue());
    }
    
    @Override
    public void onEvent(final Event event) {
        if (!this.isToggled()) {
            return;
        }
        if (event instanceof EventUpdate) {
            this.setNameWithOutSpace("AutoClicker" + (this.information() ? (" §7" + this.mincps.getCurrentValue() + " - " + this.maxcps.getCurrentValue()) : ""));
            if (this.mincps.getCurrentValue() >= this.maxcps.getCurrentValue()) {
                this.maxcps.currentValue = this.mincps.getCurrentValue() + 0.1f;
            }
            if (Mouse.isButtonDown(0)) {
                if (this.getObjectMouseOver().typeOfHit != null && this.breakblocks.isToggled() && this.getObjectMouseOver().typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                    return;
                }
                if (this.ncd.isToggled()) {
                    Minecraft.getMinecraft().leftClickCounter = 0;
                }
                if (System.currentTimeMillis() - this.lastClick > this.speed * 1000.0) {
                    this.lastClick = System.currentTimeMillis();
                    if (this.hold < this.lastClick) {
                        this.hold = this.lastClick;
                    }
                    KeyBinding.setKeyBindState(this.getGameSettings().keyBindAttack.getKeyCode(), true);
                    KeyBinding.onTick(this.getGameSettings().keyBindAttack.getKeyCode());
                    this.speed = 1.0 / ThreadLocalRandom.current().nextDouble(this.mincps.getCurrentValue() - 0.2, this.maxcps.getCurrentValue());
                    this.holdLength = this.speed / ThreadLocalRandom.current().nextDouble(this.mincps.getCurrentValue(), this.maxcps.getCurrentValue());
                }
                else if (System.currentTimeMillis() - this.hold > this.holdLength * 1000.0) {
                    KeyBinding.setKeyBindState(this.getGameSettings().keyBindAttack.getKeyCode(), false);
                    this.speed = 1.0 / ThreadLocalRandom.current().nextDouble(this.mincps.getCurrentValue() - 0.2, this.maxcps.getCurrentValue());
                    this.holdLength = this.speed / ThreadLocalRandom.current().nextDouble(this.mincps.getCurrentValue(), this.maxcps.getCurrentValue());
                }
            }
        }
    }
}
