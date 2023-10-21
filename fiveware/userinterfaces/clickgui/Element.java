package fiveware.userinterfaces.clickgui;

import fiveware.Fiveware;
import fiveware.modules.Module;
import fiveware.userinterfaces.clickgui.settings.Setting;
import fiveware.utilities.render.Rainbow;
import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

public abstract class Element {
   public Minecraft mc = Minecraft.getMinecraft();
   public FontRenderer fr;
   public Setting setting;
   public int x;
   public int y;
   public int width;
   public int height;
   public boolean extended;

   public Element() {
      this.fr = this.mc.fontRendererObj;
   }

   public void updatePosition(int x, int y, int width, int height) {
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
   }

   public abstract void drawScreen(int var1, int var2, float var3, int var4);

   public abstract void keyTyped(char var1, int var2);

   public abstract void mouseClicked(int var1, int var2, int var3);

   public abstract void mouseReleased(int var1, int var2, int var3);

   public static Setting getSetting(Module module, String name) {
      return Fiveware.settingManager.getSetting(module, name);
   }

   public static Module moduleName(String name) {
      return Fiveware.moduleManager.getModuleByName(name);
   }

   public static int rainbowMode(int offset) {
      if (getSetting(moduleName("ClickGUI"), "Rainbow Mode").getCurrentMode().equalsIgnoreCase("Regular")) {
         return Rainbow.rainbow(5.0F, 0.5F, 1.0F, (long)(offset * 200));
      } else {
         return getSetting(moduleName("ClickGUI"), "Rainbow Mode").getCurrentMode().equalsIgnoreCase("Astolfo") ? Rainbow.astolfo((float)(offset * 100), 3000.0F) : Rainbow.mixed(new Color(getMixed(1, "R"), getMixed(1, "G"), getMixed(1, "B")), new Color(getMixed(2, "R"), getMixed(2, "G"), getMixed(2, "B")), (float)offset);
      }
   }

   private static float getMixed(int number, String rgb) {
      return Fiveware.settingManager.getSetting(Fiveware.moduleManager.getModuleByName("Mixed"), "Color " + number + " " + rgb).getCurrentValue();
   }
}
