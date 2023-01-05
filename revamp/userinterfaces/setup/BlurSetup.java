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

public class BlurSetup extends GuiScreen
{
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.mc.getTextureManager().bindTexture(new ResourceLocation("revamp/background.jpg"));
        Gui.drawModalRectWithCustomSizedTexture(0, 0, 0.0f, 0.0f, this.width, this.height, (float)this.width, (float)this.height);
        GL11.glPushMatrix();
        GL11.glScalef(2.5f, 2.5f, 2.5f);
        this.drawCenteredString(this.fontRendererObj, "Revamp " + Revamp.getVERSION(), (float)(this.width / 5), (float)(this.height / 3), Rainbow.rainbow(4.0f, 0.7f, 1.0f, 200L));
        GL11.glPopMatrix();
        this.drawCenteredString(this.fontRendererObj, "Would you like blur on ClickGUI?", (float)(this.width / 2), (float)(this.height / 5), -1);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    @Override
    public void initGui() {
        final ScaledResolution sr = new ScaledResolution(this.mc);
        this.buttonList.add(new GuiButton(0, this.width / 2 - 115, this.height / 3 * 2, 98, 20, "Yes"));
        this.buttonList.add(new GuiButton(1, this.width / 2 + 15, this.height / 3 * 2, 98, 20, "No"));
        super.initGui();
    }
    
    @Override
    protected void actionPerformed(final GuiButton button) {
        switch (button.id) {
            case 0: {
                Revamp.settingManager.getSetting(Revamp.moduleManager.getModuleByName("ClickGUI"), "Blur").setToggled(true);
                break;
            }
            case 1: {
                Revamp.settingManager.getSetting(Revamp.moduleManager.getModuleByName("ClickGUI"), "Blur").setToggled(false);
                break;
            }
        }
        this.mc.displayGuiScreen(new HUDSetup());
    }
}
