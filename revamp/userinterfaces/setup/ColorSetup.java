// 
// Decompiled by Procyon v0.5.36
// 

package revamp.userinterfaces.setup;

import java.awt.Color;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import revamp.utilities.impl.render.Rainbow;
import revamp.Revamp;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.gui.GuiScreen;

public class ColorSetup extends GuiScreen
{
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.mc.getTextureManager().bindTexture(new ResourceLocation("revamp/background.jpg"));
        Gui.drawModalRectWithCustomSizedTexture(0, 0, 0.0f, 0.0f, this.width, this.height, (float)this.width, (float)this.height);
        GL11.glPushMatrix();
        GL11.glScalef(2.5f, 2.5f, 2.5f);
        this.drawCenteredString(this.fontRendererObj, "Revamp " + Revamp.getVERSION(), (float)(this.width / 5), (float)(this.height / 3), Rainbow.rainbow(4.0f, 0.7f, 1.0f, 200L));
        GL11.glPopMatrix();
        this.drawCenteredString(this.fontRendererObj, "Please choose your desired color:", (float)(this.width / 2), (float)(this.height / 5), -1);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    @Override
    public void initGui() {
        final ScaledResolution sr = new ScaledResolution(this.mc);
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 3 * 2, "Cyan (Default)"));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 115, (int)(this.height / 3 * 0.8), 90, 20, "Red"));
        this.buttonList.add(new GuiButton(2, this.width / 2 - 115, (int)(this.height / 3 * 1.1), 90, 20, "Yellow"));
        this.buttonList.add(new GuiButton(3, this.width / 2 - 115, (int)(this.height / 3 * 1.4), 90, 20, "Blue"));
        this.buttonList.add(new GuiButton(4, this.width / 2 - 115, (int)(this.height / 3 * 1.7), 90, 20, "Black"));
        this.buttonList.add(new GuiButton(5, this.width / 2 + 25, (int)(this.height / 3 * 0.8), 90, 20, "Orange"));
        this.buttonList.add(new GuiButton(6, this.width / 2 + 25, (int)(this.height / 3 * 1.1), 90, 20, "Green"));
        this.buttonList.add(new GuiButton(7, this.width / 2 + 25, (int)(this.height / 3 * 1.4), 90, 20, "Magenta"));
        this.buttonList.add(new GuiButton(8, this.width / 2 + 25, (int)(this.height / 3 * 1.7), 90, 20, "White"));
        super.initGui();
    }
    
    @Override
    protected void actionPerformed(final GuiButton button) {
        switch (button.id) {
            case 0: {
                Revamp.setClientCOLOR(Color.CYAN);
                break;
            }
            case 1: {
                Revamp.setClientCOLOR(Color.RED);
                break;
            }
            case 2: {
                Revamp.setClientCOLOR(Color.YELLOW);
                break;
            }
            case 3: {
                Revamp.setClientCOLOR(Color.BLUE);
                break;
            }
            case 4: {
                Revamp.setClientCOLOR(Color.BLACK);
                break;
            }
            case 5: {
                Revamp.setClientCOLOR(Color.ORANGE);
                break;
            }
            case 6: {
                Revamp.setClientCOLOR(Color.GREEN);
                break;
            }
            case 7: {
                Revamp.setClientCOLOR(Color.MAGENTA);
                break;
            }
            case 8: {
                Revamp.setClientCOLOR(Color.WHITE);
                break;
            }
        }
        this.mc.displayGuiScreen(new BlurSetup());
    }
}
