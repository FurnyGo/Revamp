package fiveware.utilities.render;

import java.awt.Color;

public class Rainbow {
   public static int rainbow(float seconds, float saturation, float brightness, long index) {
      float hue = (float)((System.currentTimeMillis() + index) % (long)((int)(seconds * 1000.0F))) / (seconds * 1000.0F);
      return Color.HSBtoRGB(hue, saturation, brightness);
   }

   public static Color rainbowColor(float seconds, float saturation, float brightness, long index) {
      float hue = (float)((System.currentTimeMillis() + index) % (long)((int)(seconds * 1000.0F))) / (seconds * 1000.0F);
      return Color.getHSBColor(hue, saturation, brightness);
   }

   public static int mixed(Color color1, Color color2, float offset) {
      double factor = (Math.sin((double)System.currentTimeMillis() / 1.0E8D * 0.75D * 400000.0D + (double)(offset * 100.0F * 2.405F * 0.55F)) + 1.0D) * 0.49000000953674316D;
      factor += (double)System.currentTimeMillis();
      if (factor > 1.0D) {
         double left = factor % 1.0D;
         int off = (int)factor;
         factor = off % 2 == 0 ? left : 1.0D - left;
      }

      return (new Color((int)((double)color1.getRed() * (1.0D - factor) + (double)color2.getRed() * factor), (int)((double)color1.getGreen() * (1.0D - factor) + (double)color2.getGreen() * factor), (int)((double)color1.getBlue() * (1.0D - factor) + (double)color2.getBlue() * factor))).hashCode();
   }

   public static Color mixedColor(Color color1, Color color2, float offset) {
      double factor = (Math.sin((double)System.currentTimeMillis() / 1.0E8D * 0.75D * 400000.0D + (double)(offset * 100.0F * 2.405F * 0.55F)) + 1.0D) * 0.49000000953674316D;
      factor += (double)System.currentTimeMillis();
      double inverse;
      if (factor > 1.0D) {
         inverse = factor % 1.0D;
         int off = (int)factor;
         factor = off % 2 == 0 ? inverse : 1.0D - inverse;
      }

      inverse = 1.0D - factor;
      return new Color((int)((double)color1.getRed() * inverse + (double)color2.getRed() * factor), (int)((double)color1.getGreen() * inverse + (double)color2.getGreen() * factor), (int)((double)color1.getBlue() * inverse + (double)color2.getBlue() * factor));
   }

   public static int astolfo(float offset, float speed) {
      float hue;
      for(hue = (float)(System.currentTimeMillis() % 3000L) + offset; hue > speed; hue -= speed) {
      }

      hue /= speed;
      if ((double)hue > 0.5D) {
         hue = 0.5F - (hue - 0.5F);
      }

      hue += 0.5F;
      return Color.HSBtoRGB(hue, 0.5F, 1.0F);
   }

   public static Color astolfoColor(float offset, float speed) {
      float hue;
      for(hue = (float)(System.currentTimeMillis() % 3000L) + offset; hue > speed; hue -= speed) {
      }

      hue /= speed;
      if ((double)hue > 0.5D) {
         hue = 0.5F - (hue - 0.5F);
      }

      hue += 0.5F;
      return Color.getHSBColor(hue, 0.5F, 1.0F);
   }
}
