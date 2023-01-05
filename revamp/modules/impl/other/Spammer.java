// 
// Decompiled by Procyon v0.5.36
// 

package revamp.modules.impl.other;

import revamp.utilities.impl.misc.Randomizer;
import revamp.events.impl.EventUpdate;
import revamp.events.Event;
import revamp.modules.Category;
import revamp.userinterfaces.clickgui.settings.Setting;
import revamp.interfaces.Methods;
import revamp.modules.Module;

public class Spammer extends Module implements Methods
{
    public Setting ticks;
    public Setting nospam;
    public Setting message;
    
    public Spammer() {
        super("Spammer", "Spammer", "Spams at a delay with a chosen message.", -1, 0, Category.OTHER);
        this.ticks = new Setting("Ticks", 20.0f, 1.0f, 50.0f, true, this);
        this.nospam = new Setting("No Anti-Spam", true, this);
        this.message = new Setting("Message", "Get Good, Get RVMP", this);
    }
    
    @Override
    public void onEvent(final Event event) {
        if (!this.isToggled()) {
            return;
        }
        if (event instanceof EventUpdate) {
            this.setNameWithOutSpace("Spammer" + (this.information() ? (" §7" + this.ticks.getCurrentValue()) : ""));
            final String abc = String.valueOf(Randomizer.randomInt(1, 8)) + Randomizer.randomABC() + Randomizer.randomInt(1, 9) + Randomizer.randomABC() + Randomizer.randomInt(2, 9) + Randomizer.randomABC() + Randomizer.randomInt(2, 8) + Randomizer.randomABC();
            if (this.getPlayer().ticksExisted % this.ticks.getCurrentValue() == 0.0f) {
                this.getPlayer().sendChatMessage(String.valueOf(this.message.getTyped()) + (this.nospam.isToggled() ? (" " + abc) : ""));
            }
        }
    }
}
