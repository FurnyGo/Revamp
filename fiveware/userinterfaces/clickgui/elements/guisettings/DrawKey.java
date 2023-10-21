package fiveware.userinterfaces.clickgui.elements.guisettings;

import fiveware.userinterfaces.clickgui.Element;
import fiveware.userinterfaces.clickgui.settings.Setting;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Keyboard;

public class DrawKey extends Element {
   public boolean isKeyTyped = false;

   public DrawKey(Setting setting) {
      this.setting = setting;
   }

   public void drawScreen(int mouseX, int mouseY, float partialTicks, int offset) {
      Gui.drawRect((double)this.x, (double)this.y, (double)(this.x + this.width), (double)(this.y + this.height), -1157627904);
      this.fr.drawStringWithShadow(this.setting.getName(), (float)(this.x + 3), (double)(this.y + this.height / 2 - this.fr.FONT_HEIGHT / 2 + 1), -1);
      String s = this.isKeyTyped ? "type...." : Keyboard.getKeyName(this.setting.getKey());
      this.fr.drawStringWithShadow(s, (float)(this.x + 3 + this.width - this.fr.getStringWidth(s) - 5), (double)(this.y + this.height / 2 - this.fr.FONT_HEIGHT / 2 + 1), -1);
   }

   public boolean isHovered(int mouseX, int mouseY) {
      return mouseX > this.x && mouseX < this.x + 2 + this.width - 4 && mouseY > this.y + 2 && mouseY < this.y + this.height - 2;
   }

   public void keyTyped(char typedChar, int keyCode) {
      if (this.isKeyTyped) {
         this.setting.setKey(keyCode);
         this.isKeyTyped = false;
      }

   }

   public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
      if (mouseButton == 0 && this.isHovered(mouseX, mouseY)) {
         this.isKeyTyped = !this.isKeyTyped;
      }

   }

   public void mouseReleased(int mouseX, int mouseY, int state) {
   }
}
