// 
// Decompiled by Procyon v0.5.36
// 

package revamp.userinterfaces.clickgui.elements.guisettings;

import revamp.utilities.impl.render.Rainbow;
import net.minecraft.client.gui.Gui;
import revamp.userinterfaces.clickgui.settings.Setting;
import revamp.userinterfaces.clickgui.Element;

public class DrawSlider extends Element
{
    public boolean dragging;
    
    public DrawSlider(final Setting setting) {
        this.setting = setting;
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        Gui.drawRect(this.x, this.y, this.x + this.width, this.y + this.height, -1157627904);
        this.fr.drawStringWithShadow(this.setting.getName(), (float)(this.x + 3), this.y + this.height / 2 - this.fr.FONT_HEIGHT / 2 - 1, -1);
        this.fr.drawStringWithShadow(new StringBuilder(String.valueOf(this.setting.getCurrentValue())).toString(), (float)(this.x + this.width - this.fr.getStringWidth(new StringBuilder(String.valueOf(this.setting.getCurrentValue())).toString()) - 3), this.y + this.height / 2 - this.fr.FONT_HEIGHT / 2 - 1, -1);
        Gui.drawRect(this.x, this.y + this.height - 3, this.x + this.width, this.y + this.height - 1, -1157627904);
        final double currentValue = (this.setting.getCurrentValue() - this.setting.getMinValue()) / (this.setting.getMaxValue() - this.setting.getMinValue());
        Gui.drawRect(this.x, this.y + this.height - 3, (int)(this.x + currentValue * this.width), this.y + this.height - 1, Element.getSetting(Element.moduleName("ClickGUI"), "Rainbow").isToggled() ? Rainbow.rainbow(4.0f, 0.5f, 1.0f, 200L) : this.setting.module.getCategory().getCategoryColor());
        if (this.dragging) {
            this.updateValue(mouseX);
        }
    }
    
    public void updateValue(final double mouseX) {
        final float currentValue = (float)(Math.round((float)Math.max(Math.min((mouseX - this.x) / this.width * (this.setting.getMaxValue() - this.setting.getMinValue()) + this.setting.getMinValue(), this.setting.getMaxValue()), this.setting.getMinValue()) * 100.0f) / 100.0);
        this.setting.setCurrentValue(this.setting.isOnlyInteger() ? ((float)Math.round(currentValue)) : currentValue);
    }
    
    public boolean isHovered(final int mouseX, final int mouseY) {
        return mouseX > this.x && mouseX < this.x + this.width && mouseY > this.y + this.height - 4 && mouseY < this.y + this.height;
    }
    
    @Override
    public void keyTyped(final char typedChar, final int keyCode) {
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (this.isHovered(mouseX, mouseY) && mouseButton == 0) {
            this.dragging = true;
        }
    }
    
    @Override
    public void mouseReleased(final int mouseX, final int mouseY, final int state) {
        this.dragging = false;
    }
}
