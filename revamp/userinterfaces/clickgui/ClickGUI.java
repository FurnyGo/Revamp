// 
// Decompiled by Procyon v0.5.36
// 

package revamp.userinterfaces.clickgui;

import java.io.IOException;
import java.util.Iterator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import revamp.modules.Module;
import revamp.Revamp;
import net.minecraft.client.gui.ScaledResolution;
import revamp.modules.Category;
import revamp.userinterfaces.clickgui.elements.DrawCategory;
import java.util.ArrayList;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

public class ClickGUI extends GuiScreen
{
    public final Minecraft mc;
    public final FontRenderer fr;
    public ArrayList<DrawCategory> drawCategories;
    public int x;
    public int y;
    public int width;
    public int height;
    
    public ClickGUI() {
        this.mc = Minecraft.getMinecraft();
        this.fr = this.mc.fontRendererObj;
        this.drawCategories = new ArrayList<DrawCategory>();
        this.x = 50;
        this.y = 50;
        this.width = 90;
        this.height = 15;
        Category[] values;
        for (int length = (values = Category.values()).length, i = 0; i < length; ++i) {
            final Category category = values[i];
            this.drawCategories.add(new DrawCategory(category, this.x, this.y, this.width, this.height));
            this.y += 20;
        }
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        final ScaledResolution scaledResolution = new ScaledResolution(this.mc);
        final revamp.modules.impl.render.ClickGUI clickGui = (revamp.modules.impl.render.ClickGUI)Revamp.moduleManager.getModuleByName("ClickGUI");
        for (final DrawCategory drawCategory : this.drawCategories) {
            drawCategory.drawScreen(mouseX, mouseY, partialTicks);
        }
        if (Revamp.settingManager.getSetting(clickGui, "Anime").isToggled()) {
            this.mc.getTextureManager().bindTexture(new ResourceLocation("revamp/anime/" + Revamp.settingManager.getSetting(clickGui, "Character").getCurrentMode().toLowerCase() + ".png"));
            Gui.drawModalRectWithCustomSizedTexture(scaledResolution.getScaledWidth() - 300, scaledResolution.getScaledHeight() - 470, 0.0f, 0.0f, 300, 475, 300.0f, 475.0f);
        }
        if (Revamp.settingManager.getSetting(clickGui, "Blur").isToggled() && OpenGlHelper.shadersSupported && this.mc.getRenderViewEntity() instanceof EntityPlayer) {
            if (this.mc.entityRenderer.theShaderGroup != null) {
                this.mc.entityRenderer.theShaderGroup.deleteShaderGroup();
            }
            this.mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/blur.json"));
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    @Override
    protected void keyTyped(final char typedChar, final int keyCode) throws IOException {
        for (final DrawCategory drawCategory : this.drawCategories) {
            drawCategory.keyTyped(typedChar, keyCode);
        }
        super.keyTyped(typedChar, keyCode);
    }
    
    @Override
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        for (final DrawCategory drawCategory : this.drawCategories) {
            drawCategory.mouseClicked(mouseX, mouseY, mouseButton);
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
    
    @Override
    protected void mouseReleased(final int mouseX, final int mouseY, final int state) {
        for (final DrawCategory drawCategory : this.drawCategories) {
            drawCategory.mouseReleased(mouseX, mouseY, state);
        }
        super.mouseReleased(mouseX, mouseY, state);
    }
    
    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    @Override
    public void onGuiClosed() {
        if (this.mc.entityRenderer.theShaderGroup != null) {
            this.mc.entityRenderer.theShaderGroup.deleteShaderGroup();
            this.mc.entityRenderer.theShaderGroup = null;
        }
        Revamp.moduleManager.getModuleByName("ClickGUI").setToggled(false);
    }
}
