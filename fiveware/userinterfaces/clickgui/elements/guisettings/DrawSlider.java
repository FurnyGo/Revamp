package fiveware.userinterfaces.clickgui.elements.guisettings;

import fiveware.userinterfaces.clickgui.Element;
import fiveware.userinterfaces.clickgui.settings.Setting;
import net.minecraft.client.gui.Gui;

public class DrawSlider extends Element {
   public boolean dragging;

   public DrawSlider(Setting setting) {
      this.setting = setting;
   }

   public void drawScreen(int mouseX, int mouseY, float partialTicks, int offset) {
      Gui.drawRect((double)this.x, (double)this.y, (double)(this.x + this.width), (double)(this.y + this.height), -1157627904);
      this.fr.drawStringWithShadow(this.setting.getName(), (float)(this.x + 3), (double)(this.y + this.height / 2 - this.fr.FONT_HEIGHT / 2 - 1), -1);
      this.fr.drawStringWithShadow(String.valueOf(this.setting.getCurrentValue()), (float)(this.x + this.width - this.fr.getStringWidth(String.valueOf(this.setting.getCurrentValue())) - 3), (double)(this.y + this.height / 2 - this.fr.FONT_HEIGHT / 2 - 1), -1);
      Gui.drawRect((double)this.x, (double)(this.y + this.height - 3), (double)(this.x + this.width), (double)(this.y + this.height - 1), -1157627904);
      double currentValue = (double)((this.setting.getCurrentValue() - this.setting.getMinValue()) / (this.setting.getMaxValue() - this.setting.getMinValue()));
      Gui.drawRect((double)this.x, (double)(this.y + this.height - 3), (double)((int)((double)this.x + currentValue * (double)this.width)), (double)(this.y + this.height - 1), getSetting(moduleName("ClickGUI"), "Rainbow").isToggled() ? Element.rainbowMode(offset) : this.setting.module.getCategory().getCategoryColor());
      if (this.dragging) {
         this.updateValue((double)mouseX);
      }

   }

   public void updateValue(double mouseX) {
      float currentValue = (float)((double)Math.round((float)Math.max(Math.min((mouseX - (double)this.x) / (double)this.width * (double)(this.setting.getMaxValue() - this.setting.getMinValue()) + (double)this.setting.getMinValue(), (double)this.setting.getMaxValue()), (double)this.setting.getMinValue()) * 100.0F) / 100.0D);
      this.setting.setCurrentValue(this.setting.isOnlyInteger() ? (float)Math.round(currentValue) : currentValue);
   }

   public boolean isHovered(int mouseX, int mouseY) {
      return mouseX > this.x && mouseX < this.x + this.width && mouseY > this.y + this.height - 4 && mouseY < this.y + this.height;
   }

   public void keyTyped(char typedChar, int keyCode) {
   }

   public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
      if (this.isHovered(mouseX, mouseY) && mouseButton == 0) {
         this.dragging = true;
      }

   }

   public void mouseReleased(int mouseX, int mouseY, int state) {
      this.dragging = false;
   }
}
