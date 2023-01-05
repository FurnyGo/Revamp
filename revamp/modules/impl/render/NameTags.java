// 
// Decompiled by Procyon v0.5.36
// 

package revamp.modules.impl.render;

import java.util.Iterator;
import revamp.utilities.impl.render.Items;
import net.minecraft.client.gui.Gui;
import java.awt.Color;
import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import org.lwjgl.opengl.GL11;
import net.minecraft.entity.player.EntityPlayer;
import revamp.events.impl.EventRender;
import revamp.events.Event;
import revamp.modules.Category;
import revamp.userinterfaces.clickgui.settings.Setting;
import revamp.interfaces.Methods;
import revamp.modules.Module;

public class NameTags extends Module implements Methods
{
    public Setting scale;
    public Setting armor;
    public Setting opacity;
    public Setting item;
    public Setting health;
    public Setting background;
    public Setting showself;
    
    public NameTags() {
        super("NameTags", "NameTags", "Improves name tags.", -1, 0, Category.RENDER);
        this.scale = new Setting("Scale", 1.25f, 0.5f, 2.0f, false, this);
        this.armor = new Setting("Show Armor", false, this);
        this.opacity = new Setting("Opacity", 80.0f, 0.0f, 150.0f, true, this);
        this.item = new Setting("Show Current Item", false, this);
        this.health = new Setting("Show Health", true, this);
        this.background = new Setting("Show Background", true, this);
        this.showself = new Setting("Show Self", true, this);
    }
    
    @Override
    public void onEvent(final Event event) {
        if (!this.isToggled()) {
            return;
        }
        if (event instanceof EventRender) {
            for (final EntityPlayer e : this.getWorld().playerEntities) {
                if (e == this.getPlayer() && !this.showself.isToggled()) {
                    continue;
                }
                if (this.showself.isToggled() && this.getGameSettings().thirdPersonView == 0 && e == this.getPlayer()) {
                    continue;
                }
                if (e.isDead) {
                    continue;
                }
                GL11.glPushMatrix();
                final double n = e.lastTickPosX + (e.posX - e.lastTickPosX) * this.getTimer().renderPartialTicks;
                this.getRenderManager();
                final double x = n - RenderManager.renderPosX;
                final double n2 = e.lastTickPosY + (e.posY - e.lastTickPosY) * this.getTimer().renderPartialTicks;
                this.getRenderManager();
                final double y = n2 - RenderManager.renderPosY;
                final double n3 = e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * this.getTimer().renderPartialTicks;
                this.getRenderManager();
                final double z = n3 - RenderManager.renderPosZ;
                GL11.glTranslated(x, y + e.getEyeHeight() + this.scale.getCurrentValue() / 1.5, z);
                GL11.glNormal3f(0.0f, 1.0f, 0.0f);
                if (this.getGameSettings().thirdPersonView == 2) {
                    GlStateManager.rotate(-this.getRenderManager().playerViewY, 0.0f, 1.0f, 0.0f);
                    GlStateManager.rotate(-this.getRenderManager().playerViewX, 1.0f, 0.0f, 0.0f);
                }
                else {
                    GlStateManager.rotate(-this.getPlayer().rotationYaw, 0.0f, 1.0f, 0.0f);
                    GlStateManager.rotate(this.getPlayer().rotationPitch, 1.0f, 0.0f, 0.0f);
                }
                float scale = 0.02672f;
                scale *= (float)((this.getPlayer().getDistanceToEntity(e) <= 7.0) ? 0.7000000104308128 : (this.getPlayer().getDistanceToEntity(e) * 0.1f));
                scale = Math.min((float)(this.scale.getCurrentValue() * 0.01), scale);
                GL11.glScalef(-scale, -scale, 0.2f);
                GlStateManager.disableLighting();
                GlStateManager.depthMask(false);
                GL11.glDisable(2929);
                final String information = String.valueOf(e.getDisplayName().getFormattedText()) + (this.health.isToggled() ? (" " + ((e.getHealth() < 3.0f) ? "§c" : "§a") + (int)e.getHealth()) : "");
                final int width = NameTags.fr.getStringWidth(information);
                if (this.background.isToggled()) {
                    Gui.drawRect(-width / 2 - 5, 27.0, width / 2 + 5, 40.0, new Color(0, 0, 0, (int)this.opacity.getCurrentValue()).getRGB());
                    Gui.drawRect(-width / 2 - 4, 28.0, width / 2 + 4, 39.0, new Color(0, 0, 0, (int)this.opacity.getCurrentValue()).getRGB());
                }
                NameTags.fr.drawStringWithShadow(information, (float)(width / 2 - NameTags.fr.getStringWidth(information)), 30.0, -1);
                GlStateManager.disableBlend();
                GlStateManager.depthMask(true);
                GL11.glEnable(2929);
                if (this.armor.isToggled()) {
                    for (int i = 1; i < 5; ++i) {
                        if (e.getEquipmentInSlot(i) != null) {
                            Items.renderItem(e.getEquipmentInSlot(i), (int)(i * 20 / 1.3) - 40, -5);
                        }
                    }
                }
                if (this.item.isToggled() && e.getEquipmentInSlot(0) != null) {
                    Items.renderItem(e.getEquipmentInSlot(0), -40, -5);
                }
                GL11.glPopMatrix();
            }
        }
    }
}
