// 
// Decompiled by Procyon v0.5.36
// 

package revamp.userinterfaces.mainmenu;

import revamp.userinterfaces.setup.GuiSetup;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiSelectWorld;
import revamp.utilities.impl.render.Rainbow;
import java.awt.Color;
import org.lwjgl.opengl.GL11;
import revamp.Revamp;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.GuiScreen;

public class CustomMainMenu extends GuiScreen
{
    @Override
    public void initGui() {
        final ScaledResolution sr = new ScaledResolution(this.mc);
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, (int)(this.height / 2 * 0.01), 98, 20, "Singleplayer"));
        this.buttonList.add(new GuiButton(1, this.width / 2, (int)(this.height / 2 * 0.01), 98, 20, "Multiplayer"));
        this.buttonList.add(new GuiButton(2, this.width - 60, (int)(this.height / 2 * 0.01), 55, 20, "Settings"));
        this.buttonList.add(new GuiButton(3, 5, (int)(this.height / 2 * 0.01), 55, 20, "Exit Game"));
        this.buttonList.add(new GuiButton(4, this.width / 2 - 25, this.height - 25, 40, 20, "Setup"));
        this.buttonList.add(new GuiButton(5, this.width / 2 + 20, this.height - 25, 50, 20, "Alt Login"));
        this.buttonList.add(new GuiButton(6, this.width / 2 - 90, this.height - 25, 60, 20, "Changelog"));
        super.initGui();
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.mc.getTextureManager().bindTexture(new ResourceLocation("revamp/background.jpg"));
        Gui.drawModalRectWithCustomSizedTexture(0, 0, 0.0f, 0.0f, this.width, this.height, (float)this.width, (float)this.height);
        Gui.drawRect(0.0, 24.0, this.width, 0.0, 1342177280);
        Gui.drawRect(0.0, 26.0, this.width, 0.0, 1073741824);
        this.drawString(this.fontRendererObj, "Welcome, " + (Revamp.getUSERNAME().equalsIgnoreCase("RukaMyHomie") ? "[DEV] xlzxq" : Revamp.getUSERNAME()), 2, this.height - 20, -1);
        this.drawString(this.fontRendererObj, String.valueOf(Revamp.getNAME()) + " " + Revamp.getVERSION(), 2, this.height - 10, -1);
        this.drawString(this.fontRendererObj, "Created by " + Revamp.getAUTHOR(), this.width - this.fontRendererObj.getStringWidth("Created by " + Revamp.getAUTHOR()) - 2, this.height - 10, -1);
        float offset = 0.0f;
        for (int i = 0; i < Revamp.getNAME().length(); ++i) {
            final String character = String.valueOf(Revamp.getNAME().charAt(i));
            GL11.glPushMatrix();
            GL11.glTranslatef(this.width / 2.0f, this.height / 2.0f, 0.0f);
            GL11.glScalef(3.0f, 3.0f, 1.0f);
            GL11.glTranslatef(-(this.width / 2.0f), -(this.height / 2.0f), 0.0f);
            this.drawCenteredString(this.fontRendererObj, character, this.width / 2.0f + offset - 15.0f, this.height / 2.0f - this.mc.fontRendererObj.FONT_HEIGHT / 2.0f + 5.0f, Rainbow.breath(1.0, offset, 0.9, 1.2, (Revamp.getClientCOLOR() == Color.BLACK) ? new Color(50, 50, 50) : Revamp.getClientCOLOR()));
            GL11.glPopMatrix();
            offset += (float)(this.fontRendererObj.getStringWidth(character) + 0.04);
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
            case 6: {
                this.mc.displayGuiScreen(new Changelog());
                break;
            }
        }
    }
}
