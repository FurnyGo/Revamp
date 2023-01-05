// 
// Decompiled by Procyon v0.5.36
// 

package revamp.modules.impl.render;

import net.minecraft.entity.Entity;
import java.util.Iterator;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.util.AxisAlignedBB;
import revamp.Revamp;
import revamp.utilities.impl.render.Rainbow;
import net.minecraft.client.gui.Gui;
import java.awt.Color;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;
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
    public Setting rainbow;
    public Setting expand;
    public Setting width;
    
    public ESP() {
        super("ESP", "ESP", "Renders a box on players.", -1, 0, Category.RENDER);
        this.mode = new Setting("Mode", new String[] { "2D", "3D" }, "3D", this);
        this.rainbow = new Setting("Rainbow", false, this);
        this.expand = new Setting("3D Hitbox Fix", false, this);
        this.width = new Setting("3D Line Width", 3.0f, 1.0f, 5.0f, false, this);
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
                        final double x = e.lastTickPosX + (e.posX - e.lastTickPosX) * this.getTimer().renderPartialTicks - this.getRenderManager().renderPosX;
                        final double y = e.lastTickPosY + (e.posY - e.lastTickPosY) * this.getTimer().renderPartialTicks - this.getRenderManager().renderPosY;
                        final double z = e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * this.getTimer().renderPartialTicks - this.getRenderManager().renderPosZ;
                        GL11.glPushMatrix();
                        GL11.glTranslated(x, y - 0.15, z);
                        GL11.glScalef(0.03f, 0.03f, 0.03f);
                        GL11.glRotated((double)(-this.getRenderManager().playerViewY), 0.0, 1.0, 0.0);
                        GlStateManager.disableDepth();
                        Gui.drawRect(-20.0, -1.0, -26.0, 75.0, Color.BLACK.hashCode());
                        Gui.drawRect(-21.0, 0.0, -25.0, 74.0, this.rainbow.isToggled() ? Rainbow.rainbow(5.0f, 0.5f, 1.0f, 200L) : Color.WHITE.hashCode());
                        Gui.drawRect(20.0, -1.0, 26.0, 75.0, Color.BLACK.hashCode());
                        Gui.drawRect(21.0, 0.0, 25.0, 74.0, this.rainbow.isToggled() ? Rainbow.rainbow(5.0f, 0.5f, 1.0f, 200L) : Color.WHITE.hashCode());
                        Gui.drawRect(-20.0, -1.0, 21.0, 5.0, Color.BLACK.hashCode());
                        Gui.drawRect(-21.0, 0.0, 24.0, 4.0, this.rainbow.isToggled() ? Rainbow.rainbow(5.0f, 0.5f, 1.0f, 200L) : Color.WHITE.hashCode());
                        Gui.drawRect(-20.0, 70.0, 21.0, 75.0, Color.BLACK.hashCode());
                        Gui.drawRect(-21.0, 71.0, 25.0, 74.0, this.rainbow.isToggled() ? Rainbow.rainbow(5.0f, 0.5f, 1.0f, 200L) : Color.WHITE.hashCode());
                        GlStateManager.enableDepth();
                        GL11.glPopMatrix();
                    }
                    else {
                        if (!this.mode.getCurrentMode().equalsIgnoreCase("3D")) {
                            continue;
                        }
                        GL11.glBlendFunc(770, 771);
                        GL11.glEnable(3042);
                        GL11.glLineWidth(this.width.getCurrentValue());
                        GL11.glDisable(3553);
                        GL11.glDisable(2929);
                        GL11.glDepthMask(false);
                        GL11.glColor4d(1.0, 1.0, 1.0, 1.0);
                        RenderGlobal.func_181561_a(new AxisAlignedBB(e.boundingBox.minX - e.posX + (e.posX - this.getRenderManager().renderPosX) - (this.expand.isToggled() ? (Revamp.moduleManager.getModuleByName("Hitboxes").isToggled() ? Revamp.settingManager.getSetting(Revamp.moduleManager.getModuleByName("Hitboxes"), "Hitbox").getCurrentValue() : 0.0f) : 0.0f), e.boundingBox.minY - e.posY + (e.posY - this.getRenderManager().renderPosY) - (this.expand.isToggled() ? (Revamp.moduleManager.getModuleByName("Hitboxes").isToggled() ? Revamp.settingManager.getSetting(Revamp.moduleManager.getModuleByName("Hitboxes"), "Hitbox").getCurrentValue() : 0.0f) : 0.0f), e.boundingBox.minZ - e.posZ + (e.posZ - this.getRenderManager().renderPosZ) - (this.expand.isToggled() ? (Revamp.moduleManager.getModuleByName("Hitboxes").isToggled() ? Revamp.settingManager.getSetting(Revamp.moduleManager.getModuleByName("Hitboxes"), "Hitbox").getCurrentValue() : 0.0f) : 0.0f), e.boundingBox.maxX - e.posX + (e.posX - this.getRenderManager().renderPosX) + (this.expand.isToggled() ? (Revamp.moduleManager.getModuleByName("Hitboxes").isToggled() ? Revamp.settingManager.getSetting(Revamp.moduleManager.getModuleByName("Hitboxes"), "Hitbox").getCurrentValue() : 0.0f) : 0.0f), e.boundingBox.maxY - e.posY + (e.posY - this.getRenderManager().renderPosY) + 0.1 + (this.expand.isToggled() ? (Revamp.moduleManager.getModuleByName("Hitboxes").isToggled() ? Revamp.settingManager.getSetting(Revamp.moduleManager.getModuleByName("Hitboxes"), "Hitbox").getCurrentValue() : 0.0f) : 0.0f), e.boundingBox.maxZ - e.posZ + (e.posZ - this.getRenderManager().renderPosZ) + (this.expand.isToggled() ? (Revamp.moduleManager.getModuleByName("Hitboxes").isToggled() ? Revamp.settingManager.getSetting(Revamp.moduleManager.getModuleByName("Hitboxes"), "Hitbox").getCurrentValue() : 0.0f) : 0.0f)));
                        GL11.glEnable(3553);
                        GL11.glEnable(2929);
                        GL11.glDepthMask(true);
                        GL11.glDisable(3042);
                    }
                }
            }
        }
    }
}
