// 
// Decompiled by Procyon v0.5.36
// 

package revamp.userinterfaces.clickgui.elements.guisettings;

import org.lwjgl.input.Keyboard;
import net.minecraft.client.gui.Gui;
import revamp.userinterfaces.clickgui.settings.Setting;
import revamp.userinterfaces.clickgui.Element;

public class DrawKey extends Element
{
    public boolean isKeyTyped;
    
    public DrawKey(final Setting setting) {
        this.isKeyTyped = false;
        this.setting = setting;
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        Gui.drawRect(this.x, this.y, this.x + this.width, this.y + this.height, -1157627904);
        this.fr.drawStringWithShadow(this.setting.getName(), (float)(this.x + 3), this.y + this.height / 2 - this.fr.FONT_HEIGHT / 2 + 1, -1);
        final String s = this.isKeyTyped ? "type...." : Keyboard.getKeyName(this.setting.getKey());
        this.fr.drawStringWithShadow(s, (float)(this.x + 3 + this.width - this.fr.getStringWidth(s) - 5), this.y + this.height / 2 - this.fr.FONT_HEIGHT / 2 + 1, -1);
    }
    
    public boolean isHovered(final int mouseX, final int mouseY) {
        return mouseX > this.x && mouseX < this.x + 2 + this.width - 4 && mouseY > this.y + 2 && mouseY < this.y + this.height - 2;
    }
    
    @Override
    public void keyTyped(final char typedChar, final int keyCode) {
        if (this.isKeyTyped) {
            this.setting.setKey(keyCode);
            this.isKeyTyped = false;
        }
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (mouseButton == 0 && this.isHovered(mouseX, mouseY)) {
            this.isKeyTyped = !this.isKeyTyped;
        }
    }
    
    @Override
    public void mouseReleased(final int mouseX, final int mouseY, final int state) {
    }
}
