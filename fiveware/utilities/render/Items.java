package fiveware.utilities.render;

import fiveware.utilities.Utilities;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class Items extends Utilities {
   public static void renderItem(ItemStack item, int x, int y) {
      GL11.glPushMatrix();
      GL11.glDepthMask(true);
      GlStateManager.clear(256);
      RenderHelper.enableGUIStandardItemLighting();
      mc.getRenderItem().zLevel = -100.0F;
      GlStateManager.scale(1.0F, 1.0F, 0.01F);
      GlStateManager.enableDepth();
      mc.getRenderItem().renderItemAndEffectIntoGUI(item, x, y + 8);
      mc.getRenderItem().zLevel = 0.0F;
      GlStateManager.scale(1.0F, 1.0F, 1.0F);
      RenderHelper.disableStandardItemLighting();
      GlStateManager.disableCull();
      GlStateManager.enableAlpha();
      GlStateManager.disableBlend();
      GlStateManager.disableLighting();
      GlStateManager.scale(0.5D, 0.5D, 0.5D);
      GlStateManager.disableDepth();
      GlStateManager.enableDepth();
      GlStateManager.scale(2.0F, 2.0F, 2.0F);
      GL11.glPopMatrix();
   }
}
