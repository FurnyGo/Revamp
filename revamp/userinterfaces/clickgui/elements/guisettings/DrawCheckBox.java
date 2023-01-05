// 
// Decompiled by Procyon v0.5.36
// 

package revamp.userinterfaces.clickgui.elements.guisettings;

import revamp.utilities.impl.render.Rainbow;
import net.minecraft.client.gui.Gui;
import revamp.userinterfaces.clickgui.settings.Setting;
import revamp.userinterfaces.clickgui.Element;

public class DrawCheckBox extends Element
{
    public DrawCheckBox(final Setting setting) {
        this.setting = setting;
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        Gui.drawRect(this.x, this.y, this.x + this.width, this.y + this.height, -1157627904);
        Gui.drawRect(this.x + 2, this.y + 2, this.x + 2 + this.height - 4, this.y + this.height - 2, this.setting.isToggled() ? (Element.getSetting(Element.moduleName("ClickGUI"), "Rainbow").isToggled() ? Rainbow.rainbow(4.0f, 0.5f, 1.0f, 200L) : this.setting.module.getCategory().getCategoryColor()) : -15724528);
        this.fr.drawStringWithShadow(this.setting.getName(), (float)(this.x + this.width - this.fr.getStringWidth(this.setting.getName()) - 3), this.y + this.height / 2 - this.fr.FONT_HEIGHT / 2 + 1, -1);
    }
    
    public boolean isHovered(final int mouseX, final int mouseY) {
        return mouseX > this.x + 2 && mouseX < this.x + 2 + this.height - 4 && mouseY > this.y + 2 && mouseY < this.y + this.height - 2;
    }
    
    @Override
    public void keyTyped(final char typedChar, final int keyCode) {
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (mouseButton == 0 && this.isHovered(mouseX, mouseY)) {
            this.setting.setToggled(!this.setting.isToggled());
        }
    }
    
    @Override
    public void mouseReleased(final int mouseX, final int mouseY, final int state) {
    }
}
