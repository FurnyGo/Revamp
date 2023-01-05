// 
// Decompiled by Procyon v0.5.36
// 

package revamp.userinterfaces.setup;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.FontRenderer;
import revamp.utilities.impl.time.Time;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.Minecraft;
import revamp.utilities.impl.render.Rainbow;
import revamp.Revamp;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.gui.GuiScreen;

public class HUDSetup extends GuiScreen
{
    public boolean title;
    public boolean list;
    public boolean hotbar;
    public boolean rainbow;
    
    public HUDSetup() {
        this.title = true;
        this.list = true;
        this.hotbar = true;
        this.rainbow = true;
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.mc.getTextureManager().bindTexture(new ResourceLocation("revamp/background.jpg"));
        Gui.drawModalRectWithCustomSizedTexture(0, 0, 0.0f, 0.0f, this.width, this.height, (float)this.width, (float)this.height);
        GL11.glPushMatrix();
        GL11.glScalef(2.5f, 2.5f, 2.5f);
        this.drawCenteredString(this.fontRendererObj, "Revamp " + Revamp.getVERSION(), (float)(this.width / 5), (float)(this.height / 3 - 5), Rainbow.rainbow(4.0f, 0.7f, 1.0f, 200L));
        GL11.glPopMatrix();
        this.drawCenteredString(this.fontRendererObj, "Would you like to stick with this?", (float)(this.width / 2), (float)(this.height / 5), -1);
        final Minecraft mc = Minecraft.getMinecraft();
        final ScaledResolution sr = new ScaledResolution(mc);
        final FontRenderer fr = mc.fontRendererObj;
        if (this.title) {
            float offset = 0.0f;
            for (int i = 0; i < Revamp.getNAME().length(); ++i) {
                final String character = String.valueOf(Revamp.getNAME().charAt(i));
                fr.drawStringWithShadow(character, 4.0f + offset, 4.0, this.rainbow ? Rainbow.rainbow(4.0f, 0.5f, 1.0f, i * 200) : Revamp.getClientCOLOR().getRGB());
                fr.drawStringWithShadow(Revamp.getVERSION(), 43.0f, 4.0, -1);
                offset += (float)(fr.getStringWidth(character) + 0.04);
            }
        }
        if (this.hotbar) {
            Gui.drawRect(0.0, sr.getScaledHeight() - 22, sr.getScaledWidth(), sr.getScaledHeight(), 1879048192);
            fr.drawStringWithShadow("[XYZ] 999 -999 999", 1.0f, sr.getScaledHeight() - 10, -1);
            fr.drawStringWithShadow("[FPS] 999", 1.0f, sr.getScaledHeight() - 20, -1);
            fr.drawStringWithShadow("[BPS] 99.99", (float)(fr.getStringWidth("[FPS] 999") + 5), sr.getScaledHeight() - 20, -1);
            fr.drawStringWithShadow(Time.currentTime(true, ":"), (float)(sr.getScaledWidth() - 42), sr.getScaledHeight() - 20, -1);
            fr.drawStringWithShadow(Time.currentDate("/", false), (float)(sr.getScaledWidth() - 50), sr.getScaledHeight() - 10, -1);
        }
        if (this.list) {
            int count = 0;
            final double offset2 = count * fr.FONT_HEIGHT + 4;
            fr.drawStringWithShadow("AutoClicker §78.0 - 12.0", (float)(sr.getScaledWidth() - fr.getStringWidth("AutoClicker §78.0 - 12.0") - 4), offset2, this.rainbow ? Rainbow.rainbow(5.0f, 0.5f, 1.0f, count * 200) : Revamp.getClientCOLOR().getRGB());
            ++count;
            fr.drawStringWithShadow("InventoryMove", (float)(sr.getScaledWidth() - fr.getStringWidth("InventoryMove") - 4), offset2 + 9.0, this.rainbow ? Rainbow.rainbow(5.0f, 0.5f, 1.0f, count * 200) : Revamp.getClientCOLOR().getRGB());
            ++count;
            fr.drawStringWithShadow("Timer §710.0", (float)(sr.getScaledWidth() - fr.getStringWidth("Timer §710.0") - 4), offset2 + 18.0, this.rainbow ? Rainbow.rainbow(5.0f, 0.5f, 1.0f, count * 200) : Revamp.getClientCOLOR().getRGB());
            ++count;
            fr.drawStringWithShadow("Reach §74.0", (float)(sr.getScaledWidth() - fr.getStringWidth("Reach §74.0") - 4), offset2 + 27.0, this.rainbow ? Rainbow.rainbow(5.0f, 0.5f, 1.0f, count * 200) : Revamp.getClientCOLOR().getRGB());
            ++count;
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    @Override
    public void initGui() {
        final ScaledResolution sr = new ScaledResolution(this.mc);
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 3 * 2, "Continue"));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 45, (int)(this.height / 3 * 0.8), 90, 20, "Arraylist"));
        this.buttonList.add(new GuiButton(2, this.width / 2 - 45, (int)(this.height / 3 * 1.1), 90, 20, "Rainbow"));
        this.buttonList.add(new GuiButton(3, this.width / 2 - 45, (int)(this.height / 3 * 1.4), 90, 20, "Hotbar"));
        this.buttonList.add(new GuiButton(4, this.width / 2 - 45, (int)(this.height / 3 * 1.7), 90, 20, "Title"));
        super.initGui();
    }
    
    @Override
    protected void actionPerformed(final GuiButton button) {
        switch (button.id) {
            case 0: {
                this.mc.displayGuiScreen(new ServerSetup());
                break;
            }
            case 1: {
                this.list = !this.list;
                if (this.list) {
                    this.list = true;
                    Revamp.settingManager.getSetting(Revamp.moduleManager.getModuleByName("HUD"), "Arraylist").setToggled(true);
                    break;
                }
                this.list = false;
                Revamp.settingManager.getSetting(Revamp.moduleManager.getModuleByName("HUD"), "Arraylist").setToggled(false);
                break;
            }
            case 2: {
                this.rainbow = !this.rainbow;
                if (this.rainbow) {
                    this.rainbow = true;
                    Revamp.settingManager.getSetting(Revamp.moduleManager.getModuleByName("HUD"), "Rainbow").setToggled(true);
                    break;
                }
                this.rainbow = false;
                Revamp.settingManager.getSetting(Revamp.moduleManager.getModuleByName("HUD"), "Rainbow").setToggled(false);
                break;
            }
            case 3: {
                this.hotbar = !this.hotbar;
                if (this.hotbar) {
                    this.hotbar = true;
                    Revamp.settingManager.getSetting(Revamp.moduleManager.getModuleByName("HUD"), "Hotbar").setToggled(true);
                    break;
                }
                this.hotbar = false;
                Revamp.settingManager.getSetting(Revamp.moduleManager.getModuleByName("HUD"), "Hotbar").setToggled(false);
                break;
            }
            case 4: {
                this.title = !this.title;
                if (this.title) {
                    this.title = true;
                    Revamp.settingManager.getSetting(Revamp.moduleManager.getModuleByName("HUD"), "Title").setToggled(true);
                    break;
                }
                this.title = false;
                Revamp.settingManager.getSetting(Revamp.moduleManager.getModuleByName("HUD"), "Title").setToggled(false);
                break;
            }
        }
    }
}
