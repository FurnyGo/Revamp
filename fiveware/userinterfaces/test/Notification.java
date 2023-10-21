package fiveware.userinterfaces.test;

import fiveware.utilities.render.Rainbow;
import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumChatFormatting;

public class Notification {
   private Notification.NotificationType type;
   private String title;
   private String messsage;
   private long start;
   private long fadedIn;
   private long fadeOut;
   private long end;

   public Notification(Notification.NotificationType type, String title, String messsage, int length) {
      this.type = type;
      this.title = title;
      this.messsage = messsage;
      this.fadedIn = (long)(200 * length);
      this.fadeOut = this.fadedIn + (long)(500 * length);
      this.end = this.fadeOut + this.fadedIn;
   }

   public void show() {
      this.start = System.currentTimeMillis();
   }

   public boolean isShown() {
      return this.getTime() <= this.end;
   }

   private long getTime() {
      return System.currentTimeMillis() - this.start;
   }

   public void render() {
      double offset = 0.0D;
      int width = 120;
      int height = 30;
      long time = this.getTime();
      if (time < this.fadedIn) {
         offset = Math.tanh((double)time / (double)this.fadedIn * 3.0D) * (double)width;
      } else if (time > this.fadeOut) {
         offset = Math.tanh(3.0D - (double)(time - this.fadeOut) / (double)(this.end - this.fadeOut) * 3.0D) * (double)width;
      } else {
         offset = (double)width;
      }

      Color color;
      Color color1;
      if (this.type == Notification.NotificationType.INFO) {
         color = Rainbow.mixedColor(Color.blue, Color.black, 100.0F);
         color1 = Rainbow.mixedColor(Color.blue, Color.black, 200.0F);
      } else if (this.type == Notification.NotificationType.WARNING) {
         color = Rainbow.mixedColor(Color.yellow, Color.black, 100.0F);
         color1 = Rainbow.mixedColor(Color.yellow, Color.black, 200.0F);
      } else {
         color = Rainbow.mixedColor(Color.red, Color.black, 100.0F);
         color1 = Rainbow.mixedColor(Color.red, Color.black, 200.0F);
      }

      Minecraft mc = Minecraft.getMinecraft();
      FontRenderer fr = mc.fontRendererObj;
      ScaledResolution sr = new ScaledResolution(mc);
      String text = EnumChatFormatting.BOLD + this.title + " " + EnumChatFormatting.RESET + this.messsage;
      int x = sr.getScaledWidth() / 2 - 60;
      int y = -65;
      drawRect((double)(sr.getScaledWidth() - 105 - x), (double)(sr.getScaledHeight() + 10 - height - (int)offset - y), (double)(sr.getScaledWidth() - 96 + fr.getStringWidth(text) - x), (double)(sr.getScaledHeight() - 3 - (int)offset - y), color.getRGB());
      drawRect((double)(sr.getScaledWidth() - 103 - x), (double)(sr.getScaledHeight() + 12 - height - (int)offset - y), (double)(sr.getScaledWidth() - 98 + fr.getStringWidth(text) - x), (double)(sr.getScaledHeight() - 5 - (int)offset - y), color1.getRGB());
      fr.drawString(text, sr.getScaledWidth() - 100 - x, sr.getScaledHeight() - 15 - (int)offset - y, -1);
   }

   public static void drawRect(double left, double top, double right, double bottom, int color) {
      double j;
      if (left < right) {
         j = left;
         left = right;
         right = j;
      }

      if (top < bottom) {
         j = top;
         top = bottom;
         bottom = j;
      }

      float f3 = (float)(color >> 24 & 255) / 255.0F;
      float f = (float)(color >> 16 & 255) / 255.0F;
      float f1 = (float)(color >> 8 & 255) / 255.0F;
      float f2 = (float)(color & 255) / 255.0F;
      Tessellator tessellator = Tessellator.getInstance();
      WorldRenderer worldrenderer = tessellator.getWorldRenderer();
      GlStateManager.enableBlend();
      GlStateManager.disableTexture2D();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GlStateManager.color(f, f1, f2, f3);
      worldrenderer.begin(7, DefaultVertexFormats.POSITION);
      worldrenderer.pos(left, bottom, 0.0D).endVertex();
      worldrenderer.pos(right, bottom, 0.0D).endVertex();
      worldrenderer.pos(right, top, 0.0D).endVertex();
      worldrenderer.pos(left, top, 0.0D).endVertex();
      tessellator.draw();
      GlStateManager.enableTexture2D();
      GlStateManager.disableBlend();
   }

   public static void drawRect(int mode, double left, double top, double right, double bottom, int color) {
      double j;
      if (left < right) {
         j = left;
         left = right;
         right = j;
      }

      if (top < bottom) {
         j = top;
         top = bottom;
         bottom = j;
      }

      float f3 = (float)(color >> 24 & 255) / 255.0F;
      float f = (float)(color >> 16 & 255) / 255.0F;
      float f1 = (float)(color >> 8 & 255) / 255.0F;
      float f2 = (float)(color & 255) / 255.0F;
      Tessellator tessellator = Tessellator.getInstance();
      WorldRenderer worldrenderer = tessellator.getWorldRenderer();
      GlStateManager.enableBlend();
      GlStateManager.disableTexture2D();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GlStateManager.color(f, f1, f2, f3);
      worldrenderer.begin(mode, DefaultVertexFormats.POSITION);
      worldrenderer.pos(left, bottom, 0.0D).endVertex();
      worldrenderer.pos(right, bottom, 0.0D).endVertex();
      worldrenderer.pos(right, top, 0.0D).endVertex();
      worldrenderer.pos(left, top, 0.0D).endVertex();
      tessellator.draw();
      GlStateManager.enableTexture2D();
      GlStateManager.disableBlend();
   }

   public static enum NotificationType {
      INFO,
      WARNING,
      ERROR;
   }
}
