package fiveware.modules.impl.movement;

import fiveware.Fiveware;
import fiveware.events.Event;
import fiveware.events.impl.EventPost;
import fiveware.events.impl.EventPre;
import fiveware.events.impl.EventRender2D;
import fiveware.events.impl.EventUpdate;
import fiveware.interfaces.Methods;
import fiveware.modules.Category;
import fiveware.modules.Module;
import fiveware.userinterfaces.clickgui.settings.Setting;
import fiveware.utilities.render.Rainbow;
import fiveware.utilities.time.Timer;
import net.minecraft.block.BlockAir;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;

public class Scaffold extends Module implements Methods {
   private BlockPos currPos;
   private EnumFacing currFacing;
   private boolean rotated = false;
   private Timer timer = new Timer();
   private double y;
   private float[] rot;
   private int startItem;
   public Setting delay = new Setting("Delay", 0.0F, 0.0F, 5.0F, false, this);
   public Setting sneak = new Setting("Sneak", false, this);
   public Setting count = new Setting("Block Counter", true, this);
   public Setting timerspeed = new Setting("Timer", 1.0F, 0.1F, 10.0F, false, this);
   public Setting rotations = new Setting("Rotations", true, this);
   public Setting keepy = new Setting("Keep Y", true, this);
   public Setting autojump = new Setting("AutoJump", false, this);
   public Setting swing = new Setting("No Swing", false, this);

   public Scaffold() {
      super("Scaffold", "Scaffold", "Places blocks for you.", -1, 0, Category.MOVEMENT);
   }

   public void onEnable() {
      this.y = this.getY();
      this.startItem = this.getPlayer().inventory.currentItem;
   }

   public void onEvent(Event event) {
      if (this.keepy.isToggled()) {
         this.autojump.visible = true;
      } else {
         this.autojump.visible = false;
      }

      if (this.isToggled()) {
         int blockCount;
         if (event instanceof EventUpdate) {
            for(blockCount = 0; blockCount < 9; ++blockCount) {
               ItemStack stack = this.getPlayer().inventory.getStackInSlot(blockCount);
               if (stack != null && stack.getItem() instanceof ItemBlock) {
                  this.getPlayer().inventory.currentItem = blockCount;
               }
            }

            if (this.sneak.isToggled()) {
               if (this.rotated) {
                  this.getGameSettings().keyBindSneak.pressed = true;
               } else {
                  this.getGameSettings().keyBindSneak.pressed = false;
               }
            }
         }

         if (event instanceof EventPre) {
            this.rotated = false;
            this.currPos = null;
            this.currFacing = null;
            if (!this.keepy.isToggled() || !this.isMoving()) {
               this.y = this.getY();
            }

            BlockPos pos = new BlockPos(this.getX(), this.y - 1.0D, this.getZ());
            if (this.getWorld().getBlockState(pos).getBlock() instanceof BlockAir) {
               if (this.getWorld().getBlockState(pos.add(0, -1, 0)).getBlock() != Blocks.air) {
                  this.currPos = pos.add(0, -1, 0);
                  this.currFacing = EnumFacing.UP;
               } else if (this.getWorld().getBlockState(pos.add(-1, 0, 0)).getBlock() != Blocks.air) {
                  this.currPos = pos.add(-1, 0, 0);
                  this.currFacing = EnumFacing.EAST;
               } else if (this.getWorld().getBlockState(pos.add(1, 0, 0)).getBlock() != Blocks.air) {
                  this.currPos = pos.add(1, 0, 0);
                  this.currFacing = EnumFacing.WEST;
               } else if (this.getWorld().getBlockState(pos.add(0, 0, -1)).getBlock() != Blocks.air) {
                  this.currPos = pos.add(0, 0, -1);
                  this.currFacing = EnumFacing.SOUTH;
               } else if (this.getWorld().getBlockState(pos.add(0, 0, 1)).getBlock() != Blocks.air) {
                  this.currPos = pos.add(0, 0, 1);
                  this.currFacing = EnumFacing.NORTH;
               } else {
                  this.currPos = null;
                  this.currFacing = null;
               }

               this.getTimer().timerSpeed = this.timerspeed.getCurrentValue();
               if (this.currPos != null) {
                  this.rot = fiveware.utilities.player.Blocks.getRotations(this.currPos.getX(), this.currPos.getY(), this.currPos.getZ(), this.currFacing);
                  this.rotated = true;
               }
            }

            if (this.rotations.isToggled() && this.rot != null) {
               this.getPlayer().renderYawOffset = this.rot[0];
               this.getPlayer().rotationYawHead = this.rot[0];
               ((EventPre)event).setYaw(this.rot[0]);
               ((EventPre)event).setPitch(this.rot[1]);
            }
         }

         if (event instanceof EventPost && this.currPos != null && this.timer.isDelayComplete((long)(this.delay.getCurrentValue() * 100.0F)) && this.getCurrentEquippedItem() != null && this.getCurrentEquippedItem().getItem() instanceof ItemBlock && this.getPlayerController().onPlayerRightClick(this.getPlayer(), this.getWorld(), this.getCurrentEquippedItem(), this.currPos, this.currFacing, new Vec3((double)this.currPos.getX(), (double)this.currPos.getY(), (double)this.currPos.getZ()))) {
            this.timer.setLastMS();
            if (!this.swing.isToggled()) {
               this.getPlayer().swingItem();
            } else {
               this.sendPacket(new C0APacketAnimation());
            }

            if (this.keepy.isToggled() && this.autojump.isToggled() && this.isWalking() && this.onGround()) {
               this.jump();
            }
         }

         if (event instanceof EventRender2D) {
            blockCount = 0;

            for(int i = 0; i < 9; ++i) {
               ItemStack stack = this.getPlayer().inventory.getStackInSlot(i);
               if (stack != null && stack.getItem() instanceof ItemBlock) {
                  blockCount += stack.stackSize;
               }
            }

            ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
            if (this.count.isToggled()) {
               fr.drawString(blockCount + " §fBlock" + (blockCount == 1 ? "" : "s"), sr.getScaledWidth() / 2 - 20, sr.getScaledHeight() / 2 + 10, Rainbow.mixed(Fiveware.getClientCOLOR(), Fiveware.getClientCOLOR().darker().darker(), 1.0F));
            }
         }

      }
   }

   public void onDisable() {
      this.getPlayer().inventory.currentItem = this.startItem;
      this.getGameSettings().keyBindSneak.pressed = false;
      this.getTimer().timerSpeed = 1.0F;
   }
}
