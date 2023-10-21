package fiveware.interfaces;

import fiveware.Fiveware;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Session;
import net.minecraft.util.Timer;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public interface Methods {
   Minecraft mc = Minecraft.getMinecraft();
   FontRenderer fr = mc.fontRendererObj;

   default EntityPlayerSP getPlayer() {
      return mc.thePlayer;
   }

   default void sendPacket(Packet<?> packet) {
      this.getPlayer().sendQueue.addToSendQueue(packet);
   }

   default PlayerControllerMP getPlayerController() {
      return mc.playerController;
   }

   default WorldClient getWorld() {
      return mc.theWorld;
   }

   default GameSettings getGameSettings() {
      return mc.gameSettings;
   }

   default boolean isOnLadder() {
      return this.getPlayer().isOnLadder();
   }

   default BlockPos getPosition() {
      return this.getPlayer().getPosition();
   }

   default ItemStack getCurrentEquippedItem() {
      return this.getPlayer().getCurrentEquippedItem();
   }

   default boolean holdingSword() {
      return this.getCurrentEquippedItem() != null && this.getCurrentEquippedItem().getItem() instanceof ItemSword;
   }

   default void setRightClickDelay(int delay) {
      mc.rightClickDelayTimer = delay;
   }

   default double getX() {
      return this.getPlayer().posX;
   }

   default double getY() {
      return this.getPlayer().posY;
   }

   default double getZ() {
      return this.getPlayer().posZ;
   }

   default float getYaw() {
      return this.getPlayer().renderYawOffset;
   }

   default float getPitch() {
      return this.getPlayer().rotationPitch;
   }

   default PlayerCapabilities getCapabilities() {
      return this.getPlayer().capabilities;
   }

   default Container getContainer() {
      return this.getPlayer().openContainer;
   }

   default RenderManager getRenderManager() {
      return mc.getRenderManager();
   }

   default TextureManager getTextureManager() {
      return mc.getTextureManager();
   }

   default Timer getTimer() {
      return mc.timer;
   }

   default int getHurtTime() {
      return mc.thePlayer.hurtTime;
   }

   default Session getSession() {
      return mc.getSession();
   }

   default String getUsername() {
      return this.getSession().getUsername();
   }

   default GuiScreen getCurrentScreen() {
      return mc.currentScreen;
   }

   default void displayScreen(GuiScreen gui) {
      mc.displayGuiScreen(gui);
   }

   default MovingObjectPosition getObjectMouseOver() {
      return mc.objectMouseOver;
   }

   default boolean isOutsideBorder() {
      return mc.thePlayer.isOutsideBorder();
   }

   default boolean isMoving() {
      return this.getPlayer().moveForward != 0.0F || this.getPlayer().moveStrafing != 0.0F;
   }

   default double getDirection() {
      return Math.toRadians((double)this.getPlayer().rotationYaw);
   }

   default void setMotion(double motion) {
      this.getPlayer().motionX = this.getPlayer().motionZ = 0.0D;
   }

   default void pushPlayer(double push) {
      double x = -Math.sin(this.getDirection());
      double z = Math.cos(this.getDirection());
      mc.thePlayer.motionX = x * push;
      mc.thePlayer.motionZ = z * push;
   }

   default void resumeWalk() {
      this.getGameSettings().keyBindForward.pressed = this.isKeyDown(this.getGameSettings().keyBindForward.getKeyCode());
      this.getGameSettings().keyBindBack.pressed = this.isKeyDown(this.getGameSettings().keyBindBack.getKeyCode());
      this.getGameSettings().keyBindLeft.pressed = this.isKeyDown(this.getGameSettings().keyBindLeft.getKeyCode());
      this.getGameSettings().keyBindRight.pressed = this.isKeyDown(this.getGameSettings().keyBindRight.getKeyCode());
   }

   default void stopWalk() {
      this.getGameSettings().keyBindForward.pressed = false;
      this.getGameSettings().keyBindBack.pressed = false;
      this.getGameSettings().keyBindLeft.pressed = false;
      this.getGameSettings().keyBindRight.pressed = false;
   }

   default boolean isKeyDown(int keyCode) {
      if (keyCode < 0) {
         int i = Mouse.getEventButton();
         return i - 100 == keyCode;
      } else {
         return Keyboard.isKeyDown(keyCode);
      }
   }

   default void setPosition(double x, double y, double z, boolean ground) {
      this.getPlayer().setPosition(x, y, z);
      this.getPlayer().sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, y, z, ground));
   }

   default void changePosition(double x, double y, double z) {
      mc.thePlayer.setPosition(mc.thePlayer.posX + x, mc.thePlayer.posY + y, mc.thePlayer.posZ + z);
      double var10003 = mc.thePlayer.posX + x;
      this.getPlayer().sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(var10003, mc.thePlayer.posY + y, mc.thePlayer.posZ + z, this.getPlayer().onGround));
   }

   default void stopPlayer() {
      mc.thePlayer.motionX = 0.0D;
      mc.thePlayer.motionZ = 0.0D;
   }

   default boolean onGround() {
      return this.getPlayer().onGround;
   }

   default boolean validEntityName(Entity entity) {
      if (!(entity instanceof EntityPlayer)) {
         return true;
      } else {
         String name = entity.getName();
         if (name.length() >= 3 && name.length() <= 16) {
            if (name.contains("^")) {
               return false;
            } else if (name.contains("°")) {
               return false;
            } else if (name.contains("!")) {
               return false;
            } else if (name.contains("§")) {
               return false;
            } else if (name.contains("$")) {
               return false;
            } else if (name.contains("%")) {
               return false;
            } else if (name.contains("&")) {
               return false;
            } else if (name.contains("/")) {
               return false;
            } else if (name.contains("(")) {
               return false;
            } else if (name.contains(")")) {
               return false;
            } else if (name.contains("=")) {
               return false;
            } else if (name.contains("{")) {
               return false;
            } else if (name.contains("}")) {
               return false;
            } else if (name.contains("[")) {
               return false;
            } else if (name.contains("]")) {
               return false;
            } else if (name.contains("ß")) {
               return false;
            } else if (name.contains("?")) {
               return false;
            } else if (name.contains("-")) {
               return false;
            } else if (name.contains(".")) {
               return false;
            } else if (name.contains(":")) {
               return false;
            } else if (name.contains(",")) {
               return false;
            } else if (name.contains("+")) {
               return false;
            } else if (name.contains("*")) {
               return false;
            } else if (name.contains("~")) {
               return false;
            } else if (name.contains("#")) {
               return false;
            } else if (name.contains(";")) {
               return false;
            } else {
               return !name.contains("'");
            }
         } else {
            return false;
         }
      }
   }

   default boolean isInLiquid() {
      return mc.thePlayer.isInLava() || mc.thePlayer.isInWater();
   }

   default boolean hurtTimeIsnt9() {
      return this.getHurtTime() == 1 || this.getHurtTime() == 2 || this.getHurtTime() == 3 || this.getHurtTime() == 4 || this.getHurtTime() == 5 || this.getHurtTime() == 6 || this.getHurtTime() == 7 || this.getHurtTime() == 8;
   }

   default boolean isIngame() {
      return this.getPlayer() != null && this.getWorld() != null;
   }

   default void loadRenderers() {
      mc.renderGlobal.loadRenderers();
   }

   default boolean information() {
      return Fiveware.settingManager.getSetting(Fiveware.moduleManager.getModuleByName("HUD"), "Show Information").isToggled();
   }

   default boolean leftSide() {
      return Fiveware.settingManager.getSetting(Fiveware.moduleManager.getModuleByName("HUD"), "Arraylist Align").getCurrentMode().equalsIgnoreCase("Left");
   }

   default void jump() {
      this.getPlayer().jump();
   }

   default EntityRenderer getRenderer() {
      return mc.entityRenderer;
   }

   default NetHandlerPlayClient getNetHandler() {
      return mc.getNetHandler();
   }

   default boolean isCollided() {
      return this.getPlayer().isCollidedHorizontally && this.getPlayer().isCollidedVertically;
   }

   default ServerData getServerData() {
      return mc.getCurrentServerData();
   }

   default boolean isWalking() {
      return this.getGameSettings().keyBindForward.pressed || this.getGameSettings().keyBindBack.pressed || this.getGameSettings().keyBindLeft.pressed || this.getGameSettings().keyBindRight.pressed;
   }

   default void setCape(ResourceLocation cape) {
      this.getPlayer().setLocationOfCape(cape);
   }
}
