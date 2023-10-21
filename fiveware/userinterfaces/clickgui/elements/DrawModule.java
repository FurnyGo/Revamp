package fiveware.userinterfaces.clickgui.elements;

import fiveware.Fiveware;
import fiveware.modules.Module;
import fiveware.userinterfaces.clickgui.Element;
import fiveware.userinterfaces.clickgui.elements.guisettings.DrawCheckBox;
import fiveware.userinterfaces.clickgui.elements.guisettings.DrawComboBox;
import fiveware.userinterfaces.clickgui.elements.guisettings.DrawKey;
import fiveware.userinterfaces.clickgui.elements.guisettings.DrawSlider;
import fiveware.userinterfaces.clickgui.elements.guisettings.DrawType;
import fiveware.userinterfaces.clickgui.settings.Setting;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Keyboard;

public class DrawModule {
   private final Minecraft mc = Minecraft.getMinecraft();
   private final FontRenderer fr;
   public final ArrayList<Element> elements;
   public Module module;
   public int x;
   public int y;
   public int width;
   public int height;
   public boolean extended;

   public DrawModule(Module module) {
      this.fr = Minecraft.getMinecraft().fontRendererObj;
      this.elements = new ArrayList();
      this.module = module;
      Iterator var3 = Fiveware.settingManager.getSetting().iterator();

      while(var3.hasNext()) {
         Setting setting = (Setting)var3.next();
         if (setting.getModule() == module) {
            if (setting.getType() == Setting.Type.CHECKBOX) {
               this.elements.add(new DrawCheckBox(setting));
            }

            if (setting.getType() == Setting.Type.COMBOBOX) {
               this.elements.add(new DrawComboBox(setting));
            }

            if (setting.getType() == Setting.Type.SLIDER) {
               this.elements.add(new DrawSlider(setting));
            }

            if (setting.getType() == Setting.Type.KEY) {
               this.elements.add(new DrawKey(setting));
            }

            if (setting.getType() == Setting.Type.TYPE) {
               this.elements.add(new DrawType(setting));
            }
         }
      }

   }

   public void updatePosition(int x, int y, int width, int height) {
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
   }

   public void drawScreen(int mouseX, int mouseY, float partialTicks, int offset1) {
      Gui.drawRect((double)(this.x - 2), (double)this.y, (double)(this.x + 102), (double)(this.y + this.height), -1157627904);
      if (this.isHovered(mouseX, mouseY)) {
         Gui.drawRect((double)(mouseX + 3), (double)(mouseY - 7), (double)(mouseX + this.fr.getStringWidth(this.module.getDescription()) + 4), (double)(mouseY + 3), 1342177280);
         Gui.drawRect((double)(mouseX + 1), (double)(mouseY - 8), (double)(mouseX + this.fr.getStringWidth(this.module.getDescription()) + 5), (double)(mouseY + 4), 1342177280);
         this.fr.drawString(this.module.getDescription(), mouseX + 4, mouseY - 6, -1);
      }

      int textColor = this.module.isToggled() ? (Element.getSetting(Element.moduleName("ClickGUI"), "Rainbow").isToggled() ? Element.rainbowMode(offset1) : this.module.getCategory().getCategoryColor()) : (new Color(-1)).getRGB();
      this.fr.drawStringWithShadow(this.module.getName(), (float)(this.x + 5), (double)(this.y + this.height / 2 - this.fr.FONT_HEIGHT / 2 + 1), textColor);
      int settingWidth = 0;
      Iterator var8 = this.elements.iterator();

      while(var8.hasNext()) {
         Element element = (Element)var8.next();
         this.fr.drawStringWithShadow(this.extended ? "-" : "+", (float)(this.x + 90), (double)(this.y + this.height / 2 - this.fr.FONT_HEIGHT / 2 + 1), textColor);
         String settingName = element.setting.getName();
         byte offset;
         if (element.setting.getType() == Setting.Type.CHECKBOX) {
            offset = 10;
            if (settingWidth < this.fr.getStringWidth(settingName) + this.height - 4 + offset) {
               settingWidth = this.fr.getStringWidth(settingName) + this.height - 4 + offset;
            }
         }

         String string;
         int offset;
         if (element.setting.getType() == Setting.Type.COMBOBOX) {
            string = settingName + (element.extended ? "-" : "+");
            if (settingWidth < this.fr.getStringWidth(string) + 22) {
               settingWidth = this.fr.getStringWidth(string) + 22;
            }

            String[] var14;
            int var13 = (var14 = element.setting.getModes()).length;

            for(offset = 0; offset < var13; ++offset) {
               String mode = var14[offset];
               int offset = 10;
               if (settingWidth < this.fr.getStringWidth(mode) + offset) {
                  settingWidth = this.fr.getStringWidth(mode) + offset;
               }
            }
         }

         if (element.setting.getType() == Setting.Type.KEY) {
            string = element.setting.getTyped();
            DrawKey key = (DrawKey)element;
            offset = this.fr.getStringWidth(element.setting.getName()) + (key.isKeyTyped ? this.fr.getStringWidth("type...") : this.fr.getStringWidth(Keyboard.getKeyName(element.setting.getKey()))) + 12;
            if (settingWidth < this.fr.getStringWidth(string) + offset) {
               settingWidth = this.fr.getStringWidth(string) + offset;
            }
         }

         if (element.setting.getType() == Setting.Type.TYPE) {
            string = element.setting.getTyped();
            int offset = this.fr.getStringWidth(element.setting.getName()) + 12;
            if (settingWidth < this.fr.getStringWidth(string) + offset) {
               settingWidth = this.fr.getStringWidth(string) + offset;
            }
         }

         if (element.setting.getType() == Setting.Type.SLIDER) {
            string = settingName + "00.00";
            offset = 15;
            if (settingWidth < this.fr.getStringWidth(string) + offset) {
               settingWidth = this.fr.getStringWidth(string) + offset;
            }
         }
      }

      int y = this.y;
      Iterator var18 = this.elements.iterator();

      while(var18.hasNext()) {
         Element element = (Element)var18.next();
         if (element.setting.isVisible() && this.extended) {
            element.updatePosition(this.x + 102, y, settingWidth, this.height);
            element.drawScreen(mouseX, mouseY, partialTicks, offset1);
            y += this.height;
            if (element.setting.getType() == Setting.Type.COMBOBOX && element.extended) {
               y += this.height * element.setting.getModes().length;
            }
         }
      }

   }

   public boolean isHovered(int mouseX, int mouseY) {
      return mouseX > this.x && mouseX < this.x + this.width && mouseY > this.y && mouseY < this.y + this.height;
   }

   public void keyTyped(char typedChar, int keyCode) {
      Iterator var4 = this.elements.iterator();

      while(var4.hasNext()) {
         Element element = (Element)var4.next();
         if (element.setting.isVisible() && this.extended) {
            element.keyTyped(typedChar, keyCode);
         }
      }

   }

   public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
      if (this.isHovered(mouseX, mouseY)) {
         if (mouseButton == 0 && !Keyboard.isKeyDown(42)) {
            this.module.toggle();
         }

         if (mouseButton == 1) {
            this.extended = !this.extended;
         }
      }

      Iterator var5 = this.elements.iterator();

      while(var5.hasNext()) {
         Element element = (Element)var5.next();
         if (element.setting.isVisible() && this.extended) {
            element.mouseClicked(mouseX, mouseY, mouseButton);
         }
      }

   }

   public void mouseReleased(int mouseX, int mouseY, int state) {
      Iterator var5 = this.elements.iterator();

      while(var5.hasNext()) {
         Element element = (Element)var5.next();
         if (element.setting.isVisible() && this.extended) {
            element.mouseReleased(mouseX, mouseY, state);
         }
      }

   }
}
