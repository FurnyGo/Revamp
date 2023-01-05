// 
// Decompiled by Procyon v0.5.36
// 

package revamp.userinterfaces.mainmenu;

import java.util.Iterator;
import revamp.utilities.impl.render.Rainbow;
import org.lwjgl.opengl.GL11;
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

public class Changelog extends GuiScreen
{
    private int i;
    private List<String> changelog;
    
    public Changelog() {
        this.i = Randomizer.randomInt(1, 9);
        this.changelog = new ArrayList<String>();
    }
    
    @Override
    public void initGui() {
        final ScaledResolution sr = new ScaledResolution(this.mc);
        this.buttonList.add(new GuiButton(0, this.width / 2 - 20, this.height / 3 * 2, 50, 20, "Exit"));
        this.changelog.clear();
        this.changelog.addAll(Arrays.asList("§3Revamp §bv0.32", "§3-§b-§3-§b-§3-§b-§3-§b-§3-§3-§b-§3-§b-§3-§b-§3-§b-§3-", "§a+ Fullbright Gamma", "§a+ AimAssist", "§a+ Refill", "§a+ Client Saving", "§6* InvManager", "§6* Aura Fake Autoblock", "§6* Client Background", "§6* ESP", "§6* Main Menu", "§6* Login", "§c- Random Background", "§3-§b-§3-§b-§3-§b-§3-§b-§3-§3-§b-§3-§b-§3-§b-§3-§b-§3-"));
        super.initGui();
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.mc.getTextureManager().bindTexture(new ResourceLocation("revamp/background.jpg"));
        Gui.drawModalRectWithCustomSizedTexture(0, 0, 0.0f, 0.0f, this.width, this.height, (float)this.width, (float)this.height);
        GL11.glPushMatrix();
        GL11.glScalef(2.5f, 2.5f, 2.5f);
        this.drawCenteredString(this.fontRendererObj, "Changelog", (float)(this.width / 5), (float)(this.height / 3), Rainbow.rainbow(4.0f, 0.7f, 1.0f, 200L));
        GL11.glPopMatrix();
        int y = this.height / 2 - 50;
        if (!this.changelog.isEmpty()) {
            for (final String text : this.changelog) {
                this.drawCenteredString(this.fontRendererObj, text, (float)(this.width / 2), (float)y, -1);
                y += this.fontRendererObj.FONT_HEIGHT;
            }
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    @Override
    protected void actionPerformed(final GuiButton button) {
        switch (button.id) {
            case 0: {
                this.mc.displayGuiScreen(new CustomMainMenu());
                break;
            }
        }
    }
}
