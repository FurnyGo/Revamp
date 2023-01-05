// 
// Decompiled by Procyon v0.5.36
// 

package revamp.modules.impl.movement;

import net.minecraft.client.entity.EntityPlayerSP;
import revamp.events.impl.EventUpdate;
import revamp.events.Event;
import revamp.modules.ModuleManager;
import revamp.Revamp;
import revamp.modules.Category;
import revamp.userinterfaces.clickgui.settings.Setting;
import revamp.interfaces.Methods;
import revamp.modules.Module;

public class Flight extends Module implements Methods
{
    public Setting mode;
    public Setting horizontal;
    public Setting vertical;
    public Setting speed;
    public Setting timer;
    
    public Flight() {
        super("Flight", "Flight", "Allows you to fly.", -1, 33, Category.MOVEMENT);
        this.mode = new Setting("Mode", new String[] { "Vanilla", "Creative", "AAC 3.3 Glide", "Mineberry Survival" }, "Vanilla", this);
        this.horizontal = new Setting("Horizontal", 2.5f, 0.0f, 5.0f, false, this);
        this.vertical = new Setting("Vertical", 2.5f, 0.0f, 5.0f, false, this);
        this.speed = new Setting("AAC Speed", 0.1f, 0.0f, 1.0f, false, this);
        this.timer = new Setting("AAC Timer", 0.2f, 0.1f, 1.0f, false, this);
    }
    
    @Override
    public void onEnable() {
        if (this.mode.getCurrentMode().equalsIgnoreCase("Mineberry Survival") && !this.onGround()) {
            final ModuleManager moduleManager = Revamp.moduleManager;
            ModuleManager.addChatMessage(true, "You must be on ground in order for this to work.");
            this.toggle();
        }
        else if (this.mode.getCurrentMode().equalsIgnoreCase("Mineberry Survival") && this.onGround() && this.getGameSettings().keyBindSneak.isKeyDown()) {
            final ModuleManager moduleManager2 = Revamp.moduleManager;
            ModuleManager.addChatMessage(true, "You must be not sneaking in order for this to work.");
            this.toggle();
        }
        else if (this.mode.getCurrentMode().equalsIgnoreCase("Mineberry Survival") && this.onGround() && !this.getGameSettings().keyBindSneak.isKeyDown() && this.isInLiquid()) {
            final ModuleManager moduleManager3 = Revamp.moduleManager;
            ModuleManager.addChatMessage(true, "You must not be in liquid in order for this to work.");
            this.toggle();
        }
        else if (this.mode.getCurrentMode().equalsIgnoreCase("Mineberry Survival") && this.onGround() && !this.getGameSettings().keyBindSneak.isKeyDown() && !this.isInLiquid()) {
            this.getPlayer().sendChatMessage("/crawl");
        }
    }
    
    @Override
    public void onEvent(final Event event) {
        if (!this.isToggled()) {
            return;
        }
        if (event instanceof EventUpdate) {
            this.setNameWithOutSpace("Flight" + (this.information() ? (" §7" + this.mode.getCurrentMode()) : ""));
            if (this.mode.getCurrentMode().equalsIgnoreCase("Vanilla")) {
                if (this.getPlayer().moveForward != 0.0f || this.getPlayer().moveStrafing != 0.0f) {
                    this.getPlayer().setSpeed(this.horizontal.getCurrentValue() / 2.0f);
                }
                else {
                    final EntityPlayerSP player = this.getPlayer();
                    player.motionX /= 2.0;
                    final EntityPlayerSP player2 = this.getPlayer();
                    player2.motionZ /= 2.0;
                }
                if (this.getGameSettings().keyBindJump.isKeyDown() && !this.getGameSettings().keyBindSneak.isKeyDown()) {
                    this.getPlayer().motionY = this.vertical.getCurrentValue() / 3.0f;
                }
                else if (this.getGameSettings().keyBindSneak.isKeyDown() && !this.getGameSettings().keyBindJump.isKeyDown()) {
                    this.getPlayer().motionY = -this.vertical.getCurrentValue() / 3.0f;
                }
                else {
                    this.getPlayer().motionY = 0.0;
                }
                this.getCapabilities().setFlySpeed(0.05f);
            }
            if (this.mode.getCurrentMode().equalsIgnoreCase("Creative")) {
                if (!this.getPlayer().onGround) {
                    this.getCapabilities().isFlying = true;
                }
                if (this.getGameSettings().keyBindJump.isKeyDown() && !this.getGameSettings().keyBindSneak.isKeyDown()) {
                    this.getPlayer().motionY = this.vertical.getCurrentValue() / 8.0f;
                }
                else if (this.getGameSettings().keyBindSneak.isKeyDown() && !this.getGameSettings().keyBindJump.isKeyDown()) {
                    this.getPlayer().motionY = -this.vertical.getCurrentValue() / 8.0f;
                }
                if (this.getGameSettings().keyBindForward.isKeyDown()) {
                    this.getCapabilities().setFlySpeed(this.horizontal.getCurrentValue() / 8.0f);
                }
            }
            if (this.mode.getCurrentMode().equalsIgnoreCase("AAC 3.3 Glide") && this.getPlayer().fallDistance >= this.speed.getCurrentValue()) {
                this.getPlayer().motionY = 0.0;
                this.getTimer().timerSpeed = this.timer.getCurrentValue();
                this.getPlayer().fallDistance = 0.0f;
                this.getPlayer().setSpeed(this.getPlayer().getSpeed());
            }
            if (this.mode.getCurrentMode().equalsIgnoreCase("Mineberry Survival")) {
                if (this.getPlayer().moveForward != 0.0f || this.getPlayer().moveStrafing != 0.0f) {
                    this.getPlayer().setSpeed(this.horizontal.getCurrentValue() / 2.0f);
                }
                else {
                    final EntityPlayerSP player3 = this.getPlayer();
                    player3.motionX /= 2.0;
                    final EntityPlayerSP player4 = this.getPlayer();
                    player4.motionZ /= 2.0;
                }
                if (this.getGameSettings().keyBindJump.isKeyDown() && !this.getGameSettings().keyBindSneak.isKeyDown()) {
                    this.getGameSettings().keyBindJump.pressed = false;
                    this.getPlayer().setJumping(false);
                    this.getPlayer().motionY = this.vertical.getCurrentValue() / 3.0f;
                }
                else if (this.getGameSettings().keyBindSneak.isKeyDown() && !this.getGameSettings().keyBindJump.isKeyDown()) {
                    this.getGameSettings().keyBindSneak.pressed = false;
                    this.getPlayer().setSneaking(false);
                    this.getPlayer().motionY = -this.vertical.getCurrentValue() / 3.0f;
                }
                else {
                    this.getPlayer().motionY = 0.0;
                }
                this.getCapabilities().setFlySpeed(0.05f);
            }
        }
    }
    
    @Override
    public void onDisable() {
        this.getTimer().timerSpeed = 1.0f;
        this.getCapabilities().setFlySpeed(0.05f);
        if (this.mode.getCurrentMode().equalsIgnoreCase("Creative")) {
            this.getCapabilities().isFlying = false;
        }
        if (this.mode.getCurrentMode().equalsIgnoreCase("Mineberry Survival")) {
            this.getGameSettings().keyBindSneak.pressed = true;
        }
    }
}
