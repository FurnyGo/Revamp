package fiveware.modules.impl.render;

import fiveware.events.Event;
import fiveware.events.impl.EventRender3D;
import fiveware.interfaces.Methods;
import fiveware.modules.Category;
import fiveware.modules.Module;
import fiveware.userinterfaces.clickgui.settings.Setting;
import java.util.Iterator;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;

public class NameTags extends Module implements Methods {
   public Setting scale = new Setting("Scale", 1.0F, 0.5F, 2.0F, false, this);
   public Setting opacity = new Setting("Opacity", 80.0F, 10.0F, 100.0F, true, this);
   public Setting health = new Setting("Show Health", true, this);
   public Setting ping = new Setting("Show Ping", true, this);
   public Setting dist = new Setting("Show Distance", true, this);
   public Setting background = new Setting("Show Background", true, this);
   public Setting showself = new Setting("Show Self", true, this);

   public NameTags() {
      super("Name Tags", "NameTags", "Improves name tags.", -1, 0, Category.RENDER);
   }

   public void onEvent(Event event) {
      if (this.isToggled()) {
         if (event instanceof EventRender3D) {
            Iterator var3 = this.getWorld().playerEntities.iterator();

            while(true) {
               EntityPlayer e;
               do {
                  do {
                     do {
                        if (!var3.hasNext()) {
                           return;
                        }

                        e = (EntityPlayer)var3.next();
                     } while(e == this.getPlayer() && !this.showself.isToggled());
                  } while(this.showself.isToggled() && this.getGameSettings().thirdPersonView == 0 && e == this.getPlayer());
               } while(e.isDead);

               double var10000 = e.lastTickPosX + (e.posX - e.lastTickPosX) * (double)this.getTimer().renderPartialTicks;
               this.getRenderManager();
               double x = var10000 - RenderManager.renderPosX;
               var10000 = e.lastTickPosY + (e.posY - e.lastTickPosY) * (double)this.getTimer().renderPartialTicks;
               this.getRenderManager();
               double y = var10000 - RenderManager.renderPosY;
               var10000 = e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * (double)this.getTimer().renderPartialTicks;
               this.getRenderManager();
               double z = var10000 - RenderManager.renderPosZ;
               EnumChatFormatting color;
               if (e.getHealth() > 15.0F) {
                  color = EnumChatFormatting.GREEN;
               } else if (e.getHealth() > 7.0F && e.getHealth() <= 15.0F) {
                  color = EnumChatFormatting.YELLOW;
               } else {
                  color = EnumChatFormatting.RED;
               }

               EnumChatFormatting color1;
               if (e.getDistanceToEntity(this.getPlayer()) > 8.0F) {
                  color1 = EnumChatFormatting.GREEN;
               } else if (e.getDistanceToEntity(this.getPlayer()) > 4.0F && e.getDistanceToEntity(this.getPlayer()) <= 8.0F) {
                  color1 = EnumChatFormatting.YELLOW;
               } else {
                  color1 = EnumChatFormatting.RED;
               }

               boolean check = this.getNetHandler().getPlayerInfo(e.getUniqueID()) == null;
               EnumChatFormatting color2;
               if (!check) {
                  if (this.getNetHandler().getPlayerInfo(e.getUniqueID()).getResponseTime() >= 200) {
                     color2 = EnumChatFormatting.RED;
                  } else if (this.getNetHandler().getPlayerInfo(e.getUniqueID()).getResponseTime() >= 100 && this.getNetHandler().getPlayerInfo(e.getUniqueID()).getResponseTime() < 200) {
                     color2 = EnumChatFormatting.YELLOW;
                  } else {
                     color2 = EnumChatFormatting.GREEN;
                  }
               } else {
                  color2 = EnumChatFormatting.GRAY;
               }

               String information = (this.health.isToggled() ? "" + color + (int)e.getHealth() : "") + (this.dist.isToggled() ? (this.health.isToggled() ? "§r | " : "") + color1 + (int)e.getDistanceToEntity(this.getPlayer()) : "") + (!this.ping.isToggled() ? "" : (!this.dist.isToggled() && !this.health.isToggled() ? "" : "§r | ") + color2 + (check ? "0" : this.getNetHandler().getPlayerInfo(e.getUniqueID()).getResponseTime()) + "ms");
               GlStateManager.pushMatrix();
               GlStateManager.translate((float)x + 0.0F, (float)y + e.height + 0.5F, (float)z);
               GlStateManager.rotate(-this.getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
               GlStateManager.rotate(this.getRenderManager().playerViewX, 1.0F, 0.0F, 0.0F);
               GlStateManager.scale(-this.scale.getCurrentValue() / 24.0F - this.getPlayer().getDistanceToEntity(e) / 24.0F / 24.0F, -this.scale.getCurrentValue() / 24.0F - this.getPlayer().getDistanceToEntity(e) / 24.0F / 24.0F, this.scale.getCurrentValue() / 24.0F + this.getPlayer().getDistanceToEntity(e) / 24.0F / 24.0F);
               GlStateManager.depthMask(false);
               GlStateManager.disableDepth();
               GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
               GlStateManager.disableTexture2D();
               GlStateManager.enableBlend();
               Tessellator tessellator = Tessellator.getInstance();
               WorldRenderer worldrenderer = tessellator.getWorldRenderer();
               int i = fr.getStringWidth(e.getDisplayName().getFormattedText()) / 2;
               worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
               if (this.background.isToggled()) {
                  worldrenderer.pos((double)(-i - 2), (double)(-10.0F - this.scale.getCurrentValue()) - 0.5D, 0.0D).color(0.0F, 0.0F, 0.0F, this.opacity.getCurrentValue() / 100.0F).endVertex();
                  worldrenderer.pos((double)(-i - 2), (double)(8.0F - this.scale.getCurrentValue() - 3.0F), 0.0D).color(0.0F, 0.0F, 0.0F, this.opacity.getCurrentValue() / 100.0F).endVertex();
                  worldrenderer.pos((double)(i + 2), (double)(8.0F - this.scale.getCurrentValue() - 3.0F), 0.0D).color(0.0F, 0.0F, 0.0F, this.opacity.getCurrentValue() / 100.0F).endVertex();
                  worldrenderer.pos((double)(i + 2), (double)(-10.0F - this.scale.getCurrentValue()) - 0.5D, 0.0D).color(0.0F, 0.0F, 0.0F, this.opacity.getCurrentValue() / 100.0F).endVertex();
               }

               tessellator.draw();
               GlStateManager.disableBlend();
               GlStateManager.enableTexture2D();
               fr.drawString(e.getDisplayName().getFormattedText(), -fr.getStringWidth(e.getDisplayName().getFormattedText()) / 2, (int)(-9.0F - this.scale.getCurrentValue()), -1);
               GlStateManager.scale(0.5F, 0.5F, 0.5F);
               fr.drawString(information, -fr.getStringWidth(e.getDisplayName().getFormattedText()), (int)(-1.0F - this.scale.getCurrentValue()), -1);
               GlStateManager.depthMask(true);
               GlStateManager.enableDepth();
               GlStateManager.popMatrix();
            }
         }
      }
   }
}
