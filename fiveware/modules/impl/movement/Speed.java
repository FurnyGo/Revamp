package fiveware.modules.impl.movement;

import fiveware.Fiveware;
import fiveware.events.Event;
import fiveware.events.impl.EventUpdate;
import fiveware.interfaces.Methods;
import fiveware.modules.Category;
import fiveware.modules.Module;
import fiveware.userinterfaces.clickgui.settings.Setting;
import fiveware.utilities.misc.Randomizer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.potion.Potion;
import net.minecraft.util.MathHelper;

public class Speed extends Module implements Methods {
   public Setting mode = new Setting("Mode", new String[]{"Ground", "Hop", "NCP", "AAC33Low", "AAC4", "Updated NCP", "Legit", "DAC", "Verus"}, "Hop", this);
   public Setting speed = new Setting("Speed", 2.5F, 1.0F, 5.0F, false, this);
   public Setting timer = new Setting("Timer", 1.0F, 0.1F, 10.0F, false, this);
   public Setting sprint = new Setting("Sprint", true, this);

   public Speed() {
      super("Speed", "Speed", "Allows you to move faster.", -1, 0, Category.MOVEMENT);
   }

   public void onEvent(Event event) {
      if (!this.mode.getCurrentMode().equalsIgnoreCase("Ground") && !this.mode.getCurrentMode().equalsIgnoreCase("Hop")) {
         this.speed.visible = false;
      } else {
         this.speed.visible = true;
      }

      if (this.mode.getCurrentMode().equalsIgnoreCase("AAC4")) {
         this.timer.visible = false;
      } else {
         this.timer.visible = true;
      }

      if (this.isToggled()) {
         if (event instanceof EventUpdate) {
            this.setInfo(this.information() ? "§7" + this.mode.getCurrentMode() + (this.leftSide() ? " §r" : "") : "");
            if (!this.isMoving()) {
               this.getPlayer().motionX = this.getPlayer().motionZ = 0.0D;
               return;
            }

            if (!this.mode.getCurrentMode().equalsIgnoreCase("Ground") && !this.mode.getCurrentMode().equalsIgnoreCase("Hop")) {
               EntityPlayerSP var10000;
               float timer;
               if (this.mode.getCurrentMode().equalsIgnoreCase("AAC33Low")) {
                  if (this.getPlayer().hurtTime == 0) {
                     if (this.onGround()) {
                        this.jump();
                        this.getPlayer().motionY = 0.4075D;
                     } else {
                        var10000 = this.getPlayer();
                        var10000.motionY -= 0.0145D;
                     }

                     timer = this.getPlayer().rotationYaw * 0.017453292F;
                     var10000 = this.getPlayer();
                     var10000.motionX -= (double)MathHelper.sin(timer) * 0.0015D;
                     var10000 = this.getPlayer();
                     var10000.motionZ += (double)MathHelper.cos(timer) * 0.0015D;
                  }
               } else {
                  boolean check;
                  if (this.mode.getCurrentMode().equalsIgnoreCase("DAC")) {
                     if (this.onGround()) {
                        this.jump();
                     }

                     if (this.getPlayer().isPotionActive(Potion.moveSpeed)) {
                        check = this.getPlayer().getActivePotionEffect(Potion.moveSpeed).getAmplifier() == 1;
                        this.getPlayer().jumpMovementFactor = check ? 0.045F : 0.035F;
                        this.getPlayer().speedInAir = check ? 0.023F : 0.25F;
                        var10000 = this.getPlayer();
                        var10000.motionX *= check ? 1.03D : 1.015D;
                        var10000 = this.getPlayer();
                        var10000.motionZ *= check ? 1.03D : 1.015D;
                     } else {
                        this.getPlayer().jumpMovementFactor = 0.03F;
                        this.getPlayer().speedInAir = 0.026F;
                     }

                     this.getPlayer().setSpeed(this.getPlayer().getSpeed());
                  } else if (this.mode.getCurrentMode().equalsIgnoreCase("AAC4")) {
                     if (this.onGround()) {
                        this.jump();
                     } else if (this.getPlayer().fallDistance <= 0.1F) {
                        this.getTimer().timerSpeed = 1.5F;
                     } else if (this.getPlayer().fallDistance < 1.3F) {
                        this.getTimer().timerSpeed = 0.7F;
                     } else {
                        this.getTimer().timerSpeed = 1.0F;
                     }
                  } else if (this.mode.getCurrentMode().equalsIgnoreCase("Updated NCP")) {
                     if (this.getPlayer().getActivePotionEffect(Potion.moveSpeed) != null) {
                        if (this.onGround()) {
                           this.jump();
                           this.getPlayer().setSpeed(this.getPlayer().getSpeed() + Randomizer.randomFloat(0.186F, 0.18601F));
                        } else {
                           this.getPlayer().setSpeed(this.getPlayer().getSpeed());
                        }
                     } else if (this.onGround()) {
                        this.jump();
                     } else {
                        this.getPlayer().setSpeed(this.getPlayer().getSpeed());
                     }
                  } else if (this.mode.getCurrentMode().equalsIgnoreCase("Legit")) {
                     if (this.onGround()) {
                        this.jump();
                     }
                  } else if (this.mode.getCurrentMode().equalsIgnoreCase("NCP")) {
                     if (this.isMoving()) {
                        timer = 1.6F - this.getPlayer().getSpeed();
                        if (this.getPlayer().isPotionActive(Potion.moveSpeed)) {
                           timer += (float)((this.getPlayer().getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1) / 12);
                        }

                        if ((double)timer > 1.5D) {
                           timer = 1.5F;
                        }

                        if ((double)timer < 0.6D) {
                           timer = 0.6F;
                        }

                        this.getTimer().timerSpeed = timer;
                        if (this.onGround()) {
                           this.jump();
                           if (this.getPlayer().isPotionActive(Potion.moveSpeed)) {
                              boolean check = this.getPlayer().getActivePotionEffect(Potion.moveSpeed).getAmplifier() == 1;
                              var10000 = this.getPlayer();
                              var10000.motionX *= check ? 1.19D : 1.15D;
                              var10000 = this.getPlayer();
                              var10000.motionZ *= check ? 1.19D : 1.15D;
                              this.getPlayer().speedInAir = check ? 0.029F : 0.025F;
                           } else {
                              var10000 = this.getPlayer();
                              var10000.motionX *= 1.1D;
                              var10000 = this.getPlayer();
                              var10000.motionZ *= 1.1D;
                              this.getPlayer().speedInAir = 0.02F;
                           }
                        }
                     }

                     this.getPlayer().setSpeed(this.getPlayer().getSpeed());
                  } else if (this.mode.getCurrentMode().equalsIgnoreCase("Verus")) {
                     if (this.isMoving() && this.onGround()) {
                        this.jump();
                        if (this.getPlayer().isPotionActive(Potion.moveSpeed)) {
                           check = this.getPlayer().getActivePotionEffect(Potion.moveSpeed).getAmplifier() == 1;
                           var10000 = this.getPlayer();
                           var10000.motionX *= check ? 1.18D : 1.15D;
                           var10000 = this.getPlayer();
                           var10000.motionZ *= check ? 1.18D : 1.15D;
                           this.getPlayer().speedInAir = check ? 0.028F : 0.024F;
                        } else {
                           var10000 = this.getPlayer();
                           var10000.motionX *= 1.11D;
                           var10000 = this.getPlayer();
                           var10000.motionZ *= 1.11D;
                           this.getPlayer().speedInAir = 0.02F;
                        }
                     }

                     this.getPlayer().setSpeed(this.getPlayer().getSpeed());
                  }
               }
            } else {
               this.getPlayer().setSpeed(this.speed.getCurrentValue() / 4.0F);
               if (this.onGround() && this.mode.getCurrentMode().equalsIgnoreCase("Hop")) {
                  this.jump();
               }
            }

            if (this.sprint.isToggled()) {
               this.getPlayer().setSprinting(true);
            } else if (!Fiveware.moduleManager.getModuleByName("Sprint").isToggled()) {
               this.getPlayer().setSprinting(false);
            }

            if (!this.mode.getCurrentMode().equalsIgnoreCase("AAC4")) {
               this.getTimer().timerSpeed = this.timer.getCurrentValue();
            }
         }

      }
   }

   public void onDisable() {
      this.getTimer().timerSpeed = 1.0F;
      this.getPlayer().jumpMovementFactor = 0.02F;
      this.getPlayer().speedInAir = 0.02F;
   }
}
