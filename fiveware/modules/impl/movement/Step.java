package fiveware.modules.impl.movement;

import fiveware.Fiveware;
import fiveware.events.Event;
import fiveware.events.impl.EventUpdate;
import fiveware.interfaces.Methods;
import fiveware.modules.Category;
import fiveware.modules.Module;
import fiveware.userinterfaces.clickgui.settings.Setting;
import fiveware.utilities.player.Movement;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.client.C03PacketPlayer;

public class Step extends Module implements Methods {
   private boolean step;
   private int ticks = 0;
   public Setting mode = new Setting("Mode", new String[]{"Vanilla", "NCP 1", "NCP 2", "Legit", "AAC4", "AAC3"}, "Vanilla", this);
   public Setting height = new Setting("Height", 2.0F, 1.0F, 10.0F, false, this);
   public Setting aac4 = new Setting("1.5 Blocks", true, this);

   public Step() {
      super("Step", "Step", "Allows you to step up blocks.", -1, 0, Category.MOVEMENT);
   }

   public void onEvent(Event event) {
      if (!this.mode.getCurrentMode().equalsIgnoreCase("Vanilla")) {
         this.height.visible = false;
      } else {
         this.height.visible = true;
      }

      if (!this.mode.getCurrentMode().equalsIgnoreCase("AAC4")) {
         this.aac4.visible = false;
      } else {
         this.aac4.visible = true;
      }

      if (this.isToggled()) {
         if (event instanceof EventUpdate) {
            this.setInfo(this.information() ? "§7" + this.mode.getCurrentMode() + (this.leftSide() ? " §r" : "") : "");
            if (this.getPlayer() == null) {
               return;
            }

            if (this.mode.getCurrentMode().equalsIgnoreCase("Vanilla")) {
               this.getPlayer().stepHeight = this.height.getCurrentValue();
            } else {
               this.getPlayer().stepHeight = 0.5F;
            }

            if (this.mode.getCurrentMode().equalsIgnoreCase("NCP 1")) {
               if (this.getPlayer().isCollidedHorizontally && this.onGround() && Movement.canStep(1.0F)) {
                  ++this.ticks;
                  switch(this.ticks) {
                  case 1:
                     if (this.onGround()) {
                        this.getTimer().timerSpeed = 1.05F;
                        this.getPlayer().motionY = 0.41999998688697815D;
                     }
                  case 2:
                  default:
                     break;
                  case 3:
                     this.getPlayer().motionY = -1.0D;
                  case 4:
                     this.getPlayer().setSpeed(this.getPlayer().getSpeed() + 0.15F);
                     this.getTimer().timerSpeed = 1.0F;
                  }
               } else {
                  this.ticks = 0;
               }
            } else if (this.mode.getCurrentMode().equalsIgnoreCase("NCP 2")) {
               if (this.getPlayer().isCollidedHorizontally && this.onGround() && Movement.canStep(1.0F)) {
                  this.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(this.getX(), this.getY() + 0.42D, this.getZ(), this.onGround()));
                  this.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(this.getX(), this.getY() + 0.75D, this.getZ(), this.onGround()));
                  this.getPlayer().setPosition(this.getX(), this.getY() + 0.51D, this.getZ());
                  (new Thread(new Runnable() {
                     public void run() {
                        try {
                           Thread.sleep(100L);
                        } catch (InterruptedException var2) {
                           var2.printStackTrace();
                        }

                     }
                  })).start();
               }
            } else if (this.mode.getCurrentMode().equalsIgnoreCase("Legit")) {
               if (this.getPlayer().isCollidedHorizontally && this.onGround() && Movement.canStep(1.0F)) {
                  this.jump();
               }
            } else {
               EntityPlayerSP var10000;
               if (this.mode.getCurrentMode().equalsIgnoreCase("AAC3")) {
                  if (this.getPlayer().isCollidedHorizontally) {
                     if (!this.onGround() || !Movement.canStep(1.0F) && !Movement.canStep(2.0F) && !Movement.canStep(1.5F)) {
                        if (this.getPlayer().motionY < 0.03D) {
                           var10000 = this.getPlayer();
                           var10000.motionY += 0.08D;
                        }
                     } else {
                        this.jump();
                     }

                     if (this.getPlayer().motionY > 0.03D) {
                        this.getPlayer().setPosition(this.getPlayer().posX, this.getPlayer().posY - 0.001D, this.getPlayer().posZ);
                        this.getPlayer().onGround = true;
                        this.getPlayer().motionX = -Math.sin(Math.toRadians((double)this.getPlayer().rotationYaw)) * -0.05D;
                        this.getPlayer().motionZ = Math.cos(Math.toRadians((double)this.getPlayer().rotationYaw)) * -0.05D;
                     }
                  }
               } else if (this.mode.getCurrentMode().equalsIgnoreCase("AAC4")) {
                  if (this.step) {
                     this.getTimer().timerSpeed = (float)(0.9D + (double)(this.getPlayer().ticksExisted % 4 / 20));
                  }

                  if (this.onGround()) {
                     this.step = false;
                     if (!Fiveware.moduleManager.getModuleByName("Speed").isToggled() || !Fiveware.moduleManager.getModuleByName("Timer").isToggled() || !Fiveware.moduleManager.getModuleByName("Scaffold").isToggled() || !Fiveware.moduleManager.getModuleByName("FastLadder").isToggled()) {
                        this.getTimer().timerSpeed = 1.0F;
                     }
                  }

                  if (this.getPlayer().isCollidedHorizontally && this.isMoving() && this.onGround() && (Movement.canStep(1.0F) || this.aac4.isToggled() && Movement.canStep(1.5F))) {
                     this.getPlayer().isAirBorne = true;
                     this.step = true;
                     this.getTimer().timerSpeed = 4.0F;
                     var10000 = this.getPlayer();
                     var10000.motionY += this.aac4.isToggled() ? 0.55D : 0.47D;
                  }
               }
            }
         }

      }
   }

   public void onDisable() {
      this.getPlayer().stepHeight = 0.5F;
      this.getTimer().timerSpeed = 1.0F;
   }
}
