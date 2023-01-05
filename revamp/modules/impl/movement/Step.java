// 
// Decompiled by Procyon v0.5.36
// 

package revamp.modules.impl.movement;

import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import revamp.events.impl.EventUpdate;
import revamp.events.Event;
import revamp.modules.Category;
import revamp.userinterfaces.clickgui.settings.Setting;
import revamp.interfaces.Methods;
import revamp.modules.Module;

public class Step extends Module implements Methods
{
    public Setting mode;
    public Setting height;
    
    public Step() {
        super("Step", "Step", "Allows you to step up blocks.", -1, 0, Category.MOVEMENT);
        this.mode = new Setting("Mode", new String[] { "Vanilla", "NCP", "Legit" }, "Vanilla", this);
        this.height = new Setting("Height", 2.0f, 1.0f, 10.0f, false, this);
    }
    
    @Override
    public void onEvent(final Event event) {
        if (!this.isToggled()) {
            return;
        }
        if (event instanceof EventUpdate) {
            this.setNameWithOutSpace("Step" + (this.information() ? (" §7" + this.mode.getCurrentMode()) : ""));
            if (this.mode.getCurrentMode().equalsIgnoreCase("Vanilla")) {
                this.getPlayer().stepHeight = this.height.getCurrentValue();
            }
            if (this.mode.getCurrentMode().equalsIgnoreCase("NCP")) {
                this.getPlayer().stepHeight = 0.6f;
                if (this.isCollided() && this.onGround()) {
                    this.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(this.getX(), this.getY() + 0.419999284, this.getZ(), this.onGround()));
                    this.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(this.getX(), this.getY() + 0.752999372, this.getZ(), this.onGround()));
                    this.getPlayer().setPosition(this.getX(), this.getY() + 0.42, this.getZ());
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(100L);
                            }
                            catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
            }
            if (this.mode.getCurrentMode().equalsIgnoreCase("Legit") && this.isCollided() && this.onGround()) {
                this.jump();
            }
        }
    }
    
    @Override
    public void onDisable() {
        this.getPlayer().stepHeight = 0.6f;
    }
}
