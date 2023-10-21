package fiveware.modules.impl.render;

import fiveware.Fiveware;
import fiveware.events.Event;
import fiveware.events.impl.EventRender2D;
import fiveware.interfaces.Methods;
import fiveware.modules.Category;
import fiveware.modules.Module;
import fiveware.modules.impl.combat.Aura;
import fiveware.userinterfaces.clickgui.settings.Setting;
import fiveware.utilities.render.Items;
import fiveware.utilities.render.Rainbow;
import java.awt.Color;
import java.util.Objects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

public class TargetHUD extends Module implements Methods {
   public Setting mode = new Setting("Mode", new String[]{"Fiveware", "New", "Zephyr"}, "Fiveware", this);
   public Setting posx = new Setting("X Pos", 140.0F, 1.0F, 1000.0F, false, this);
   public Setting posy = new Setting("Y Pos", 280.0F, 1.0F, 1000.0F, false, this);
   public Setting opacity = new Setting("Opacity", 80.0F, 0.0F, 150.0F, true, this);
   public Setting health = new Setting("Show Health", true, this);
   public Setting armor = new Setting("Show Armor", false, this);
   public Setting bar = new Setting("Show Bar", false, this);
   public Setting ping = new Setting("Show Ping", true, this);

   public TargetHUD() {
      super("Target HUD", "TargetHUD", "Shows a overlay of the target.", -1, 0, Category.RENDER);
   }

   public void onEvent(Event event) {
      if (!this.mode.getCurrentMode().equalsIgnoreCase("New") && !this.mode.getCurrentMode().equalsIgnoreCase("Zephyr")) {
         this.armor.visible = true;
         this.bar.visible = true;
         this.health.visible = true;
         this.ping.visible = true;
      } else {
         this.armor.visible = false;
         this.bar.visible = false;
         if (this.mode.getCurrentMode().equalsIgnoreCase("Zephyr")) {
            this.health.visible = false;
            this.ping.visible = false;
         } else {
            this.health.visible = true;
            this.ping.visible = true;
         }
      }

      if (this.isToggled()) {
         if (event instanceof EventRender2D) {
            this.setInfo(this.information() ? "§7" + this.mode.getCurrentMode() + (this.leftSide() ? " §r" : "") : "");
            new ScaledResolution(Minecraft.getMinecraft());
            Aura aura = (Aura)Fiveware.moduleManager.getModuleByName("Aura");
            if (aura.isToggled() && aura.target != null) {
               if (this.getPlayer().getDistanceToEntity(aura.target) > 6.0F || !aura.target.isEntityAlive()) {
                  return;
               }

               float x = this.posx.getCurrentValue();
               float y = this.posy.getCurrentValue();
               NetworkPlayerInfo playerinfo;
               String ms;
               if (this.mode.getCurrentMode().equalsIgnoreCase("Fiveware")) {
                  double hpWidth = 92.0D * MathHelper.clamp_double((double)(aura.target.getHealth() / aura.target.getMaxHealth()), 0.0D, 1.0D);
                  float range = (float)Math.round((double)this.getPlayer().getDistanceToEntity(aura.target) * 100.0D) / 100.0F;
                  playerinfo = this.getNetHandler().getPlayerInfo(aura.target.getUniqueID());
                  EnumChatFormatting color;
                  if (playerinfo != null) {
                     if (playerinfo.getResponseTime() >= 200) {
                        color = EnumChatFormatting.RED;
                     } else if (playerinfo.getResponseTime() >= 100 && playerinfo.getResponseTime() < 200) {
                        color = EnumChatFormatting.YELLOW;
                     } else {
                        color = EnumChatFormatting.GREEN;
                     }
                  } else {
                     color = EnumChatFormatting.GRAY;
                  }

                  ms = playerinfo == null ? "0ms" : "" + color + playerinfo.getResponseTime() + "ms";
                  String healthStr = String.valueOf((int)aura.target.getHealth() * 2 / 2);
                  String match;
                  if ((float)((int)aura.target.getHealth() * 2) / 2.0F == aura.target.getHealth()) {
                     match = "Tied";
                  } else if ((float)((int)aura.target.getHealth() * 2) / 2.0F > aura.target.getHealth()) {
                     match = "Losing";
                  } else {
                     match = "Winning";
                  }

                  Color c = Rainbow.mixedColor(Color.red, Color.black, 100.0F);
                  Color c1 = Rainbow.mixedColor(Color.red, Color.black, 200.0F);
                  Gui.drawRect((double)x - 6.0D, (double)y - 6.0D, (double)(x + 141.0F), (double)(y + 41.0F), (new Color(c.getRed(), c.getGreen(), c.getBlue(), (int)this.opacity.getCurrentValue())).getRGB());
                  Gui.drawRect((double)x - 3.0D, (double)y - 3.0D, (double)(x + 138.0F), (double)(y + 38.0F), (new Color(c1.getRed(), c1.getGreen(), c1.getBlue(), (int)this.opacity.getCurrentValue())).getRGB());
                  if (this.bar.isToggled()) {
                     Gui.drawRect((double)(x + 25.0F), (double)(y + 38.0F), (double)(x + 117.0F), (double)(y + 27.0F), Fiveware.getClientCOLOR().darker().darker().getRGB());
                     Gui.drawRect((double)(x + 25.0F), (double)(y + 38.0F), (double)(x + 25.0F) + hpWidth, (double)(y + 27.0F), Fiveware.getClientCOLOR().getRGB());
                  }

                  if (this.health.isToggled()) {
                     fr.drawStringWithShadow(healthStr + " hearts", x + 27.0F, (double)(y + 29.0F), -1);
                  }

                  fr.drawStringWithShadow(range + " block" + (range == 1.0F ? "" : "s"), x + 27.0F, (double)(y + 8.0F), -1);
                  if (this.ping.isToggled()) {
                     fr.drawStringWithShadow(ms, x + 125.0F - (float)fr.getStringWidth(ms) / 2.0F, (double)y - 2.5D, -1);
                  }

                  fr.drawStringWithShadow(match, x + 27.0F, (double)(y + 18.0F), -1);
                  fr.drawStringWithShadow(aura.target.getName(), x + 27.0F, (double)(y - 2.0F), -1);
                  if (this.armor.isToggled()) {
                     for(int i = 1; i < 5; ++i) {
                        if (aura.target.getEquipmentInSlot(i) != null) {
                           Items.renderItem(aura.target.getEquipmentInSlot(i), (int)(x + 140.0F - (float)((int)((double)(i * 20) / 1.3D)) - 40.0F), (int)(y - 30.0F));
                        }
                     }
                  }

                  if (this.getWorld() != null) {
                     GuiInventory.drawEntityOnScreen((int)(x + 10.0F), (int)(y + 39.0F), 28, 0.0F, 0.0F, aura.target);
                  }
               } else if (this.mode.getCurrentMode().equalsIgnoreCase("Zephyr")) {
                  Gui.drawRect((double)x - 2.0D, (double)y - 2.0D, (double)(x + 151.0F), (double)(y + 31.0F), (new Color(0, 0, 0, (int)this.opacity.getCurrentValue())).getRGB());
                  Gui.drawRect((double)x - 2.0D, (double)y - 2.0D, (double)(x + 151.0F), (double)(y + 31.0F), (new Color(255, 0, 0, aura.target.hurtTime / 10 * 255)).getRGB());
                  Gui.drawRect((double)x - 2.0D, (double)y + 30.0D, (double)(x + 151.0F), (double)(y + 31.0F), Rainbow.rainbow(3.0F, 0.8F, 1.0F, 1L));
                  int h = -15;
                  int q = -5;
                  fr.drawStringWithShadow(aura.target.getName(), x - (float)q, (double)(y - (float)h - 10.0F), -1);
                  fr.drawStringWithShadow("Distance: " + (int)this.getPlayer().getDistanceToEntity(aura.target), x - (float)q, (double)(y - (float)h), -1);
               } else {
                  float xPos;
                  if (aura.target instanceof AbstractClientPlayer) {
                     xPos = 38.0F;
                  } else {
                     xPos = -3.0F;
                  }

                  float offset;
                  if (this.ping.isToggled()) {
                     offset = 0.0F;
                  } else {
                     offset = -10.0F;
                  }

                  Gui.drawRect((double)x - 8.0D, (double)y - 8.0D, (double)(x + 141.0F), (double)(y + 41.0F), (new Color(0, 0, 0, (int)this.opacity.getCurrentValue())).getRGB());
                  Gui.drawRect((double)x - 5.0D, (double)y - 5.0D, (double)(x + 138.0F), (double)(y + 38.0F), (new Color(0, 0, 0, (int)this.opacity.getCurrentValue())).getRGB());
                  fr.drawStringWithShadow(aura.target.getName(), x + xPos, (double)(y - 3.0F), -1);
                  int range = (int)this.getPlayer().getDistanceToEntity(aura.target);
                  playerinfo = this.getNetHandler().getPlayerInfo(aura.target.getUniqueID());
                  String ms = Objects.isNull(playerinfo) ? "0ms" : (playerinfo.getResponseTime() > 100 ? "§c" : "§a") + playerinfo.getResponseTime() + "ms";
                  ms = String.valueOf((float)((int)aura.target.getHealth() * 2) / 2.0F);
                  if (this.health.isToggled()) {
                     fr.drawStringWithShadow(ms + " hearts", x + xPos, (double)(y + 27.0F + offset), -1);
                  }

                  fr.drawStringWithShadow(range + " blocks", x + xPos, (double)(y + 7.0F), -1);
                  if (this.ping.isToggled()) {
                     fr.drawStringWithShadow(ms, x + xPos, (double)(y + 17.0F), -1);
                  }

                  if (aura.target instanceof AbstractClientPlayer) {
                     this.getTextureManager().bindTexture(((AbstractClientPlayer)aura.target).getLocationSkin());
                     GL11.glEnable(3042);
                     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                     Gui.drawScaledCustomSizeModalRect((int)(x - 3.0F), (int)(y - 3.0F), 8.1F, 8.0F, 8, 8, 40, 40, 65.0F, 65.0F);
                     GL11.glDisable(3042);
                  }
               }
            }
         }

      }
   }
}
