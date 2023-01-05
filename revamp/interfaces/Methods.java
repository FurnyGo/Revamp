// 
// Decompiled by Procyon v0.5.36
// 

package revamp.interfaces;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.util.EnumFacing;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.renderer.EntityRenderer;
import revamp.Revamp;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.C03PacketPlayer;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.Session;
import net.minecraft.util.Timer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.inventory.Container;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.network.Packet;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.Minecraft;

public interface Methods
{
    public static final Minecraft mc = Minecraft.getMinecraft();
    public static final FontRenderer fr = Methods.mc.fontRendererObj;
    
    default EntityPlayerSP getPlayer() {
        return Methods.mc.thePlayer;
    }
    
    default void sendPacket(final Packet<?> packet) {
        this.getPlayer().sendQueue.addToSendQueue(packet);
    }
    
    default PlayerControllerMP getPlayerController() {
        return Methods.mc.playerController;
    }
    
    default WorldClient getWorld() {
        return Methods.mc.theWorld;
    }
    
    default GameSettings getGameSettings() {
        return Methods.mc.gameSettings;
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
        return this.getCurrentEquippedItem().getItem() instanceof ItemSword;
    }
    
    default void setRightClickDelay(final int delay) {
        Methods.mc.rightClickDelayTimer = delay;
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
        return Methods.mc.getRenderManager();
    }
    
    default Timer getTimer() {
        return Methods.mc.timer;
    }
    
    default int getHurtTime() {
        return Methods.mc.thePlayer.hurtTime;
    }
    
    default Session getSession() {
        return Methods.mc.getSession();
    }
    
    default String getUsername() {
        return this.getSession().getUsername();
    }
    
    default GuiScreen getCurrentScreen() {
        return Methods.mc.currentScreen;
    }
    
    default void displayScreen(final GuiScreen gui) {
        Methods.mc.displayGuiScreen(gui);
    }
    
    default MovingObjectPosition getObjectMouseOver() {
        return Methods.mc.objectMouseOver;
    }
    
    default boolean isOutsideBorder() {
        return Methods.mc.thePlayer.isOutsideBorder();
    }
    
    default boolean isMoving() {
        return this.getPlayer().moveForward != 0.0f || this.getPlayer().moveStrafing != 0.0f;
    }
    
    default double getDirection() {
        return Math.toRadians(this.getPlayer().rotationYaw);
    }
    
    default void setMotion(final double motion) {
        final EntityPlayerSP player = this.getPlayer();
        final EntityPlayerSP player2 = this.getPlayer();
        final double n = 0.0;
        player2.motionZ = n;
        player.motionX = n;
    }
    
    default void pushPlayer(final double push) {
        final double x = -Math.sin(this.getDirection());
        final double z = Math.cos(this.getDirection());
        Methods.mc.thePlayer.motionX = x * push;
        Methods.mc.thePlayer.motionZ = z * push;
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
    
    default boolean isKeyDown(final int keyCode) {
        if (keyCode < 0) {
            final int i = Mouse.getEventButton();
            return i - 100 == keyCode;
        }
        return Keyboard.isKeyDown(keyCode);
    }
    
    default void setPosition(final double x, final double y, final double z, final boolean ground) {
        this.getPlayer().setPosition(x, y, z);
        this.getPlayer().sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, y, z, ground));
    }
    
    default void changePosition(final double x, final double y, final double z) {
        Methods.mc.thePlayer.setPosition(Methods.mc.thePlayer.posX + x, Methods.mc.thePlayer.posY + y, Methods.mc.thePlayer.posZ + z);
        this.getPlayer().sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Methods.mc.thePlayer.posX + x, Methods.mc.thePlayer.posY + y, Methods.mc.thePlayer.posZ + z, this.getPlayer().onGround));
    }
    
    default void stopPlayer() {
        Methods.mc.thePlayer.motionX = 0.0;
        Methods.mc.thePlayer.motionZ = 0.0;
    }
    
    default boolean onGround() {
        return this.getPlayer().onGround;
    }
    
    default boolean validEntityName(final Entity entity) {
        if (!(entity instanceof EntityPlayer)) {
            return true;
        }
        final String name = entity.getName();
        return name.length() >= 3 && name.length() <= 16 && !name.contains("^") && !name.contains("°") && !name.contains("!") && !name.contains("§") && !name.contains("$") && !name.contains("%") && !name.contains("&") && !name.contains("/") && !name.contains("(") && !name.contains(")") && !name.contains("=") && !name.contains("{") && !name.contains("}") && !name.contains("[") && !name.contains("]") && !name.contains("\u00df") && !name.contains("?") && !name.contains("-") && !name.contains(".") && !name.contains(":") && !name.contains(",") && !name.contains("+") && !name.contains("*") && !name.contains("~") && !name.contains("#") && !name.contains(";") && !name.contains("'");
    }
    
    default boolean isInLiquid() {
        return Methods.mc.thePlayer.isInLava() || Methods.mc.thePlayer.isInWater();
    }
    
    default boolean hurtTimeIsnt9() {
        return this.getHurtTime() == 1 || this.getHurtTime() == 2 || this.getHurtTime() == 3 || this.getHurtTime() == 4 || this.getHurtTime() == 5 || this.getHurtTime() == 6 || this.getHurtTime() == 7 || this.getHurtTime() == 8;
    }
    
    default boolean isIngame() {
        return this.getPlayer() != null && this.getWorld() != null;
    }
    
    default void loadRenderers() {
        Methods.mc.renderGlobal.loadRenderers();
    }
    
    default boolean information() {
        return Revamp.settingManager.getSetting(Revamp.moduleManager.getModuleByName("HUD"), "Show Information").isToggled();
    }
    
    default void jump() {
        this.getPlayer().jump();
    }
    
    default EntityRenderer getRenderer() {
        return Methods.mc.entityRenderer;
    }
    
    default NetHandlerPlayClient getNetHandler() {
        return Methods.mc.getNetHandler();
    }
    
    default boolean isCollided() {
        return this.getPlayer().isCollidedHorizontally && this.getPlayer().isCollidedVertically;
    }
    
    default ServerData getServerData() {
        return Methods.mc.getCurrentServerData();
    }
    
    default void placeBlock(final BlockPos pos, final EnumFacing facing) {
        this.getPlayerController().onPlayerRightClick(this.getPlayer(), this.getWorld(), this.getPlayer().getHeldItem(), pos, facing, new Vec3(0.5, 0.5, 0.5));
    }
    
    default boolean isWalking() {
        return this.getGameSettings().keyBindForward.pressed || this.getGameSettings().keyBindBack.pressed || this.getGameSettings().keyBindLeft.pressed || this.getGameSettings().keyBindRight.pressed;
    }
    
    default void setCape(final ResourceLocation cape) {
        this.getPlayer().setLocationOfCape(cape);
    }
}
