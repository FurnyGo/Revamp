// 
// Decompiled by Procyon v0.5.36
// 

package revamp.userinterfaces.clickgui.elements.guisettings;

import revamp.Revamp;
import revamp.utilities.impl.render.Rainbow;
import net.minecraft.client.gui.Gui;
import revamp.userinterfaces.clickgui.settings.Setting;
import revamp.userinterfaces.clickgui.Element;

public class DrawComboBox extends Element
{
    public DrawComboBox(final Setting setting) {
        this.setting = setting;
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        Gui.drawRect(this.x, this.y, this.x + this.width, this.y + this.height, -1157627904);
        this.fr.drawStringWithShadow(this.setting.getName(), (float)(this.x + this.width / 2 - this.fr.getStringWidth(this.setting.getName()) / 2), this.y + this.height / 2 - this.fr.FONT_HEIGHT / 2 + 1, -1);
        if (this.extended) {
            int y = this.y + this.height;
            String[] modes;
            for (int length = (modes = this.setting.getModes()).length, i = 0; i < length; ++i) {
                final String mode = modes[i];
                Gui.drawRect(this.x, y, this.x + this.width, y + this.height, -1157627904);
                this.fr.drawStringWithShadow(mode, (float)(this.x + this.width / 2 - this.fr.getStringWidth(mode) / 2), y + this.height / 2 - this.fr.FONT_HEIGHT / 2 + 1, this.setting.getCurrentMode().equals(mode) ? (Element.getSetting(Element.moduleName("ClickGUI"), "Rainbow").isToggled() ? Rainbow.rainbow(4.0f, 0.5f, 1.0f, 200L) : Revamp.getClientCOLOR().getRGB()) : -1);
                y += this.height;
            }
        }
    }
    
    public boolean isHovered(final int mouseX, final int mouseY) {
        return mouseX > this.x && mouseX < this.x + this.width && mouseY > this.y && mouseY < this.y + this.height;
    }
    
    @Override
    public void keyTyped(final char typedChar, final int keyCode) {
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (this.isHovered(mouseX, mouseY) && (mouseButton == 0 || mouseButton == 1)) {
            this.extended = !this.extended;
        }
        if (this.extended) {
            int y = this.y + this.height;
            String[] modes;
            for (int length = (modes = this.setting.getModes()).length, i = 0; i < length; ++i) {
                final String mode = modes[i];
                if (mouseButton == 0 && mouseX > this.x && mouseX < this.x + this.width && mouseY > y && mouseY < y + this.height) {
                    this.setting.setCurrentMode(mode);
                }
                y += this.height;
            }
        }
    }
    
    @Override
    public void mouseReleased(final int mouseX, final int mouseY, final int state) {
    }
}
