// 
// Decompiled by Procyon v0.5.36
// 

package revamp.modules.impl.render;

import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.util.AxisAlignedBB;
import revamp.Revamp;
import java.util.Iterator;
import net.minecraft.entity.Entity;
import net.minecraft.client.gui.Gui;
import java.awt.Color;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import revamp.events.impl.EventRender;
import revamp.events.Event;
import revamp.modules.Category;
import revamp.userinterfaces.clickgui.settings.Setting;
import revamp.interfaces.Methods;
import revamp.modules.Module;

public class ESP extends Module implements Methods
{
    public Setting mode;
    public Setting red;
    public Setting green;
    public Setting blue;
    public Setting width;
    
    public ESP() {
        super("ESP", "ESP", "Renders a box on players.", -1, 0, Category.RENDER);
        this.mode = new Setting("Mode", new String[] { "2D", "3D" }, "3D", this);
        this.red = new Setting("Red", 0.5f, 0.0f, 1.0f, false, this);
        this.green = new Setting("Green", 0.5f, 0.0f, 1.0f, false, this);
        this.blue = new Setting("Blue", 0.5f, 0.0f, 1.0f, false, this);
        this.width = new Setting("Line Width", 2.0f, 1.0f, 5.0f, false, this);
    }
    
    @Override
    public void onEvent(final Event event) {
        if (!this.isToggled()) {
            return;
        }
        if (event instanceof EventRender) {
            this.setNameWithOutSpace("ESP" + (this.information() ? (" §7" + this.mode.getCurrentMode()) : ""));
            for (final Object o : this.getWorld().loadedEntityList) {
                if (o instanceof EntityPlayer && o != this.getPlayer()) {
                    final EntityPlayer e = (EntityPlayer)o;
                    if (this.mode.getCurrentMode().equalsIgnoreCase("2D")) {
                        final double n = e.lastTickPosX + (e.posX - e.lastTickPosX) * this.getTimer().renderPartialTicks;
                        this.getRenderManager();
                        final double x = n - RenderManager.renderPosX;
                        final double n2 = e.lastTickPosY + (e.posY - e.lastTickPosY) * this.getTimer().renderPartialTicks;
                        this.getRenderManager();
                        final double y = n2 - RenderManager.renderPosY;
                        final double n3 = e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * this.getTimer().renderPartialTicks;
                        this.getRenderManager();
                        final double z = n3 - RenderManager.renderPosZ;
                        GL11.glPushMatrix();
                        GL11.glTranslated(x, y - 0.15, z);
                        GL11.glScalef(0.03f, 0.03f, 0.03f);
                        GL11.glRotated((double)(-this.getRenderManager().playerViewY), 0.0, 1.0, 0.0);
                        GlStateManager.disableDepth();
                        Gui.drawRect(-20.0, -1.0, -26.0, 75.0, Color.BLACK.hashCode());
                        Gui.drawRect(-21.0, 0.0, -25.0, 74.0, new Color(this.red.getCurrentValue(), this.green.getCurrentValue(), this.blue.getCurrentValue()).getRGB());
                        Gui.drawRect(20.0, -1.0, 26.0, 75.0, Color.BLACK.hashCode());
                        Gui.drawRect(21.0, 0.0, 25.0, 74.0, new Color(this.red.getCurrentValue(), this.green.getCurrentValue(), this.blue.getCurrentValue()).getRGB());
                        Gui.drawRect(-20.0, -1.0, 21.0, 5.0, Color.BLACK.hashCode());
                        Gui.drawRect(-21.0, 0.0, 24.0, 4.0, new Color(this.red.getCurrentValue(), this.green.getCurrentValue(), this.blue.getCurrentValue()).getRGB());
                        Gui.drawRect(-20.0, 70.0, 21.0, 75.0, Color.BLACK.hashCode());
                        Gui.drawRect(-21.0, 71.0, 25.0, 74.0, new Color(this.red.getCurrentValue(), this.green.getCurrentValue(), this.blue.getCurrentValue()).getRGB());
                        GlStateManager.enableDepth();
                        GL11.glPopMatrix();
                    }
                    else {
                        if (!this.mode.getCurrentMode().equalsIgnoreCase("3D")) {
                            continue;
                        }
                        this.entityESPBox(e, (int)this.width.getCurrentValue(), new Color(this.red.getCurrentValue(), this.green.getCurrentValue(), this.blue.getCurrentValue()));
                    }
                }
            }
        }
    }
    
    public void entityESPBox(final Entity entity, final int lineWidth, final Color color) {
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glLineWidth((float)lineWidth);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        final double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * this.getTimer().renderPartialTicks;
        final double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * this.getTimer().renderPartialTicks;
        final double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * this.getTimer().renderPartialTicks;
        final float yaw = entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * this.getTimer().renderPartialTicks;
        final double n = x;
        this.getRenderManager();
        final double x2 = n - RenderManager.renderPosX;
        final double n2 = y;
        this.getRenderManager();
        final double y2 = n2 - RenderManager.renderPosY;
        final double n3 = z;
        this.getRenderManager();
        GlStateManager.translate(x2, y2, n3 - RenderManager.renderPosZ);
        GlStateManager.rotate(-yaw, 0.0f, 1.0f, 0.0f);
        final double n4 = x;
        this.getRenderManager();
        final double x3 = -(n4 - RenderManager.renderPosX);
        final double n5 = y;
        this.getRenderManager();
        final double y3 = -(n5 - RenderManager.renderPosY);
        final double n6 = z;
        this.getRenderManager();
        GlStateManager.translate(x3, y3, -(n6 - RenderManager.renderPosZ));
        GL11.glEnable(2848);
        GlStateManager.color(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f);
        final Module module = Revamp.moduleManager.getModuleByName("Hitboxes");
        final double n7 = x - entity.width / 2.0f - 0.05 - x;
        final double n8 = x;
        this.getRenderManager();
        final double x4 = n7 + (n8 - RenderManager.renderPosX) - (module.isToggled() ? Revamp.settingManager.getSetting(module, "Hitbox").getCurrentValue() : 0.0f);
        final double n9 = y - y;
        final double n10 = y;
        this.getRenderManager();
        final double y4 = n9 + (n10 - RenderManager.renderPosY) - (module.isToggled() ? Revamp.settingManager.getSetting(module, "Hitbox").getCurrentValue() : 0.0f);
        final double n11 = z - entity.width / 2.0f - 0.05 - z;
        final double n12 = z;
        this.getRenderManager();
        final double z2 = n11 + (n12 - RenderManager.renderPosZ) - (module.isToggled() ? Revamp.settingManager.getSetting(module, "Hitbox").getCurrentValue() : 0.0f);
        final double n13 = x + entity.width / 2.0f + 0.05 - x;
        final double n14 = x;
        this.getRenderManager();
        final double x5 = n13 + (n14 - RenderManager.renderPosX) + (module.isToggled() ? Revamp.settingManager.getSetting(module, "Hitbox").getCurrentValue() : 0.0f);
        final double n15 = y + entity.height + 0.1 - y;
        final double n16 = y;
        this.getRenderManager();
        final double y5 = n15 + (n16 - RenderManager.renderPosY) + (module.isToggled() ? Revamp.settingManager.getSetting(module, "Hitbox").getCurrentValue() : 0.0f);
        final double n17 = z + entity.width / 2.0f + 0.05 - z;
        final double n18 = z;
        this.getRenderManager();
        RenderGlobal.func_181561_a(new AxisAlignedBB(x4, y4, z2, x5, y5, n17 + (n18 - RenderManager.renderPosZ) + (module.isToggled() ? Revamp.settingManager.getSetting(module, "Hitbox").getCurrentValue() : 0.0f)));
        final double n19 = x;
        this.getRenderManager();
        final double x6 = n19 - RenderManager.renderPosX;
        final double n20 = y;
        this.getRenderManager();
        final double y6 = n20 - RenderManager.renderPosY;
        final double n21 = z;
        this.getRenderManager();
        GlStateManager.translate(x6, y6, n21 - RenderManager.renderPosZ);
        GlStateManager.rotate(yaw, 0.0f, 1.0f, 0.0f);
        final double n22 = x;
        this.getRenderManager();
        final double x7 = -(n22 - RenderManager.renderPosX);
        final double n23 = y;
        this.getRenderManager();
        final double y7 = -(n23 - RenderManager.renderPosY);
        final double n24 = z;
        this.getRenderManager();
        GlStateManager.translate(x7, y7, -(n24 - RenderManager.renderPosZ));
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
    }
}
