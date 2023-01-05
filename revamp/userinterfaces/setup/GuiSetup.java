// 
// Decompiled by Procyon v0.5.36
// 

package revamp.userinterfaces.setup;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import revamp.utilities.impl.render.Rainbow;
import revamp.Revamp;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.gui.GuiScreen;

public class GuiSetup extends GuiScreen
{
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.mc.getTextureManager().bindTexture(new ResourceLocation("revamp/background.jpg"));
        Gui.drawModalRectWithCustomSizedTexture(0, 0, 0.0f, 0.0f, this.width, this.height, (float)this.width, (float)this.height);
        GL11.glPushMatrix();
        GL11.glScalef(2.5f, 2.5f, 2.5f);
        this.drawCenteredString(this.fontRendererObj, "Revamp " + Revamp.getVERSION(), (float)(this.width / 5), (float)(this.height / 3), Rainbow.rainbow(4.0f, 0.7f, 1.0f, 200L));
        GL11.glPopMatrix();
        this.drawCenteredString(this.fontRendererObj, "Welcome to Revamp Client!", (float)(this.width / 2), (float)(this.height / 2 - 55), -1);
        this.drawCenteredString(this.fontRendererObj, "In this setup, you get to choose what", (float)(this.width / 2), (float)(this.height / 2 - 35), -1);
        this.drawCenteredString(this.fontRendererObj, "color you like, customize the HUD, if you", (float)(this.width / 2), (float)(this.height / 2 - 25), -1);
        this.drawCenteredString(this.fontRendererObj, "want blurs on GUI's and what server you", (float)(this.width / 2), (float)(this.height / 2 - 15), -1);
        this.drawCenteredString(this.fontRendererObj, "intend to play on with Revamp Client.", (float)(this.width / 2), (float)(this.height / 2 - 5), -1);
        this.drawCenteredString(this.fontRendererObj, "To get started, press the Start button.", (float)(this.width / 2), (float)(this.height / 2 + 15), -1);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    @Override
    public void initGui() {
        final ScaledResolution sr = new ScaledResolution(this.mc);
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 3 * 2, "Start"));
        super.initGui();
    }
    
    @Override
    protected void actionPerformed(final GuiButton button) {
        switch (button.id) {
            case 0: {
                this.mc.displayGuiScreen(new ColorSetup());
                break;
            }
        }
    }
}
