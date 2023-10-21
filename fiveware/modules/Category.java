package fiveware.modules;

import java.awt.Color;

public enum Category {
   COMBAT("Combat"),
   MOVEMENT("Movement"),
   RENDER("Render"),
   PLAYER("Player"),
   WORLD("World"),
   OTHER("Other"),
   TARGETS("Targets");

   public String name;
   private int index;
   // $FF: synthetic field
   private static volatile int[] $SWITCH_TABLE$fiveware$modules$Category;

   public int getCategoryColor() {
      switch($SWITCH_TABLE$fiveware$modules$Category()[this.ordinal()]) {
      case 1:
         return Color.RED.darker().getRGB();
      case 2:
         return Color.GREEN.getRGB();
      case 3:
         return Color.CYAN.getRGB();
      case 4:
         return Color.MAGENTA.darker().getRGB();
      case 5:
         return Color.YELLOW.getRGB();
      case 6:
         return Color.LIGHT_GRAY.getRGB();
      case 7:
         return Color.RED.getRGB();
      default:
         return 16777215;
      }
   }

   private Category(String name) {
      this.name = name;
   }

   // $FF: synthetic method
   static int[] $SWITCH_TABLE$fiveware$modules$Category() {
      int[] var10000 = $SWITCH_TABLE$fiveware$modules$Category;
      if (var10000 != null) {
         return var10000;
      } else {
         int[] var0 = new int[values().length];

         try {
            var0[COMBAT.ordinal()] = 1;
         } catch (NoSuchFieldError var7) {
         }

         try {
            var0[MOVEMENT.ordinal()] = 2;
         } catch (NoSuchFieldError var6) {
         }

         try {
            var0[OTHER.ordinal()] = 6;
         } catch (NoSuchFieldError var5) {
         }

         try {
            var0[PLAYER.ordinal()] = 4;
         } catch (NoSuchFieldError var4) {
         }

         try {
            var0[RENDER.ordinal()] = 3;
         } catch (NoSuchFieldError var3) {
         }

         try {
            var0[TARGETS.ordinal()] = 7;
         } catch (NoSuchFieldError var2) {
         }

         try {
            var0[WORLD.ordinal()] = 5;
         } catch (NoSuchFieldError var1) {
         }

         $SWITCH_TABLE$fiveware$modules$Category = var0;
         return var0;
      }
   }
}
