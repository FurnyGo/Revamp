package fiveware.utilities;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;

public class Utilities {
   protected static Minecraft mc = Minecraft.getMinecraft();

   protected static EntityPlayerSP getPlayer() {
      return mc.thePlayer;
   }

   protected static WorldClient getWorld() {
      return mc.theWorld;
   }
}
