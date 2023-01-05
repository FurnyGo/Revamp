// 
// Decompiled by Procyon v0.5.36
// 

package revamp.userinterfaces.clickgui.elements;

import net.minecraft.client.gui.Gui;
import revamp.utilities.impl.render.Rainbow;
import revamp.userinterfaces.clickgui.Element;
import java.util.Iterator;
import revamp.modules.Module;
import revamp.Revamp;
import revamp.modules.Category;
import java.util.ArrayList;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.Minecraft;

public class DrawCategory
{
    public Minecraft mc;
    public FontRenderer fr;
    public ArrayList<DrawModule> drawModules;
    public Category category;
    public int x;
    public int y;
    public int width;
    public int height;
    public int dragX;
    public int dragY;
    public boolean extended;
    public boolean dragging;
    
    public DrawCategory(final Category category, final int x, final int y, final int width, final int height) {
        this.mc = Minecraft.getMinecraft();
        this.fr = Minecraft.getMinecraft().fontRendererObj;
        this.drawModules = new ArrayList<DrawModule>();
        this.category = category;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        Revamp.getINSTANCE();
        for (final Module module : Revamp.moduleManager.getModules()) {
            if (module.getCategory() == category) {
                this.drawModules.add(new DrawModule(module));
            }
        }
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        if (this.dragging) {
            this.x = this.dragX + mouseX;
            this.y = this.dragY + mouseY;
        }
        Gui.drawRect(this.x - 3, this.y, this.x + 100 + 3, this.y + this.height, Element.getSetting(Element.moduleName("ClickGUI"), "Rainbow").isToggled() ? Rainbow.rainbow(4.0f, 0.5f, 1.0f, 200L) : Revamp.getClientCOLOR().getRGB());
        final String name = String.valueOf(this.category.name().substring(0, 1).toUpperCase()) + this.category.name().substring(1).toLowerCase();
        this.fr.drawStringWithShadow(name, (float)(this.x + 50 - this.fr.getStringWidth(name) / 2), this.y + this.height / 2 - this.fr.FONT_HEIGHT / 2 + 1, -1);
        for (final DrawModule drawModule : this.drawModules) {
            final String string = String.valueOf(drawModule.module.getName()) + (drawModule.elements.isEmpty() ? "" : (drawModule.extended ? " " : " "));
            if (this.width < this.fr.getStringWidth(string) + 22) {
                this.width = this.fr.getStringWidth(string) + 22;
            }
        }
        if (this.extended) {
            int y = this.y + this.height;
            for (final DrawModule drawModule2 : this.drawModules) {
                drawModule2.updatePosition(this.x, y, this.width, this.height);
                drawModule2.drawScreen(mouseX, mouseY, partialTicks);
                y += this.height;
            }
        }
    }
    
    public boolean isHovered(final int mouseX, final int mouseY) {
        return mouseX >= this.x - 3 && mouseX <= this.x + this.width + 3 && mouseY > this.y && mouseY < this.y + this.height;
    }
    
    public void keyTyped(final char typedChar, final int keyCode) {
        if (this.extended) {
            for (final DrawModule drawModule : this.drawModules) {
                drawModule.keyTyped(typedChar, keyCode);
            }
        }
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (this.isHovered(mouseX, mouseY)) {
            if (mouseButton == 0) {
                this.dragX = this.x - mouseX;
                this.dragY = this.y - mouseY;
                this.dragging = true;
            }
            if (mouseButton == 1) {
                this.extended = !this.extended;
            }
        }
        if (this.extended) {
            for (final DrawModule drawModule : this.drawModules) {
                drawModule.mouseClicked(mouseX, mouseY, mouseButton);
            }
        }
    }
    
    public void mouseReleased(final int mouseX, final int mouseY, final int state) {
        this.dragging = false;
        if (this.extended) {
            for (final DrawModule drawModule : this.drawModules) {
                drawModule.mouseReleased(mouseX, mouseY, state);
            }
        }
    }
}
