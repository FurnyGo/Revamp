package fiveware.userinterfaces.clickgui.elements.guisettings;

import fiveware.userinterfaces.clickgui.Element;
import fiveware.userinterfaces.clickgui.settings.Setting;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ChatAllowedCharacters;
import org.lwjgl.input.Keyboard;

public class DrawType extends Element {
   public boolean isKeyTyped = false;

   public DrawType(Setting setting) {
      this.setting = setting;
   }

   public void drawScreen(int mouseX, int mouseY, float partialTicks, int offset) {
      Gui.drawRect((double)this.x, (double)this.y, (double)(this.x + this.width), (double)(this.y + this.height), -1157627904);
      this.fr.drawStringWithShadow(this.setting.getName(), (float)(this.x + 3), (double)(this.y + this.height / 2 - this.fr.FONT_HEIGHT / 2 + 1), -1);
      String s = this.setting.getTyped();
      int rnd = (int)(System.currentTimeMillis() / 1000L);
      this.fr.drawStringWithShadow(s.replace('§', '&') + (this.isKeyTyped ? (rnd % 2 == 0 ? "§7_" : "") : ""), (float)(this.x + 3 + this.width - this.fr.getStringWidth(s.replace('§', '&')) - 5 - (this.isKeyTyped ? this.fr.getStringWidth("§7_") - 1 : 0)), (double)(this.y + this.height / 2 - this.fr.FONT_HEIGHT / 2 + 1), -1);
   }

   public boolean isHovered(int mouseX, int mouseY) {
      return mouseX > this.x && mouseX < this.x + 2 + this.width - 4 && mouseY > this.y + 2 && mouseY < this.y + this.height - 2;
   }

   public void keyTyped(char typedChar, int keyCode) {
      Keyboard.enableRepeatEvents(true);
      if (this.isKeyTyped) {
         int key = Keyboard.getEventKey() == 0 ? Keyboard.getEventCharacter() + 256 : Keyboard.getEventKey();
         if (key == 28) {
            this.isKeyTyped = false;
         } else if (key == 14) {
            if (this.setting.getTyped().length() <= 0) {
               return;
            }

            this.setting.setTyped(this.setting.getTyped().substring(0, this.setting.getTyped().length() - 1));
            return;
         }

         if (ChatAllowedCharacters.isAllowedCharacter(typedChar) || typedChar == 167) {
            this.setting.setTyped(this.setting.getTyped() + typedChar);
         }
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
