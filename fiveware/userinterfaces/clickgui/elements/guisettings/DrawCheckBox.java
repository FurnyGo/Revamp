package fiveware.userinterfaces.clickgui.elements.guisettings;

import fiveware.userinterfaces.clickgui.Element;
import fiveware.userinterfaces.clickgui.settings.Setting;
import net.minecraft.client.gui.Gui;

public class DrawCheckBox extends Element {
   public DrawCheckBox(Setting setting) {
      this.setting = setting;
   }

   public void drawScreen(int mouseX, int mouseY, float partialTicks, int offset) {
      Gui.drawRect((double)this.x, (double)this.y, (double)(this.x + this.width), (double)(this.y + this.height), -1157627904);
      Gui.drawRect((double)(this.x + 2), (double)(this.y + 2), (double)(this.x + 2 + this.height - 4), (double)(this.y + this.height - 2), this.setting.isToggled() ? (getSetting(moduleName("ClickGUI"), "Rainbow").isToggled() ? Element.rainbowMode(offset) : this.setting.module.getCategory().getCategoryColor()) : -15724528);
      this.fr.drawStringWithShadow(this.setting.getName(), (float)(this.x + this.width - this.fr.getStringWidth(this.setting.getName()) - 3), (double)(this.y + this.height / 2 - this.fr.FONT_HEIGHT / 2 + 1), -1);
   }

   public boolean isHovered(int mouseX, int mouseY) {
      return mouseX > this.x + 2 && mouseX < this.x + 2 + this.height - 4 && mouseY > this.y + 2 && mouseY < this.y + this.height - 2;
   }

   public void keyTyped(char typedChar, int keyCode) {
   }

   public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
      if (mouseButton == 0 && this.isHovered(mouseX, mouseY)) {
         this.setting.setToggled(!this.setting.isToggled());
      }

   }

   public void mouseReleased(int mouseX, int mouseY, int state) {
   }
}
