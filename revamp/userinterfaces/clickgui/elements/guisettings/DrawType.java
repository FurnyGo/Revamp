// 
// Decompiled by Procyon v0.5.36
// 

package revamp.userinterfaces.clickgui.elements.guisettings;

import net.minecraft.util.ChatAllowedCharacters;
import org.lwjgl.input.Keyboard;
import net.minecraft.client.gui.Gui;
import revamp.userinterfaces.clickgui.settings.Setting;
import revamp.userinterfaces.clickgui.Element;

public class DrawType extends Element
{
    public boolean isKeyTyped;
    
    public DrawType(final Setting setting) {
        this.isKeyTyped = false;
        this.setting = setting;
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        Gui.drawRect(this.x, this.y, this.x + this.width, this.y + this.height, -1157627904);
        this.fr.drawStringWithShadow(this.setting.getName(), (float)(this.x + 3), this.y + this.height / 2 - this.fr.FONT_HEIGHT / 2 + 1, -1);
        final String s = this.setting.getTyped();
        final int rnd = (int)(System.currentTimeMillis() / 1000L);
        this.fr.drawStringWithShadow(String.valueOf(s.replace('§', '&')) + (this.isKeyTyped ? ((rnd % 2 == 0) ? "§7_" : "") : ""), (float)(this.x + 3 + this.width - this.fr.getStringWidth(s.replace('§', '&')) - 5 - (this.isKeyTyped ? (this.fr.getStringWidth("§7_") - 1) : 0)), this.y + this.height / 2 - this.fr.FONT_HEIGHT / 2 + 1, -1);
    }
    
    public boolean isHovered(final int mouseX, final int mouseY) {
        return mouseX > this.x && mouseX < this.x + 2 + this.width - 4 && mouseY > this.y + 2 && mouseY < this.y + this.height - 2;
    }
    
    @Override
    public void keyTyped(final char typedChar, final int keyCode) {
        Keyboard.enableRepeatEvents(true);
        if (this.isKeyTyped) {
            final int key = (Keyboard.getEventKey() == 0) ? (Keyboard.getEventCharacter() + '\u0100') : Keyboard.getEventKey();
            if (key == 28) {
                this.isKeyTyped = false;
            }
            else if (key == 14) {
                if (this.setting.getTyped().length() <= 0) {
                    return;
                }
                this.setting.setTyped(this.setting.getTyped().substring(0, this.setting.getTyped().length() - 1));
                return;
            }
            if (ChatAllowedCharacters.isAllowedCharacter(typedChar) || typedChar == '§') {
                this.setting.setTyped(String.valueOf(this.setting.getTyped()) + typedChar);
            }
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
