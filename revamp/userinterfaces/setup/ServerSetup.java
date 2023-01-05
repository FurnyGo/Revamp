// 
// Decompiled by Procyon v0.5.36
// 

package revamp.userinterfaces.setup;

import java.util.Iterator;
import revamp.modules.Module;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import revamp.utilities.impl.render.Rainbow;
import revamp.Revamp;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.gui.GuiScreen;

public class ServerSetup extends GuiScreen
{
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.mc.getTextureManager().bindTexture(new ResourceLocation("revamp/background.jpg"));
        Gui.drawModalRectWithCustomSizedTexture(0, 0, 0.0f, 0.0f, this.width, this.height, (float)this.width, (float)this.height);
        GL11.glPushMatrix();
        GL11.glScalef(2.5f, 2.5f, 2.5f);
        this.drawCenteredString(this.fontRendererObj, "Revamp " + Revamp.getVERSION(), (float)(this.width / 5), (float)(this.height / 3), Rainbow.rainbow(4.0f, 0.7f, 1.0f, 200L));
        GL11.glPopMatrix();
        this.drawCenteredString(this.fontRendererObj, "What server are you willing to play on?", (float)(this.width / 2), (float)(this.height / 5), -1);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    @Override
    public void initGui() {
        final ScaledResolution sr = new ScaledResolution(this.mc);
        this.buttonList.add(new GuiButton(0, this.width / 2 - 115, (int)(this.height / 3 * 0.8), 90, 20, "AAC 3.3 Servers"));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 115, (int)(this.height / 3 * 1.1), 90, 20, "Ghost Cheating"));
        this.buttonList.add(new GuiButton(2, this.width / 2 + 25, (int)(this.height / 3 * 0.8), 90, 20, "Minemen Club"));
        this.buttonList.add(new GuiButton(3, this.width / 2 + 25, (int)(this.height / 3 * 1.1), 90, 20, "Hypixel"));
        super.initGui();
    }
    
    @Override
    protected void actionPerformed(final GuiButton button) {
        switch (button.id) {
            case 0: {
                for (final Module m : Revamp.getModules()) {
                    m.setToggled(false);
                    if (m.name.equalsIgnoreCase("Hitboxes") || m.name.equalsIgnoreCase("Velocity") || m.name.equalsIgnoreCase("Reach") || m.name.equalsIgnoreCase("Sprint") || m.name.equalsIgnoreCase("NameProtect") || m.name.equalsIgnoreCase("Scoreboard") || m.name.equalsIgnoreCase("ItemPhysics") || m.name.equalsIgnoreCase("NoHurtCam") || m.name.equalsIgnoreCase("Animations") || m.name.equalsIgnoreCase("TargetHUD") || m.name.equalsIgnoreCase("Fullbright") || m.name.equalsIgnoreCase("NameTags") || m.name.equalsIgnoreCase("Ambience") || m.name.equalsIgnoreCase("HUD") || m.name.equalsIgnoreCase("ESP") || m.name.equalsIgnoreCase("Fucker") || m.name.equalsIgnoreCase("FastPlace") || m.name.equalsIgnoreCase("NoSlowdown") || m.name.equalsIgnoreCase("Invisibles") || m.name.equalsIgnoreCase("Players") || m.name.equalsIgnoreCase("FastLadder") || m.name.equalsIgnoreCase("InventoryMove") || m.name.equalsIgnoreCase("KeepSprint") || m.name.equalsIgnoreCase("Step") || m.name.equalsIgnoreCase("Cape")) {
                        m.setToggled(true);
                    }
                    m.setKey(0);
                    if (m.name.equalsIgnoreCase("Aura") || m.name.equalsIgnoreCase("Speed")) {
                        m.setKey(19);
                    }
                    if (m.name.equalsIgnoreCase("Flight")) {
                        m.setKey(33);
                    }
                    if (m.name.equalsIgnoreCase("Stealer")) {
                        m.setKey(34);
                    }
                    if (m.name.equalsIgnoreCase("Fucker")) {
                        m.setKey(21);
                    }
                    if (m.name.equalsIgnoreCase("Eagle")) {
                        m.setKey(45);
                    }
                    if (m.name.equalsIgnoreCase("ClickGUI")) {
                        m.setKey(54);
                    }
                    if (m.name.equalsIgnoreCase("Velocity")) {
                        Revamp.settingManager.getSetting(m, "Mode").setCurrentMode("AAC 3.3");
                    }
                    if (m.name.equalsIgnoreCase("NoSlowdown")) {
                        Revamp.settingManager.getSetting(m, "Mode").setCurrentMode("AAC");
                    }
                    if (m.name.equalsIgnoreCase("NoFall")) {
                        Revamp.settingManager.getSetting(m, "Mode").setCurrentMode("AAC 3.3");
                    }
                    if (m.name.equalsIgnoreCase("FastLadder")) {
                        Revamp.settingManager.getSetting(m, "Mode").setCurrentMode("AAC 3.3");
                    }
                    if (m.name.equalsIgnoreCase("Hitboxes")) {
                        Revamp.settingManager.getSetting(m, "Hitbox").setCurrentValue(0.1f);
                    }
                    if (m.name.equalsIgnoreCase("Reach")) {
                        Revamp.settingManager.getSetting(m, "Reach").setCurrentValue(3.75f);
                    }
                    if (m.name.equalsIgnoreCase("Flight")) {
                        Revamp.settingManager.getSetting(m, "Mode").setCurrentMode("AAC 3.3 Glide");
                        Revamp.settingManager.getSetting(m, "AAC Speed").setCurrentValue(0.1f);
                        Revamp.settingManager.getSetting(m, "AAC Timer").setCurrentValue(0.3f);
                    }
                    if (m.name.equalsIgnoreCase("Step")) {
                        Revamp.settingManager.getSetting(m, "Mode").setCurrentMode("NCP");
                    }
                    if (m.name.equalsIgnoreCase("Fucker")) {
                        Revamp.settingManager.getSetting(m, "Beds").setToggled(true);
                        Revamp.settingManager.getSetting(m, "Eggs").setToggled(false);
                        Revamp.settingManager.getSetting(m, "Radius").setCurrentValue(3.6f);
                    }
                    if (m.name.equalsIgnoreCase("Aura")) {
                        Revamp.settingManager.getSetting(m, "Crack Size").setCurrentValue(7.0f);
                        Revamp.settingManager.getSetting(m, "Reach").setCurrentValue(4.75f);
                        Revamp.settingManager.getSetting(m, "APS").setCurrentValue(9.0f);
                        Revamp.settingManager.getSetting(m, "AutoBlock").setToggled(true);
                        Revamp.settingManager.getSetting(m, "Block Mode").setCurrentMode("AAC");
                        Revamp.settingManager.getSetting(m, "Rotations").setToggled(false);
                        Revamp.settingManager.getSetting(m, "Through Walls").setToggled(true);
                    }
                    if (m.name.equalsIgnoreCase("Speed")) {
                        Revamp.settingManager.getSetting(m, "Mode").setCurrentMode("B-Hop");
                        Revamp.settingManager.getSetting(m, "Movement Speed").setCurrentValue(1.1f);
                    }
                    if (m.name.equalsIgnoreCase("Stealer")) {
                        Revamp.settingManager.getSetting(m, "Close Chest").setToggled(true);
                        Revamp.settingManager.getSetting(m, "Delay").setCurrentValue(95.5f);
                    }
                }
                break;
            }
            case 1: {
                for (final Module m : Revamp.getModules()) {
                    m.setToggled(false);
                    if (m.name.equalsIgnoreCase("Hitboxes") || m.name.equalsIgnoreCase("Velocity") || m.name.equalsIgnoreCase("Reach") || m.name.equalsIgnoreCase("Sprint") || m.name.equalsIgnoreCase("AutoClicker") || m.name.equalsIgnoreCase("NameProtect") || m.name.equalsIgnoreCase("Fullbright") || m.name.equalsIgnoreCase("NameTags") || m.name.equalsIgnoreCase("Ambience") || m.name.equalsIgnoreCase("HUD") || m.name.equalsIgnoreCase("ESP") || m.name.equalsIgnoreCase("FastPlace") || m.name.equalsIgnoreCase("Cape")) {
                        m.setToggled(true);
                    }
                    m.setKey(0);
                    if (m.name.equalsIgnoreCase("Eagle")) {
                        m.setKey(33);
                    }
                    if (m.name.equalsIgnoreCase("Stealer")) {
                        m.setKey(34);
                    }
                    if (m.name.equalsIgnoreCase("ClickGUI")) {
                        m.setKey(54);
                    }
                    if (m.name.equalsIgnoreCase("FastPlace")) {
                        Revamp.settingManager.getSetting(m, "Blocks Only").setToggled(true);
                    }
                    if (m.name.equalsIgnoreCase("AutoClicker")) {
                        Revamp.settingManager.getSetting(m, "Minimum CPS").setCurrentValue(8.5f);
                        Revamp.settingManager.getSetting(m, "Maximum CPS").setCurrentValue(10.5f);
                        Revamp.settingManager.getSetting(m, "Break Blocks").setToggled(false);
                    }
                    if (m.name.equalsIgnoreCase("Hitboxes")) {
                        Revamp.settingManager.getSetting(m, "Hitbox").setCurrentValue(0.03f);
                    }
                    if (m.name.equalsIgnoreCase("Velocity")) {
                        Revamp.settingManager.getSetting(m, "Mode").setCurrentMode("Custom");
                        Revamp.settingManager.getSetting(m, "Horizontal").setCurrentValue(95.0f);
                        Revamp.settingManager.getSetting(m, "Vertical").setCurrentValue(95.0f);
                    }
                    if (m.name.equalsIgnoreCase("Reach")) {
                        Revamp.settingManager.getSetting(m, "Reach").setCurrentValue(3.2f);
                    }
                    if (m.name.equalsIgnoreCase("Stealer")) {
                        Revamp.settingManager.getSetting(m, "Delay").setCurrentValue(150.0f);
                        Revamp.settingManager.getSetting(m, "Close Chest").setToggled(false);
                    }
                }
                break;
            }
            case 2: {
                for (final Module m : Revamp.getModules()) {
                    m.setToggled(false);
                    if (m.name.equalsIgnoreCase("Hitboxes") || m.name.equalsIgnoreCase("Reach") || m.name.equalsIgnoreCase("Sprint") || m.name.equalsIgnoreCase("Step") || m.name.equalsIgnoreCase("NameProtect") || m.name.equalsIgnoreCase("Scoreboard") || m.name.equalsIgnoreCase("ItemPhysics") || m.name.equalsIgnoreCase("NoHurtCam") || m.name.equalsIgnoreCase("TargetHUD") || m.name.equalsIgnoreCase("AntiBot") || m.name.equalsIgnoreCase("Fullbright") || m.name.equalsIgnoreCase("NameTags") || m.name.equalsIgnoreCase("Ambience") || m.name.equalsIgnoreCase("HUD") || m.name.equalsIgnoreCase("ESP") || m.name.equalsIgnoreCase("FastPlace") || m.name.equalsIgnoreCase("AutoTool") || m.name.equalsIgnoreCase("Velocity") || m.name.equalsIgnoreCase("Players") || m.name.equalsIgnoreCase("Cape") || m.name.equalsIgnoreCase("InventoryMove") || m.name.equalsIgnoreCase("Animations")) {
                        m.setToggled(true);
                    }
                    m.setKey(0);
                    if (m.name.equalsIgnoreCase("Aura")) {
                        m.setKey(19);
                    }
                    if (m.name.equalsIgnoreCase("Eagle")) {
                        m.setKey(33);
                    }
                    if (m.name.equalsIgnoreCase("ClickGUI")) {
                        m.setKey(54);
                    }
                    if (m.name.equalsIgnoreCase("Aura")) {
                        Revamp.settingManager.getSetting(m, "Crack Size").setCurrentValue(4.0f);
                        Revamp.settingManager.getSetting(m, "Reach").setCurrentValue(3.65f);
                        Revamp.settingManager.getSetting(m, "APS").setCurrentValue(10.0f);
                        Revamp.settingManager.getSetting(m, "AutoBlock").setToggled(true);
                        Revamp.settingManager.getSetting(m, "Block Mode").setCurrentMode("Fake");
                        Revamp.settingManager.getSetting(m, "Rotations").setToggled(true);
                        Revamp.settingManager.getSetting(m, "Rotations Mode").setCurrentMode("Server");
                        Revamp.settingManager.getSetting(m, "Through Walls").setToggled(false);
                    }
                    if (m.name.equalsIgnoreCase("Step")) {
                        Revamp.settingManager.getSetting(m, "Mode").setCurrentMode("Legit");
                    }
                    if (m.name.equalsIgnoreCase("Velocity")) {
                        Revamp.settingManager.getSetting(m, "Mode").setCurrentMode("Hypixel");
                    }
                    if (m.name.equalsIgnoreCase("Hitboxes")) {
                        Revamp.settingManager.getSetting(m, "Hitbox").setCurrentValue(0.1f);
                    }
                    if (m.name.equalsIgnoreCase("Reach")) {
                        Revamp.settingManager.getSetting(m, "Reach").setCurrentValue(3.3f);
                    }
                    if (m.name.equalsIgnoreCase("AntiBot")) {
                        Revamp.settingManager.getSetting(m, "Mode").setCurrentMode("Hypixel 2");
                    }
                }
                break;
            }
            case 3: {
                for (final Module m : Revamp.getModules()) {
                    m.setToggled(false);
                    if (m.name.equalsIgnoreCase("Hitboxes") || m.name.equalsIgnoreCase("Reach") || m.name.equalsIgnoreCase("Sprint") || m.name.equalsIgnoreCase("Step") || m.name.equalsIgnoreCase("NameProtect") || m.name.equalsIgnoreCase("Scoreboard") || m.name.equalsIgnoreCase("ItemPhysics") || m.name.equalsIgnoreCase("NoHurtCam") || m.name.equalsIgnoreCase("TargetHUD") || m.name.equalsIgnoreCase("AntiBot") || m.name.equalsIgnoreCase("Fullbright") || m.name.equalsIgnoreCase("NameTags") || m.name.equalsIgnoreCase("Ambience") || m.name.equalsIgnoreCase("HUD") || m.name.equalsIgnoreCase("ESP") || m.name.equalsIgnoreCase("FastPlace") || m.name.equalsIgnoreCase("AutoTool") || m.name.equalsIgnoreCase("Velocity") || m.name.equalsIgnoreCase("Players") || m.name.equalsIgnoreCase("Cape") || m.name.equalsIgnoreCase("Stealer") || m.name.equalsIgnoreCase("KeepSprint") || m.name.equalsIgnoreCase("AutoArmor") || m.name.equalsIgnoreCase("NoFall") || m.name.equalsIgnoreCase("Criticals") || m.name.equalsIgnoreCase("Animations")) {
                        m.setToggled(true);
                    }
                    m.setKey(0);
                    if (m.name.equalsIgnoreCase("Aura")) {
                        m.setKey(19);
                    }
                    if (m.name.equalsIgnoreCase("Fucker")) {
                        m.setKey(21);
                    }
                    if (m.name.equalsIgnoreCase("NoFall")) {
                        m.setKey(45);
                    }
                    if (m.name.equalsIgnoreCase("InvManager")) {
                        m.setKey(34);
                    }
                    if (m.name.equalsIgnoreCase("Eagle")) {
                        m.setKey(33);
                    }
                    if (m.name.equalsIgnoreCase("ClickGUI")) {
                        m.setKey(54);
                    }
                    if (m.name.equalsIgnoreCase("Aura")) {
                        Revamp.settingManager.getSetting(m, "Crack Size").setCurrentValue(5.0f);
                        Revamp.settingManager.getSetting(m, "Reach").setCurrentValue(4.1f);
                        Revamp.settingManager.getSetting(m, "APS").setCurrentValue(10.0f);
                        Revamp.settingManager.getSetting(m, "AutoBlock").setToggled(true);
                        Revamp.settingManager.getSetting(m, "Block Mode").setCurrentMode("Fake");
                        Revamp.settingManager.getSetting(m, "Rotations").setToggled(true);
                        Revamp.settingManager.getSetting(m, "Rotations Mode").setCurrentMode("Server");
                        Revamp.settingManager.getSetting(m, "Through Walls").setToggled(false);
                    }
                    if (m.name.equalsIgnoreCase("Fucker")) {
                        Revamp.settingManager.getSetting(m, "Beds").setToggled(true);
                        Revamp.settingManager.getSetting(m, "Eggs").setToggled(false);
                        Revamp.settingManager.getSetting(m, "Radius").setCurrentValue(4.1f);
                    }
                    if (m.name.equalsIgnoreCase("Stealer")) {
                        Revamp.settingManager.getSetting(m, "Delay").setCurrentValue(90.0f);
                        Revamp.settingManager.getSetting(m, "Close Chest").setToggled(true);
                        Revamp.settingManager.getSetting(m, "Name Check").setToggled(true);
                    }
                    if (m.name.equalsIgnoreCase("Killsults")) {
                        Revamp.settingManager.getSetting(m, "Server Mode").setCurrentMode("Hypixel");
                        Revamp.settingManager.getSetting(m, "Insult Mode").setCurrentMode("Advertisement");
                    }
                    if (m.name.equalsIgnoreCase("AutoArmor")) {
                        Revamp.settingManager.getSetting(m, "Delay").setCurrentValue(2.0f);
                        Revamp.settingManager.getSetting(m, "Open Inv").setToggled(true);
                    }
                    if (m.name.equalsIgnoreCase("FastPlace")) {
                        Revamp.settingManager.getSetting(m, "Blocks Only").setToggled(true);
                    }
                    if (m.name.equalsIgnoreCase("Step")) {
                        Revamp.settingManager.getSetting(m, "Mode").setCurrentMode("Legit");
                    }
                    if (m.name.equalsIgnoreCase("Hitboxes")) {
                        Revamp.settingManager.getSetting(m, "Hitbox").setCurrentValue(0.1f);
                    }
                    if (m.name.equalsIgnoreCase("Reach")) {
                        Revamp.settingManager.getSetting(m, "Reach").setCurrentValue(3.6f);
                    }
                    if (m.name.equalsIgnoreCase("Velocity")) {
                        Revamp.settingManager.getSetting(m, "Mode").setCurrentMode("Hypixel");
                    }
                    if (m.name.equalsIgnoreCase("AntiBot")) {
                        Revamp.settingManager.getSetting(m, "Mode").setCurrentMode("Hypixel 2");
                    }
                    if (m.name.equalsIgnoreCase("Criticals")) {
                        Revamp.settingManager.getSetting(m, "Mode").setCurrentMode("Packet");
                    }
                    if (m.name.equalsIgnoreCase("NoFall")) {
                        Revamp.settingManager.getSetting(m, "Mode").setCurrentMode("Packet");
                    }
                }
                break;
            }
        }
        this.mc.displayGuiScreen(new FinishSetup());
    }
}
