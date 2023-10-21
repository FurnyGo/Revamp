package fiveware.userinterfaces.clickgui.elements.guisettings;

import fiveware.userinterfaces.clickgui.Element;
import fiveware.userinterfaces.clickgui.settings.Setting;
import net.minecraft.client.gui.Gui;

public class DrawComboBox extends Element {
   public DrawComboBox(Setting setting) {
      this.setting = setting;
   }

   public void drawScreen(int mouseX, int mouseY, float partialTicks, int offset) {
      Gui.drawRect((double)this.x, (double)this.y, (double)(this.x + this.width), (double)(this.y + this.height), -1157627904);
      this.fr.drawStringWithShadow(this.setting.getName(), (float)(this.x + this.width / 2 - this.fr.getStringWidth(this.setting.getName()) / 2), (double)(this.y + this.height / 2 - this.fr.FONT_HEIGHT / 2 + 1), -1);
      if (this.extended) {
         int y = this.y + this.height;
         String[] var9;
         int var8 = (var9 = this.setting.getModes()).length;

         for(int var7 = 0; var7 < var8; ++var7) {
            String mode = var9[var7];
            Gui.drawRect((double)this.x, (double)y, (double)(this.x + this.width), (double)(y + this.height), -1157627904);
            this.fr.drawStringWithShadow(mode, (float)(this.x + this.width / 2 - this.fr.getStringWidth(mode) / 2), (double)(y + this.height / 2 - this.fr.FONT_HEIGHT / 2 + 1), this.setting.getCurrentMode().equals(mode) ? (getSetting(moduleName("ClickGUI"), "Rainbow").isToggled() ? Element.rainbowMode(offset) : this.setting.module.getCategory().getCategoryColor()) : -1);
            y += this.height;
         }
      }

   }

   public boolean isHovered(int mouseX, int mouseY) {
      return mouseX > this.x && mouseX < this.x + this.width && mouseY > this.y && mouseY < this.y + this.height;
   }

   public void keyTyped(char typedChar, int keyCode) {
   }

   public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
      if (this.isHovered(mouseX, mouseY) && (mouseButton == 0 || mouseButton == 1)) {
         this.extended = !this.extended;
      }

      if (this.extended) {
         int y = this.y + this.height;
         String[] var8;
         int var7 = (var8 = this.setting.getModes()).length;

         for(int var6 = 0; var6 < var7; ++var6) {
            String mode = var8[var6];
            if (mouseButton == 0 && mouseX > this.x && mouseX < this.x + this.width && mouseY > y && mouseY < y + this.height) {
               this.setting.setCurrentMode(mode);
            }

            y += this.height;
         }
      }

   }

   public void mouseReleased(int mouseX, int mouseY, int state) {
   }
}
