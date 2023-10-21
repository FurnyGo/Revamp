package fiveware.modules.impl.render;

import fiveware.Fiveware;
import fiveware.events.Event;
import fiveware.events.impl.EventRender3D;
import fiveware.interfaces.Methods;
import fiveware.modules.Category;
import fiveware.modules.Module;
import fiveware.userinterfaces.clickgui.settings.Setting;
import fiveware.utilities.render.Rainbow;
import java.awt.Color;
import java.util.Iterator;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.opengl.GL11;

public class ESP extends Module implements Methods {
   public Setting mode = new Setting("Mode", new String[]{"2D", "3D", "Chams", "Shader"}, "3D", this);
   public Setting bar = new Setting("2D Health Bar", true, this);
   public Setting red = new Setting("Red", 0.5F, 0.0F, 1.0F, false, this);
   public Setting green = new Setting("Green", 0.5F, 0.0F, 1.0F, false, this);
   public Setting blue = new Setting("Blue", 0.5F, 0.0F, 1.0F, false, this);
   public Setting rainbow = new Setting("Rainbow", false, this);
   public Setting rmode = new Setting("Rainbow Mode", new String[]{"Regular", "Mixed", "Astolfo"}, "Regular", this);
   public Setting width = new Setting("Line Width", 2.0F, 1.0F, 5.0F, false, this);

   public ESP() {
      super("ESP", "ESP", "Renders a box on players.", -1, 0, Category.RENDER);
   }

   public void onEvent(Event event) {
      if (!this.mode.getCurrentMode().equalsIgnoreCase("Shader") && !this.mode.getCurrentMode().equalsIgnoreCase("Chams")) {
         if (this.mode.getCurrentMode().equalsIgnoreCase("2D")) {
            this.bar.visible = true;
         } else {
            this.bar.visible = false;
         }

         if (this.mode.getCurrentMode().equalsIgnoreCase("3D")) {
            this.width.visible = true;
         } else {
            this.width.visible = false;
         }

         this.rainbow.visible = true;
         if (this.rainbow.isToggled()) {
            this.rmode.visible = true;
            this.red.visible = false;
            this.green.visible = false;
            this.blue.visible = false;
         } else {
            this.rmode.visible = false;
            this.red.visible = true;
            this.green.visible = true;
            this.blue.visible = true;
         }
      } else {
         this.bar.visible = false;
         this.red.visible = false;
         this.green.visible = false;
         this.blue.visible = false;
         this.rainbow.visible = false;
         this.rmode.visible = false;
         this.width.visible = false;
      }

      if (this.isToggled()) {
         if (event instanceof EventRender3D) {
            this.setInfo(this.information() ? "§7" + this.mode.getCurrentMode() + (this.leftSide() ? " §r" : "") : "");
            Iterator var3 = this.getWorld().loadedEntityList.iterator();

            while(var3.hasNext()) {
               Object o = var3.next();
               if (o instanceof EntityPlayer && o != this.getPlayer()) {
                  EntityPlayer e = (EntityPlayer)o;
                  Color espColor = this.rainbow.isToggled() ? new Color(this.rainbowMode(false).getRed(), this.rainbowMode(false).getGreen(), this.rainbowMode(false).getBlue()) : new Color(this.red.getCurrentValue(), this.green.getCurrentValue(), this.blue.getCurrentValue());
                  double x;
                  double y;
                  double z;
                  double var10000;
                  if (this.mode.getCurrentMode().equalsIgnoreCase("2D")) {
                     var10000 = e.lastTickPosX + (e.posX - e.lastTickPosX) * (double)this.getTimer().renderPartialTicks;
                     this.getRenderManager();
                     x = var10000 - RenderManager.renderPosX;
                     var10000 = e.lastTickPosY + (e.posY - e.lastTickPosY) * (double)this.getTimer().renderPartialTicks;
                     this.getRenderManager();
                     y = var10000 - RenderManager.renderPosY;
                     var10000 = e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * (double)this.getTimer().renderPartialTicks;
                     this.getRenderManager();
                     z = var10000 - RenderManager.renderPosZ;
                     int b = (int)(74.0F * (e.getHealth() / e.getMaxHealth()));
                     GL11.glPushMatrix();
                     GL11.glTranslated(x, y - 0.15D, z);
                     GL11.glScalef(0.03F, 0.03F, 0.03F);
                     GL11.glRotated((double)(-this.getRenderManager().playerViewY), 0.0D, 1.0D, 0.0D);
                     GlStateManager.disableDepth();
                     GlStateManager.disableBlend();
                     Gui.drawRect(-20.0D, -1.0D, -26.0D, 75.0D, Color.BLACK.hashCode());
                     Gui.drawRect(-21.0D, 0.0D, -25.0D, 74.0D, espColor.getRGB());
                     Gui.drawRect(20.0D, -1.0D, 26.0D, 75.0D, Color.BLACK.hashCode());
                     Gui.drawRect(21.0D, 0.0D, 25.0D, 74.0D, espColor.getRGB());
                     Gui.drawRect(-20.0D, -1.0D, 21.0D, 5.0D, Color.BLACK.hashCode());
                     Gui.drawRect(-21.0D, 0.0D, 24.0D, 4.0D, espColor.getRGB());
                     Gui.drawRect(-20.0D, 70.0D, 21.0D, 75.0D, Color.BLACK.hashCode());
                     Gui.drawRect(-21.0D, 71.0D, 25.0D, 74.0D, espColor.getRGB());
                     if (this.bar.isToggled()) {
                        Gui.drawRect(31.0D, -1.0D, 27.0D, 75.0D, Color.black.getRGB());
                        Gui.drawRect(30.0D, 0.0D, 28.0D, (double)b, this.rainbow.isToggled() ? (new Color(this.rainbowMode(true).getRed(), this.rainbowMode(true).getGreen(), this.rainbowMode(true).getBlue())).getRGB() : (new Color(this.red.getCurrentValue(), this.green.getCurrentValue(), this.blue.getCurrentValue())).getRGB());
                     }

                     GlStateManager.enableBlend();
                     GlStateManager.enableDepth();
                     GL11.glPopMatrix();
                  } else if (this.mode.getCurrentMode().equalsIgnoreCase("3D")) {
                     GL11.glBlendFunc(770, 771);
                     GL11.glEnable(3042);
                     GL11.glLineWidth(this.width.getCurrentValue());
                     GL11.glDisable(3553);
                     GL11.glDisable(2929);
                     GL11.glDepthMask(false);
                     x = e.lastTickPosX + (e.posX - e.lastTickPosX) * (double)this.getTimer().renderPartialTicks;
                     y = e.lastTickPosY + (e.posY - e.lastTickPosY) * (double)this.getTimer().renderPartialTicks;
                     z = e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * (double)this.getTimer().renderPartialTicks;
                     float yaw = e.prevRotationYaw + (e.rotationYaw - e.prevRotationYaw) * this.getTimer().renderPartialTicks;
                     this.getRenderManager();
                     var10000 = x - RenderManager.renderPosX;
                     this.getRenderManager();
                     double var10001 = y - RenderManager.renderPosY;
                     this.getRenderManager();
                     GlStateManager.translate(var10000, var10001, z - RenderManager.renderPosZ);
                     GlStateManager.rotate(-yaw, 0.0F, 1.0F, 0.0F);
                     this.getRenderManager();
                     var10000 = -(x - RenderManager.renderPosX);
                     this.getRenderManager();
                     var10001 = -(y - RenderManager.renderPosY);
                     this.getRenderManager();
                     GlStateManager.translate(var10000, var10001, -(z - RenderManager.renderPosZ));
                     GL11.glEnable(2848);
                     GlStateManager.color((float)espColor.getRed() / 255.0F, (float)espColor.getGreen() / 255.0F, (float)espColor.getBlue() / 255.0F);
                     Module module = Fiveware.moduleManager.getModuleByName("Hitboxes");
                     double var10002 = x - (double)(e.width / 2.0F) - 0.05D - x;
                     this.getRenderManager();
                     var10002 = var10002 + (x - RenderManager.renderPosX) - (double)(module.isToggled() ? Fiveware.settingManager.getSetting(module, "Hitbox").getCurrentValue() : 0.0F);
                     double var10003 = y - y;
                     this.getRenderManager();
                     var10003 = var10003 + (y - RenderManager.renderPosY) - (double)(module.isToggled() ? Fiveware.settingManager.getSetting(module, "Hitbox").getCurrentValue() : 0.0F);
                     double var10004 = z - (double)(e.width / 2.0F) - 0.05D - z;
                     this.getRenderManager();
                     var10004 = var10004 + (z - RenderManager.renderPosZ) - (double)(module.isToggled() ? Fiveware.settingManager.getSetting(module, "Hitbox").getCurrentValue() : 0.0F);
                     double var10005 = x + (double)(e.width / 2.0F) + 0.05D - x;
                     this.getRenderManager();
                     var10005 = var10005 + (x - RenderManager.renderPosX) + (double)(module.isToggled() ? Fiveware.settingManager.getSetting(module, "Hitbox").getCurrentValue() : 0.0F);
                     double var10006 = y + (double)e.height + 0.1D - y;
                     this.getRenderManager();
                     var10006 = var10006 + (y - RenderManager.renderPosY) + (double)(module.isToggled() ? Fiveware.settingManager.getSetting(module, "Hitbox").getCurrentValue() : 0.0F);
                     double var10007 = z + (double)(e.width / 2.0F) + 0.05D - z;
                     this.getRenderManager();
                     RenderGlobal.func_181561_a(new AxisAlignedBB(var10002, var10003, var10004, var10005, var10006, var10007 + (z - RenderManager.renderPosZ) + (double)(module.isToggled() ? Fiveware.settingManager.getSetting(module, "Hitbox").getCurrentValue() : 0.0F)));
                     this.getRenderManager();
                     var10000 = x - RenderManager.renderPosX;
                     this.getRenderManager();
                     var10001 = y - RenderManager.renderPosY;
                     this.getRenderManager();
                     GlStateManager.translate(var10000, var10001, z - RenderManager.renderPosZ);
                     GlStateManager.rotate(yaw, 0.0F, 1.0F, 0.0F);
                     this.getRenderManager();
                     var10000 = -(x - RenderManager.renderPosX);
                     this.getRenderManager();
                     var10001 = -(y - RenderManager.renderPosY);
                     this.getRenderManager();
                     GlStateManager.translate(var10000, var10001, -(z - RenderManager.renderPosZ));
                     GL11.glEnable(3553);
                     GL11.glEnable(2929);
                     GL11.glDepthMask(true);
                     GL11.glDisable(3042);
                  }
               }
            }
         }

      }
   }

   public Color rainbowMode(boolean espModeIs2DandBarIsEnabled) {
      if (this.rmode.getCurrentMode().equalsIgnoreCase("Regular")) {
         return Rainbow.rainbowColor(5.0F, 0.5F, 1.0F, (long)(espModeIs2DandBarIsEnabled ? 300 : 200));
      } else {
         return this.rmode.getCurrentMode().equalsIgnoreCase("Astolfo") ? Rainbow.astolfoColor((float)(espModeIs2DandBarIsEnabled ? 200 : 100), 3000.0F) : Rainbow.mixedColor(new Color(this.getMixed(1, "R"), this.getMixed(1, "G"), this.getMixed(1, "B")), new Color(this.getMixed(2, "R"), this.getMixed(2, "G"), this.getMixed(2, "B")), (float)(espModeIs2DandBarIsEnabled ? 2 : 1));
      }
   }

   private float getMixed(int number, String rgb) {
      return Fiveware.settingManager.getSetting(Fiveware.moduleManager.getModuleByName("Mixed"), "Color " + number + " " + rgb).getCurrentValue();
   }
}
