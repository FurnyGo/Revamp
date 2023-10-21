package fiveware.modules.impl.movement;

import fiveware.Fiveware;
import fiveware.events.Event;
import fiveware.events.impl.EventUpdate;
import fiveware.interfaces.Methods;
import fiveware.modules.Category;
import fiveware.modules.Module;
import fiveware.userinterfaces.clickgui.settings.Setting;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;

public class Flight extends Module implements Methods {
   boolean verusDMG;
   public Setting mode = new Setting("Mode", new String[]{"Motion", "Smooth", "AAC33Glide", "DACHigh", "Verus"}, "Motion", this);
   public Setting horizontal = new Setting("Horizontal", 2.5F, 0.0F, 5.0F, false, this);
   public Setting vertical = new Setting("Vertical", 2.5F, 0.0F, 5.0F, false, this);
   public Setting speed = new Setting("Speed", 0.1F, 0.0F, 1.0F, false, this);
   public Setting timer = new Setting("Timer", 0.8F, 0.1F, 2.0F, false, this);
   public Setting fakedmg = new Setting("Fake Damage", false, this);
   public Setting bobbing = new Setting("Bobbing", true, this);

   public Flight() {
      super("Flight", "Flight", "Allows you to fly.", -1, 33, Category.MOVEMENT);
   }

   public void onEnable() {
      if (this.fakedmg.isToggled()) {
         this.getPlayer().performHurtAnimation();
         this.getPlayer().playSound("game.player.hurt", 1.0F, 1.0F);
      }

      if (this.mode.getCurrentMode().equalsIgnoreCase("Verus") && this.isMoving() && this.onGround()) {
         this.sendPacket(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, new ItemStack(Items.water_bucket), 0.0F, 0.5F, 0.0F));
         this.sendPacket(new C08PacketPlayerBlockPlacement(new BlockPos(this.getX(), this.getY() - 1.5D, this.getZ()), 1, new ItemStack(Blocks.stone.getItem(this.getWorld(), new BlockPos(-1, -1, -1))), 0.0F, 0.94F, 0.0F));
         this.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(this.getPlayer().posX, this.getPlayer().posY + 3.001D, this.getPlayer().posZ, false));
         this.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(this.getPlayer().posX, this.getPlayer().posY, this.getPlayer().posZ, false));
         this.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(this.getPlayer().posX, this.getPlayer().posY, this.getPlayer().posZ, true));
         this.verusDMG = true;
      }

      if (this.mode.getCurrentMode().equalsIgnoreCase("DACHigh")) {
         Fiveware.sendChatMessage(false, "Please shoot yourself with a bow / rod.");
      }

   }

   public void onEvent(Event event) {
      if (!this.mode.getCurrentMode().equalsIgnoreCase("DACHigh") && !this.mode.getCurrentMode().equalsIgnoreCase("Verus")) {
         if (!this.mode.getCurrentMode().equalsIgnoreCase("AAC33Glide")) {
            this.horizontal.visible = true;
            this.vertical.visible = true;
            this.speed.visible = false;
            this.timer.visible = false;
         } else {
            this.horizontal.visible = false;
            this.vertical.visible = false;
            this.speed.visible = true;
            this.timer.visible = true;
         }

         this.bobbing.visible = true;
         this.fakedmg.visible = true;
      } else {
         this.horizontal.visible = false;
         this.vertical.visible = false;
         this.speed.visible = false;
         this.timer.visible = false;
         if (this.mode.getCurrentMode().equalsIgnoreCase("Verus")) {
            this.bobbing.visible = true;
            this.fakedmg.visible = true;
         } else {
            this.bobbing.visible = false;
            this.fakedmg.visible = false;
         }
      }

      if (this.isToggled()) {
         if (event instanceof EventUpdate) {
            this.setInfo(this.information() ? "§7" + this.mode.getCurrentMode() + (this.leftSide() ? " §r" : "") : "");
            if (this.bobbing.isToggled() && this.isMoving() && !this.onGround()) {
               this.getPlayer().cameraYaw = 0.1F;
            }

            EntityPlayerSP var10000;
            if (this.mode.getCurrentMode().equalsIgnoreCase("Motion")) {
               if (this.isMoving()) {
                  this.getPlayer().setSpeed(this.horizontal.getCurrentValue() / 2.0F);
               } else {
                  var10000 = this.getPlayer();
                  var10000.motionX /= 2.0D;
                  var10000 = this.getPlayer();
                  var10000.motionZ /= 2.0D;
               }

               if (this.getGameSettings().keyBindJump.isKeyDown() && !this.getGameSettings().keyBindSneak.isKeyDown()) {
                  this.getPlayer().motionY = (double)(this.vertical.getCurrentValue() / 3.0F);
               } else if (this.getGameSettings().keyBindSneak.isKeyDown() && !this.getGameSettings().keyBindJump.isKeyDown()) {
                  this.getPlayer().motionY = (double)(-this.vertical.getCurrentValue() / 3.0F);
               } else {
                  this.getPlayer().motionY = 0.0D;
               }

               this.getCapabilities().setFlySpeed(0.05F);
            }

            if (this.mode.getCurrentMode().equalsIgnoreCase("Smooth")) {
               if (!this.getPlayer().onGround) {
                  this.getCapabilities().isFlying = true;
               }

               if (this.getGameSettings().keyBindJump.isKeyDown() && !this.getGameSettings().keyBindSneak.isKeyDown()) {
                  this.getPlayer().motionY = (double)(this.vertical.getCurrentValue() / 8.0F);
               } else if (this.getGameSettings().keyBindSneak.isKeyDown() && !this.getGameSettings().keyBindJump.isKeyDown()) {
                  this.getPlayer().motionY = (double)(-this.vertical.getCurrentValue() / 8.0F);
               }

               if (this.getGameSettings().keyBindForward.isKeyDown()) {
                  this.getCapabilities().setFlySpeed(this.horizontal.getCurrentValue() / 8.0F);
               }
            }

            if (this.mode.getCurrentMode().equalsIgnoreCase("AAC33Glide") && this.getPlayer().fallDistance >= this.speed.getCurrentValue()) {
               this.getPlayer().motionY = 0.0D;
               this.getTimer().timerSpeed = this.timer.getCurrentValue();
               this.getPlayer().fallDistance = 0.0F;
               this.getPlayer().setSpeed(this.getPlayer().getSpeed());
            }

            if (this.mode.getCurrentMode().equalsIgnoreCase("DACHigh") && this.getPlayer().hurtTime > 2) {
               var10000 = this.getPlayer();
               var10000.motionY += 0.1D;
               this.getPlayer().setSpeed(this.getPlayer().getSpeed());
            }

            if (this.mode.getCurrentMode().equalsIgnoreCase("Verus")) {
               this.getPlayer().onGround = true;
               this.sendPacket(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, new ItemStack(Items.water_bucket), 0.0F, 0.5F, 0.0F));
               this.sendPacket(new C08PacketPlayerBlockPlacement(new BlockPos(this.getX(), this.getY() - 1.5D, this.getZ()), 1, new ItemStack(Blocks.stone.getItem(this.getWorld(), new BlockPos(-1, -1, -1))), 0.0F, 0.94F, 0.0F));
               if (this.verusDMG && this.getHurtTime() != 0) {
                  this.getPlayer().setSpeed(1.0F);
                  this.getTimer().timerSpeed = 0.6F;
               } else {
                  this.getPlayer().setSpeed(this.getPlayer().getSpeed());
                  this.getTimer().timerSpeed = 1.0F;
                  this.verusDMG = false;
               }

               if (this.getGameSettings().keyBindSneak.pressed) {
                  this.getPlayer().motionY = -0.55D;
               } else if (this.getGameSettings().keyBindJump.pressed) {
                  this.getPlayer().motionY = 0.55D;
               } else {
                  this.getPlayer().motionY = 0.0D;
               }
            }
         }

      }
   }

   public void onDisable() {
      this.getTimer().timerSpeed = 1.0F;
      this.getCapabilities().setFlySpeed(0.05F);
      if (this.mode.getCurrentMode().equalsIgnoreCase("Motion")) {
         this.getPlayer().motionX = this.getPlayer().motionZ = 0.0D;
      }

      if (this.mode.getCurrentMode().equalsIgnoreCase("Smooth")) {
         this.getCapabilities().isFlying = false;
      }

   }
}
