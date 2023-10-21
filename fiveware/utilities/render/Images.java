package fiveware.utilities.render;

import com.google.common.collect.Maps;
import java.awt.image.BufferedImage;
import java.util.Map;
import net.minecraft.client.renderer.texture.DynamicTexture;
import org.lwjgl.opengl.GL11;

public class Images {
   private static Map<String, DynamicTexture> images = Maps.newHashMap();

   public static void addTexture(String name, BufferedImage image) {
      images.put(name, new DynamicTexture(image));
   }

   public static void bindTexture(String name) {
      GL11.glBindTexture(3553, ((DynamicTexture)images.get(name)).getGlTextureId());
   }
}
