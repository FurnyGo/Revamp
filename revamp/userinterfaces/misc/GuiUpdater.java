// 
// Decompiled by Procyon v0.5.36
// 

package revamp.userinterfaces.misc;

import org.lwjgl.opengl.GL11;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import revamp.updater.Updater;
import net.minecraft.client.gui.GuiScreen;

public class GuiUpdater extends GuiScreen
{
    @Override
    public void initGui() {
        Updater.update();
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.mc.getTextureManager().bindTexture(new ResourceLocation("revamp/background.jpg"));
        Gui.drawModalRectWithCustomSizedTexture(0, 0, 0.0f, 0.0f, this.width, this.height, (float)this.width, (float)this.height);
        GL11.glPushMatrix();
        GL11.glTranslatef(this.width / 2.0f, this.height / 2.0f, 0.0f);
        GL11.glScalef(3.0f, 3.0f, 1.0f);
        GL11.glTranslatef(-(this.width / 2.0f), -(this.height / 2.0f), 0.0f);
        this.drawCenteredString(this.fontRendererObj, "Updating | " + Updater.percentage + "%", this.width / 2.0f + 5.0f, this.height / 2.0f - this.mc.fontRendererObj.FONT_HEIGHT / 2.0f + 5.0f, -1);
        GL11.glPopMatrix();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
