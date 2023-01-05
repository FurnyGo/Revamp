// 
// Decompiled by Procyon v0.5.36
// 

package revamp.userinterfaces.mainmenu;

import revamp.userinterfaces.misc.GuiAltLogin;
import revamp.userinterfaces.setup.GuiSetup;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiSelectWorld;
import java.util.Iterator;
import revamp.utilities.impl.render.Rainbow;
import java.awt.Color;
import org.lwjgl.opengl.GL11;
import revamp.Revamp;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import java.util.Collection;
import java.util.Arrays;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import java.util.ArrayList;
import revamp.utilities.impl.misc.Randomizer;
import java.util.List;
import net.minecraft.client.gui.GuiScreen;

public class CustomMainMenu extends GuiScreen
{
    private int i;
    private List<String> changelog;
    
    public CustomMainMenu() {
        this.i = Randomizer.randomInt(1, 9);
        this.changelog = new ArrayList<String>();
    }
    
    @Override
    public void initGui() {
        final ScaledResolution sr = new ScaledResolution(this.mc);
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, (int)(this.height / 2 * 0.01), 98, 20, "Singleplayer"));
        this.buttonList.add(new GuiButton(1, this.width / 2, (int)(this.height / 2 * 0.01), 98, 20, "Multiplayer"));
        this.buttonList.add(new GuiButton(2, this.width - 60, (int)(this.height / 2 * 0.01), 55, 20, "Settings"));
        this.buttonList.add(new GuiButton(3, 5, (int)(this.height / 2 * 0.01), 55, 20, "Exit Game"));
        this.buttonList.add(new GuiButton(4, this.width / 2 - 55, this.height - 25, 50, 20, "Setup"));
        this.buttonList.add(new GuiButton(5, this.width / 2, this.height - 25, 50, 20, "Alt Login"));
        this.changelog.clear();
        this.changelog.addAll(Arrays.asList("§3Revamp §bv0.31", "§3-§b-§3-§b-§3-§b-§3-§b-§3-§3-§b-§3-§b-§3-§b-§3-§b-§3-", "§a+ Flight Mineberry Survival", "§a+ Random Background", "§a+ Server Setup", "§a+ Friends", "§a+ Criticals", "§a+ Step Legit", "§a+ AutoArmor", "§a+ InventoryManager", "§a+ NoFall Spoof", "§a+ Stealer Name Check", "§a+ AutoTool", "§a+ AutoClicker NCD", "§6* HUD", "§6* AntiBot", "§6* Cracked Gen", "§c- AirJump", "§c- MotionBlur"));
        super.initGui();
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.mc.getTextureManager().bindTexture(new ResourceLocation("revamp/bgs/background" + this.i + ".jpg"));
        Gui.drawModalRectWithCustomSizedTexture(0, 0, 0.0f, 0.0f, this.width, this.height, (float)this.width, (float)this.height);
        Gui.drawRect(0.0, 24.0, this.width, 0.0, 1342177280);
        Gui.drawRect(0.0, 26.0, this.width, 0.0, 1073741824);
        this.drawString(this.fontRendererObj, "Welcome, " + Revamp.getUSERNAME(), 2, this.height - 20, -1);
        this.drawString(this.fontRendererObj, String.valueOf(Revamp.getNAME()) + " " + Revamp.getVERSION(), 2, this.height - 10, -1);
        this.drawString(this.fontRendererObj, "Created by " + Revamp.getAUTHOR(), this.width - this.fontRendererObj.getStringWidth("Created by " + Revamp.getAUTHOR()) - 2, this.height - 10, -1);
        float offset = 0.0f;
        for (int i = 0; i < Revamp.getNAME().length(); ++i) {
            final String character = String.valueOf(Revamp.getNAME().charAt(i));
            GL11.glPushMatrix();
            GL11.glTranslatef(this.width / 2.0f, this.height / 2.0f, 0.0f);
            GL11.glScalef(3.0f, 3.0f, 1.0f);
            GL11.glTranslatef(-(this.width / 2.0f), -(this.height / 2.0f), 0.0f);
            this.drawCenteredString(this.fontRendererObj, character, this.width / 2.0f + offset - 15.0f, this.height / 2.0f - this.mc.fontRendererObj.FONT_HEIGHT / 2.0f + 5.0f, Rainbow.breath(offset, (Revamp.getClientCOLOR() == Color.BLACK) ? new Color(50, 50, 50) : Revamp.getClientCOLOR()));
            GL11.glPopMatrix();
            offset += (float)(this.fontRendererObj.getStringWidth(character) + 0.04);
        }
        int y = 30;
        if (!this.changelog.isEmpty()) {
            for (final String text : this.changelog) {
                this.drawString(this.fontRendererObj, text, 5, y, -1);
                y += this.fontRendererObj.FONT_HEIGHT;
            }
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    @Override
    protected void actionPerformed(final GuiButton button) {
        switch (button.id) {
            case 0: {
                this.mc.displayGuiScreen(new GuiSelectWorld(this));
                break;
            }
            case 1: {
                this.mc.displayGuiScreen(new GuiMultiplayer(this));
                break;
            }
            case 2: {
                this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
                break;
            }
            case 3: {
                this.mc.shutdown();
                break;
            }
            case 4: {
                this.mc.displayGuiScreen(new GuiSetup());
                break;
            }
            case 5: {
                this.mc.displayGuiScreen(new GuiAltLogin());
                break;
            }
        }
    }
}
