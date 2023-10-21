package fiveware.userinterfaces.clickgui.elements;

import fiveware.Fiveware;
import fiveware.modules.Category;
import fiveware.modules.Module;
import fiveware.userinterfaces.clickgui.Element;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;

public class DrawCategory {
   public Minecraft mc = Minecraft.getMinecraft();
   public FontRenderer fr;
   public ArrayList<DrawModule> drawModules;
   public Category category;
   public int x;
   public int y;
   public int width;
   public int height;
   public int dragX;
   public int dragY;
   public boolean extended;
   public boolean dragging;

   public DrawCategory(Category category, int x, int y, int width, int height) {
      this.fr = Minecraft.getMinecraft().fontRendererObj;
      this.drawModules = new ArrayList();
      this.category = category;
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
      Fiveware.getINSTANCE();
      Iterator var7 = Fiveware.moduleManager.getModules().iterator();

      while(var7.hasNext()) {
         Module module = (Module)var7.next();
         if (module.getCategory() == category) {
            this.drawModules.add(new DrawModule(module));
         }
      }

   }

   public void drawScreen(int mouseX, int mouseY, float partialTicks, int offset) {
      if (this.dragging) {
         this.x = this.dragX + mouseX;
         this.y = this.dragY + mouseY;
      }

      Gui.drawRect((double)(this.x - 3), (double)this.y, (double)(this.x + 100 + 3), (double)(this.y + this.height), Element.getSetting(Element.moduleName("ClickGUI"), "Rainbow").isToggled() ? Element.rainbowMode(offset) : this.category.getCategoryColor());
      this.fr.drawStringWithShadow(this.category.name().toLowerCase(), (float)this.x, (double)(this.y + this.height / 2 - this.fr.FONT_HEIGHT / 2 + 1), -1);
      this.fr.drawStringWithShadow(this.extended ? "-" : "+", (float)(this.x + 100 - this.fr.getStringWidth(this.extended ? "-" : "+")), (double)(this.y + this.height / 2 - this.fr.FONT_HEIGHT / 2 + 1), -1);
      Iterator var6 = this.drawModules.iterator();

      while(var6.hasNext()) {
         DrawModule drawModule = (DrawModule)var6.next();
         String string = drawModule.module.getName() + (drawModule.elements.isEmpty() ? "" : (drawModule.extended ? " " : " "));
         if (this.width < this.fr.getStringWidth(string) + 22) {
            this.width = this.fr.getStringWidth(string) + 22;
         }
      }

      if (this.extended) {
         int y = this.y + this.height;

         for(Iterator var10 = this.drawModules.iterator(); var10.hasNext(); y += this.height) {
            DrawModule drawModule = (DrawModule)var10.next();
            drawModule.updatePosition(this.x, y, this.width, this.height);
            drawModule.drawScreen(mouseX, mouseY, partialTicks, offset);
         }
      }

   }

   public boolean isHovered(int mouseX, int mouseY) {
      return mouseX >= this.x - 3 && mouseX <= this.x + this.width + 3 && mouseY > this.y && mouseY < this.y + this.height;
   }

   public void keyTyped(char typedChar, int keyCode) {
      if (this.extended) {
         Iterator var4 = this.drawModules.iterator();

         while(var4.hasNext()) {
            DrawModule drawModule = (DrawModule)var4.next();
            drawModule.keyTyped(typedChar, keyCode);
         }
      }

   }

   public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
      if (this.isHovered(mouseX, mouseY)) {
         if (mouseButton == 0) {
            this.dragX = this.x - mouseX;
            this.dragY = this.y - mouseY;
            this.dragging = true;
         }

         if (mouseButton == 1) {
            this.extended = !this.extended;
         }
      }

      if (this.extended) {
         Iterator var5 = this.drawModules.iterator();

         while(var5.hasNext()) {
            DrawModule drawModule = (DrawModule)var5.next();
            drawModule.mouseClicked(mouseX, mouseY, mouseButton);
         }
      }

   }

   public void mouseReleased(int mouseX, int mouseY, int state) {
      this.dragging = false;
      if (this.extended) {
         Iterator var5 = this.drawModules.iterator();

         while(var5.hasNext()) {
            DrawModule drawModule = (DrawModule)var5.next();
            drawModule.mouseReleased(mouseX, mouseY, state);
         }
      }

   }
}
