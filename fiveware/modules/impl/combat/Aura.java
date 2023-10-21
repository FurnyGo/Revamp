package fiveware.modules.impl.combat;

import fiveware.Fiveware;
import fiveware.events.Event;
import fiveware.events.impl.EventPre;
import fiveware.interfaces.Methods;
import fiveware.modules.Category;
import fiveware.modules.Module;
import fiveware.userinterfaces.clickgui.settings.Setting;
import fiveware.utilities.misc.Randomizer;
import fiveware.utilities.time.Timer;
import java.security.SecureRandom;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C0APacketAnimation;

public class Aura extends Module implements Methods {
   private int spinyaw = 0;
   private int cps;
   private int targetIndex;
   public EntityLivingBase target;
   private Timer timer = new Timer();
   private boolean block;
   public Setting mode = new Setting("Mode", new String[]{"Single", "Switch"}, "Single", this);
   public Setting sortingmode = new Setting("Sorting Mode", new String[]{"Distance", "Health"}, "Distance", this);
   public Setting crack = new Setting("Crack Size", 5.0F, 0.0F, 10.0F, true, this);
   public Setting range = new Setting("Range", 3.0F, 1.0F, 6.0F, false, this);
   public Setting minaps = new Setting("Minimum APS", 8.0F, 1.0F, 20.0F, false, this);
   public Setting maxaps = new Setting("Maximum APS", 12.0F, 1.0F, 20.0F, false, this);
   public Setting rotations = new Setting("Rotations", true, this);
   public Setting rotationmode = new Setting("Rotation Mode", new String[]{"Vanilla", "Spin", "Bypass"}, "Vanilla", this);
   public Setting rotationtype = new Setting("Rotation Type", new String[]{"Client", "Server"}, "Server", this);
   public Setting autoblock = new Setting("AutoBlock", true, this);
   public Setting blockmode = new Setting("Block Mode", new String[]{"Vanilla", "AAC3", "Fake"}, "Vanilla", this);
   public Setting throughwalls = new Setting("Through Walls", true, this);
   public Setting noswing = new Setting("No Swing", false, this);

   public Aura() {
      super("Aura", "Aura", "Kills people for you.", -1, 19, Category.COMBAT);
   }

   public void onEvent(Event event) {
      this.rotationmode.visible = this.rotations.isToggled();
      this.rotationtype.visible = this.rotations.isToggled();
      this.blockmode.visible = this.autoblock.isToggled();
      if (this.isToggled()) {
         if (event instanceof EventPre) {
            this.setInfo(this.information() ? "§7" + this.mode.getCurrentMode() + (this.leftSide() ? " §r" : "") : "");
            boolean attack = false;
            if (this.timer.hasReached((long)this.cps)) {
               int maxValue = (int)((this.minaps.getMaxValue() - this.maxaps.getCurrentValue()) * 20.0F);
               int minValue = (int)((this.minaps.getMaxValue() - this.minaps.getCurrentValue()) * 20.0F);
               this.cps = (int)((double)minValue + (new SecureRandom()).nextDouble() * (double)(maxValue - minValue) - (double)(new SecureRandom()).nextInt(10) + (double)(new SecureRandom()).nextInt(10));
               this.timer.reset();
               attack = true;
            }

            List<EntityLivingBase> entities = this.getTargets();
            if (entities.size() >= this.targetIndex) {
               this.targetIndex = 0;
            }

            if (entities.isEmpty()) {
               this.targetIndex = 0;
               return;
            }

            EntityLivingBase entity = (EntityLivingBase)entities.get(this.targetIndex);
            this.target = entities.size() > 0 ? (EntityLivingBase)entities.get(0) : null;
            this.update((EventPre)event, this.mode.getCurrentMode().equalsIgnoreCase("Single") ? this.target : entity);
            if (attack) {
               this.block = true;
               int i;
               if (this.mode.getCurrentMode().equalsIgnoreCase("Single")) {
                  if (this.target == null) {
                     return;
                  }

                  if (!this.throughwalls.isToggled() && !this.getPlayer().canEntityBeSeen(this.target)) {
                     return;
                  }

                  for(i = 0; (float)i < this.crack.getCurrentValue(); ++i) {
                     this.getPlayer().onCriticalHit(this.target);
                  }

                  if (this.noswing.isToggled()) {
                     this.sendPacket(new C0APacketAnimation());
                  } else {
                     this.getPlayer().swingItem();
                  }

                  this.getPlayerController().attackEntity(this.getPlayer(), this.target);
               } else {
                  if (entity == null) {
                     return;
                  }

                  if (!this.throughwalls.isToggled() && !this.getPlayer().canEntityBeSeen(entity)) {
                     return;
                  }

                  for(i = 0; (float)i < this.crack.getCurrentValue(); ++i) {
                     this.getPlayer().onCriticalHit(this.target);
                  }

                  if (this.noswing.isToggled()) {
                     this.sendPacket(new C0APacketAnimation());
                  } else {
                     this.getPlayer().swingItem();
                  }

                  this.getPlayerController().attackEntity(this.getPlayer(), entity);
                  ++this.targetIndex;
               }
            }

            if (this.block && this.autoblock.isToggled() && !this.blockmode.getCurrentMode().equalsIgnoreCase("Fake")) {
               this.block();
            }
         }

      }
   }

   public void onDisable() {
      this.targetIndex = 0;
      this.timer.reset();
      this.target = null;
      this.block = false;
   }

   private List<EntityLivingBase> getTargets() {
      List<EntityLivingBase> entities = (List)this.getWorld().loadedEntityList.stream().filter((entity) -> {
         return entity instanceof EntityLivingBase;
      }).map((entity) -> {
         return (EntityLivingBase)entity;
      }).filter((entity) -> {
         if ((!(entity instanceof EntityPlayer) || Fiveware.moduleManager.getModuleByName("Players").isToggled()) && (!(entity instanceof EntityAnimal) || Fiveware.moduleManager.getModuleByName("Animals").isToggled()) && (!(entity instanceof EntityMob) || Fiveware.moduleManager.getModuleByName("Mobs").isToggled()) && (!entity.isOnSameTeam(this.getPlayer()) || Fiveware.moduleManager.getModuleByName("Teams").isToggled()) && (!entity.isInvisible() || Fiveware.moduleManager.getModuleByName("Invisibles").isToggled()) && (entity instanceof EntityPlayer || entity instanceof EntityAnimal || entity instanceof EntityMob || Fiveware.moduleManager.getModuleByName("Others").isToggled()) && !(entity instanceof EntityArmorStand) && (!(entity instanceof EntityPlayer) || !Fiveware.friendManager.isFriend(entity.getName()) || Fiveware.moduleManager.getModuleByName("Friends").isToggled())) {
            return this.getPlayer() != entity && entity.isEntityAlive();
         } else {
            return false;
         }
      }).filter((entity) -> {
         return this.getPlayer().getDistanceToEntity(entity) < this.range.getCurrentValue();
      }).sorted(Comparator.comparingDouble((entity) -> {
         String var2;
         switch((var2 = this.sortingmode.getCurrentMode()).hashCode()) {
         case -2137395588:
            if (var2.equals("Health")) {
               return (double)entity.getHealth();
            }
            break;
         case 353103893:
            if (var2.equals("Distance")) {
               return this.getPlayer().getDistanceSqToEntity(entity);
            }
         }

         return -1.0D;
      })).sorted(Comparator.comparing((entity) -> {
         return entity instanceof EntityPlayer;
      })).collect(Collectors.toList());
      return entities;
   }

   private float[] getRotations(EntityLivingBase entity, EventPre target) {
      if (!this.rotationmode.getCurrentMode().equalsIgnoreCase("Vanilla") && !this.rotationmode.getCurrentMode().equalsIgnoreCase("Bypass")) {
         this.spinyaw += 10;
         float pitch = Randomizer.randomFloat(89.0F, 90.0F);
         float yaw = (float)this.spinyaw;
         return new float[]{yaw, pitch};
      } else {
         double x = entity.posX + (entity.posX - entity.lastTickPosX) - target.x;
         double y = entity.posY + (double)entity.getEyeHeight() - (target.y + (double)this.getPlayer().getEyeHeight()) - 0.1D;
         double z = entity.posZ + (entity.posZ - entity.lastTickPosZ) - target.z;
         double yaw = Math.toDegrees(-Math.atan(x / z));
         double pitch = -Math.toDegrees(Math.atan(y / Math.sqrt(Math.pow(x, 2.0D) + Math.pow(z, 2.0D))));
         if (x > 0.0D && z < 0.0D) {
            yaw = -90.0D + Math.toDegrees(Math.atan(z / x));
         } else if (x < 0.0D && z < 0.0D) {
            yaw = 90.0D + Math.toDegrees(Math.atan(z / x));
         }

         double lcd = (double)this.getGameSettings().mouseSensitivity * 0.6D + 0.2D;
         double gcd = lcd * lcd * lcd * 1.2D;
         yaw -= yaw % gcd;
         pitch -= pitch % gcd;
         if (this.rotationmode.getCurrentMode().equalsIgnoreCase("Bypass")) {
            yaw += Math.random() * Math.random() * 6.0D - Math.random();
            pitch += Math.random() * Math.random() * (6.0D - Math.random()) - Math.random();
         }

         if (pitch > 90.0D) {
            pitch = 90.0D;
         }

         if (pitch < -90.0D) {
            pitch = -90.0D;
         }

         if (yaw > 180.0D) {
            yaw = 180.0D;
         }

         if (yaw < -180.0D) {
            yaw = -180.0D;
         }

         return new float[]{(float)yaw, (float)pitch};
      }
   }

   private void block() {
      if (this.getPlayer().inventory.getCurrentItem() != null) {
         if (this.blockmode.getCurrentMode().equalsIgnoreCase("Vanilla")) {
            this.getPlayerController().sendUseItem(this.getPlayer(), this.getWorld(), this.getPlayer().inventory.getCurrentItem());
         } else if (this.blockmode.getCurrentMode().equalsIgnoreCase("AAC3") && this.getPlayer().ticksExisted % 2 == 0) {
            this.getPlayerController().sendUseItem(this.getPlayer(), this.getWorld(), this.getPlayer().inventory.getCurrentItem());
         }

      }
   }

   public boolean checkAutoblock() {
      return this.isToggled() && this.block && this.autoblock.isToggled() && !this.blockmode.getCurrentMode().equalsIgnoreCase("Vanilla") && this.getPlayer().getCurrentEquippedItem() != null && this.getPlayer().getCurrentEquippedItem().getItem() instanceof ItemSword && this.target != null && this.getPlayer().getDistanceToEntity(this.target) < this.range.getCurrentValue();
   }

   private void update(EventPre e, EntityLivingBase target) {
      if (target != null) {
         this.updateRotations(e, target);
      }

   }

   private void updateRotations(EventPre e, EntityLivingBase target) {
      float[] rot = this.getRotations(target, e);
      if (this.rotations.isToggled()) {
         if (this.rotationtype.getCurrentMode().equalsIgnoreCase("Server")) {
            e.setYaw(rot[0]);
            e.setPitch(rot[1]);
            this.getPlayer().renderYawOffset = rot[0];
            this.getPlayer().rotationYawHead = rot[0];
         } else {
            this.getPlayer().rotationYaw = rot[0];
            this.getPlayer().rotationPitch = rot[1];
         }
      }

   }
}
