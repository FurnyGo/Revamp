// 
// Decompiled by Procyon v0.5.36
// 

package revamp.userinterfaces;

import revamp.userinterfaces.clickgui.settings.Setting;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import revamp.modules.impl.render.NameProtect;
import revamp.modules.Category;
import revamp.modules.Module;
import java.util.ArrayList;
import revamp.utilities.impl.time.Time;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.network.NetworkPlayerInfo;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.inventory.GuiInventory;
import revamp.utilities.impl.render.Items;
import revamp.utilities.impl.render.Rainbow;
import java.util.Objects;
import net.minecraft.util.MathHelper;
import revamp.modules.impl.combat.Aura;
import net.minecraft.client.gui.ScaledResolution;
import java.util.Iterator;
import net.minecraft.client.gui.FontRenderer;
import revamp.friend.FriendManager;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.client.gui.Gui;
import java.awt.Color;
import revamp.Revamp;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;

public class HUD extends GuiIngame
{
    public HUD(final Minecraft mcIn) {
        super(mcIn);
    }
    
    @Override
    public void renderGameOverlay(final float p_175180_1_) {
        super.renderGameOverlay(p_175180_1_);
        if (Revamp.moduleManager.getModuleByName("TargetHUD").isToggled()) {
            this.drawTargetHUD();
        }
        if (Revamp.moduleManager.getModuleByName("HUD").isToggled()) {
            this.drawHUD();
        }
        if (Revamp.moduleManager.getModuleByName("Radar").isToggled()) {
            this.drawRadar();
        }
    }
    
    private void drawRadar() {
        final Minecraft mc = Minecraft.getMinecraft();
        final FontRenderer fr = mc.fontRendererObj;
        final float x = this.getSetting("X Pos", "Radar").getCurrentValue();
        final float y = this.getSetting("Y Pos", "Radar").getCurrentValue();
        Gui.drawRect(x - 1.0, y - 1.0, x + 92.0f, y + 92.0f, new Color(0, 0, 0, (int)this.getSetting("Opacity", "Radar").getCurrentValue()).getRGB());
        Gui.drawRect(x + 1.0, y + 1.0, x + 90.0f, y + 90.0f, new Color(0, 0, 0, (int)this.getSetting("Opacity", "Radar").getCurrentValue()).getRGB());
        for (final Entity e : mc.theWorld.loadedEntityList) {
            if (e instanceof EntityPlayer && e != mc.thePlayer) {
                if (e instanceof EntityArmorStand) {
                    continue;
                }
                float xpos = x;
                float ypos = y;
                xpos = (float)(e.posX - mc.thePlayer.posX + 44.0 + x);
                ypos = (float)(e.posZ - mc.thePlayer.posZ + 44.0 + y);
                if (xpos >= x + 88.0f || ypos >= y + 88.0f || ypos <= y + 2.0f) {
                    continue;
                }
                if (xpos <= x + 2.0f) {
                    continue;
                }
                Gui.drawRect(xpos, ypos, xpos + 1.0, ypos + 1.0, FriendManager.isFriend(e.getName()) ? Color.green.getRGB() : Color.red.getRGB());
            }
        }
        Gui.drawRect(x + 44.0f, y + 44.0f, x + 45.0f, y + 45.0f, -1);
    }
    
    private void drawTargetHUD() {
        final Minecraft mc = Minecraft.getMinecraft();
        final ScaledResolution sr = new ScaledResolution(mc);
        final FontRenderer fr = mc.fontRendererObj;
        final Aura aura = (Aura)Revamp.moduleManager.getModuleByName("Aura");
        if (aura.isToggled() && aura.target != null) {
            final float x = this.getSetting("X Pos", "TargetHUD").getCurrentValue();
            final float y = this.getSetting("Y Pos", "TargetHUD").getCurrentValue();
            if (this.getSetting("Mode", "TargetHUD").getCurrentMode().equalsIgnoreCase("Revamp")) {
                final double hpWidth = 92.0 * MathHelper.clamp_double(aura.target.getHealth() / aura.target.getMaxHealth(), 0.0, 1.0);
                final int range = (int)mc.thePlayer.getDistanceToEntity(aura.target);
                final NetworkPlayerInfo playerinfo = mc.getNetHandler().getPlayerInfo(aura.target.getUniqueID());
                final String ping = Objects.isNull(playerinfo) ? "0ms" : (String.valueOf((playerinfo.getResponseTime() > 100) ? "§c" : "§a") + playerinfo.getResponseTime() + "ms");
                final String healthStr = String.valueOf((int)aura.target.getHealth() * 2 / 2.0f);
                String match;
                if ((int)aura.target.getHealth() * 2 / 2.0f == mc.thePlayer.getHealth()) {
                    match = "Tied";
                }
                else if ((int)aura.target.getHealth() * 2 / 2.0f > mc.thePlayer.getHealth()) {
                    match = "Losing";
                }
                else {
                    match = "Winning";
                }
                Gui.drawRect(x - 6.0, y - 6.0, x + 141.0f, y + 41.0f, new Color(0, 0, 0, (int)this.getSetting("Opacity", "TargetHUD").getCurrentValue()).getRGB());
                Gui.drawRect(x - 3.0, y - 3.0, x + 138.0f, y + 38.0f, new Color(0, 0, 0, (int)this.getSetting("Opacity", "TargetHUD").getCurrentValue()).getRGB());
                if (this.getSetting("Show Bar", "TargetHUD").isToggled()) {
                    Gui.drawRect(x + 47.0f, y + 39.0f, x + 139.0f, y + 28.0f, Rainbow.breath(0.8, 50.0, 1.2, 0.3, (aura.target.getHealth() / 2.0 < 3.0) ? Color.RED.darker().darker() : Revamp.getClientCOLOR().darker().darker()));
                    Gui.drawRect(x + 47.0f, y + 39.0f, x + 47.0f + hpWidth, y + 28.0f, Rainbow.breath(0.8, 100.0, 1.2, 0.3, (aura.target.getHealth() / 2.0 < 3.0) ? Color.RED : Revamp.getClientCOLOR()));
                }
                if (this.getSetting("Show Health", "TargetHUD").isToggled()) {
                    fr.drawStringWithShadow(String.valueOf(healthStr) + " hearts", x + 76.0f - fr.getStringWidth(String.valueOf(healthStr) + " hearts") / 2.0f, y + 30.0f, -1);
                }
                fr.drawStringWithShadow(String.valueOf(range) + " blocks", x + 27.0f, y + 8.0f, -1);
                if (this.getSetting("Show Ping", "TargetHUD").isToggled()) {
                    fr.drawStringWithShadow(ping, x + 125.0f - fr.getStringWidth(ping) / 2.0f, y - 2.5, -1);
                }
                fr.drawStringWithShadow(match, x + 27.0f, y + 18.0f, -1);
                fr.drawStringWithShadow(aura.target.getName(), x + 27.0f, y - 2.0f, -1);
                if (this.getSetting("Show Armor", "TargetHUD").isToggled()) {
                    for (int i = 1; i < 5; ++i) {
                        if (aura.target.getEquipmentInSlot(i) != null) {
                            Items.renderItem(aura.target.getEquipmentInSlot(i), (int)(x + 176.0f - (int)(i * 20 / 1.3) - 40.0f), (int)(y + 3.0f));
                        }
                    }
                }
                if (mc.theWorld != null) {
                    GuiInventory.drawEntityOnScreen((int)(x + 10.0f), (int)(y + 39.0f), 28, 0.0f, 0.0f, aura.target);
                }
            }
            if (this.getSetting("Mode", "TargetHUD").getCurrentMode().equalsIgnoreCase("Dev")) {
                float xPos;
                if (aura.target instanceof AbstractClientPlayer) {
                    xPos = 38.0f;
                }
                else {
                    xPos = -3.0f;
                }
                float offset;
                if (this.getSetting("Show Ping", "TargetHUD").isToggled()) {
                    offset = 0.0f;
                }
                else {
                    offset = -10.0f;
                }
                Gui.drawRect(x - 8.0, y - 8.0, x + 141.0f, y + 41.0f, new Color(0, 0, 0, (int)this.getSetting("Opacity", "TargetHUD").getCurrentValue()).getRGB());
                Gui.drawRect(x - 5.0, y - 5.0, x + 138.0f, y + 38.0f, new Color(0, 0, 0, (int)this.getSetting("Opacity", "TargetHUD").getCurrentValue()).getRGB());
                fr.drawStringWithShadow(aura.target.getName(), x + xPos, y - 3.0f, -1);
                final int range = (int)mc.thePlayer.getDistanceToEntity(aura.target);
                final NetworkPlayerInfo playerinfo = mc.getNetHandler().getPlayerInfo(aura.target.getUniqueID());
                final String ping = Objects.isNull(playerinfo) ? "0ms" : (String.valueOf((playerinfo.getResponseTime() > 100) ? "§c" : "§a") + playerinfo.getResponseTime() + "ms");
                final String healthStr = String.valueOf((int)aura.target.getHealth() * 2 / 2.0f);
                if (this.getSetting("Show Health", "TargetHUD").isToggled()) {
                    fr.drawStringWithShadow(String.valueOf(healthStr) + " hearts", x + xPos, y + 27.0f + offset, -1);
                }
                fr.drawStringWithShadow(String.valueOf(range) + " blocks", x + xPos, y + 7.0f, -1);
                if (this.getSetting("Show Ping", "TargetHUD").isToggled()) {
                    fr.drawStringWithShadow(ping, x + xPos, y + 17.0f, -1);
                }
                if (aura.target instanceof AbstractClientPlayer) {
                    mc.getTextureManager().bindTexture(((AbstractClientPlayer)aura.target).getLocationSkin());
                    GL11.glEnable(3042);
                    GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                    Gui.drawScaledCustomSizeModalRect((int)(x - 3.0f), (int)(y - 3.0f), 8.1f, 8.0f, 8, 8, 40, 40, 65.0f, 65.0f);
                    GL11.glDisable(3042);
                }
            }
        }
    }
    
    private void drawHUD() {
        final Minecraft mc = Minecraft.getMinecraft();
        final ScaledResolution sr = new ScaledResolution(mc);
        final FontRenderer fr = mc.fontRendererObj;
        final boolean rainbow = this.getSetting("Rainbow", "HUD").isToggled();
        String information;
        if (this.getSetting("Title Mode", "HUD").getCurrentMode().equalsIgnoreCase(this.getSetting("Arraylist Mode", "HUD").getCurrentMode())) {
            information = this.getSetting("Title Mode", "HUD").getCurrentMode();
        }
        else if (this.getSetting("Title Mode", "HUD").getCurrentMode().equalsIgnoreCase("Revamp") && this.getSetting("Arraylist Mode", "HUD").getCurrentMode().equalsIgnoreCase("Clean")) {
            information = "Clevamp";
        }
        else if (this.getSetting("Title Mode", "HUD").getCurrentMode().equalsIgnoreCase("Revamp") && this.getSetting("Arraylist Mode", "HUD").getCurrentMode().equalsIgnoreCase("Stacking")) {
            information = "Stackvamp";
        }
        else if (this.getSetting("Title Mode", "HUD").getCurrentMode().equalsIgnoreCase("Skeet") && this.getSetting("Arraylist Mode", "HUD").getCurrentMode().equalsIgnoreCase("Clean")) {
            information = "Cleet";
        }
        else if (this.getSetting("Title Mode", "HUD").getCurrentMode().equalsIgnoreCase("Skeet") && this.getSetting("Arraylist Mode", "HUD").getCurrentMode().equalsIgnoreCase("Revamp")) {
            information = "Resense";
        }
        else if (this.getSetting("Title Mode", "HUD").getCurrentMode().equalsIgnoreCase("Skeet") && this.getSetting("Arraylist Mode", "HUD").getCurrentMode().equalsIgnoreCase("Jello")) {
            information = "Skello";
        }
        else if (this.getSetting("Title Mode", "HUD").getCurrentMode().equalsIgnoreCase("Skeet") && this.getSetting("Arraylist Mode", "HUD").getCurrentMode().equalsIgnoreCase("Stacking")) {
            information = "Stackeet";
        }
        else if (this.getSetting("Title Mode", "HUD").getCurrentMode().equalsIgnoreCase("Jello") && this.getSetting("Arraylist Mode", "HUD").getCurrentMode().equalsIgnoreCase("Stacking")) {
            information = "Jellcking";
        }
        else if (this.getSetting("Title Mode", "HUD").getCurrentMode().equalsIgnoreCase("Jello") && this.getSetting("Arraylist Mode", "HUD").getCurrentMode().equalsIgnoreCase("Revamp")) {
            information = "Rello";
        }
        else if (this.getSetting("Title Mode", "HUD").getCurrentMode().equalsIgnoreCase("Jello") && this.getSetting("Arraylist Mode", "HUD").getCurrentMode().equalsIgnoreCase("Clean")) {
            information = "Clello";
        }
        else {
            information = "N/A";
        }
        Revamp.moduleManager.getModuleByName("HUD").setNameWithOutSpace("HUD" + (this.getSetting("Show Information", "HUD").isToggled() ? (" §7" + information) : ""));
        if (this.getSetting("Hotbar", "HUD").isToggled() && !(mc.currentScreen instanceof GuiChat)) {
            final double bps = Math.hypot(mc.thePlayer.posX - mc.thePlayer.prevPosX, mc.thePlayer.posZ - mc.thePlayer.prevPosZ) * mc.timer.timerSpeed * 20.0;
            Gui.drawRect(0.0, sr.getScaledHeight() - 22, sr.getScaledWidth(), sr.getScaledHeight(), 1879048192);
            if (mc.thePlayer.inventory.currentItem == 0) {
                Gui.drawRect(sr.getScaledWidth() / 2 - 91 + mc.thePlayer.inventory.currentItem * 20, sr.getScaledHeight() - 22, sr.getScaledWidth() / 2 + 91 - 160, sr.getScaledHeight(), -1140850689);
            }
            else {
                Gui.drawRect(sr.getScaledWidth() / 2 - 91 + mc.thePlayer.inventory.currentItem * 20, sr.getScaledHeight() - 22, sr.getScaledWidth() / 2 + 91 - 20 * (8 - mc.thePlayer.inventory.currentItem), sr.getScaledHeight(), -1140850689);
            }
            if (this.getSetting("Show XYZ", "HUD").isToggled()) {
                fr.drawStringWithShadow("[XYZ] " + Math.round(mc.thePlayer.posX) + " " + Math.round(mc.thePlayer.posY) + " " + Math.round(mc.thePlayer.posZ), 1.0f, sr.getScaledHeight() - 10, -1);
            }
            if (this.getSetting("Show FPS", "HUD").isToggled()) {
                fr.drawStringWithShadow("[FPS] " + Minecraft.getDebugFPS(), 1.0f, sr.getScaledHeight() - 20, -1);
            }
            if (this.getSetting("Show BPS", "HUD").isToggled()) {
                fr.drawStringWithShadow("[BPS] " + Math.round(bps * 100.0) / 100.0, (float)(this.getSetting("Show FPS", "HUD").isToggled() ? (this.getSetting("Show XYZ", "HUD").isToggled() ? (fr.getStringWidth("[FPS] " + Minecraft.getDebugFPS()) + 5) : 1) : 1), sr.getScaledHeight() - (this.getSetting("Show XYZ", "HUD").isToggled() ? 20 : 10), -1);
            }
            fr.drawStringWithShadow(Time.currentTime(true, ":"), (float)(sr.getScaledWidth() - 42), sr.getScaledHeight() - 20, -1);
            fr.drawStringWithShadow(Time.currentDate("/", false), (float)(sr.getScaledWidth() - 50), sr.getScaledHeight() - 10, -1);
        }
        if (mc.gameSettings.showDebugInfo) {
            return;
        }
        final ArrayList<Module> modules = new ArrayList<Module>();
        for (final Module i : Revamp.getModules()) {
            if (i.isToggled()) {
                if (i.getCategory() == Category.RENDER && !this.getSetting("Show Render Modules", "HUD").isToggled()) {
                    continue;
                }
                modules.add(i);
            }
        }
        final FontRenderer fontRenderer;
        modules.sort((m1, m2) -> fontRenderer.getStringWidth(this.getSetting("Lowercase", "HUD").isToggled() ? m2.getNameWithOutSpace().toLowerCase() : m2.getNameWithOutSpace()) - fontRenderer.getStringWidth(this.getSetting("Lowercase", "HUD").isToggled() ? m1.getNameWithOutSpace().toLowerCase() : m1.getNameWithOutSpace()));
        if (this.getSetting("Title", "HUD").isToggled()) {
            if (this.getSetting("Title Mode", "HUD").getCurrentMode().equalsIgnoreCase("Revamp")) {
                float offset = 0.0f;
                for (int j = 0; j < Revamp.getNAME().length(); ++j) {
                    final String character = String.valueOf(Revamp.getNAME().charAt(j));
                    fr.drawStringWithShadow(character, 4.0f + offset, 4.0, rainbow ? this.rainbowMode(j) : Revamp.getClientCOLOR().getRGB());
                    fr.drawStringWithShadow(Revamp.getVERSION(), 43.0f, 4.0, -1);
                    offset += (float)(fr.getStringWidth(character) + 0.04);
                }
            }
            else if (this.getSetting("Title Mode", "HUD").getCurrentMode().equalsIgnoreCase("Skeet")) {
                final Module nameprotect = Revamp.moduleManager.getModuleByName("NameProtect");
                int responseTime = 0;
                Label_1526: {
                    if (mc.thePlayer != null) {
                        final NetHandlerPlayClient netHandler = mc.getNetHandler();
                        final EntityPlayerSP thePlayer = mc.thePlayer;
                        if (netHandler.getPlayerInfo(EntityPlayer.getOfflineUUID(mc.getSession().getUsername())) != null) {
                            final NetHandlerPlayClient netHandler2 = mc.getNetHandler();
                            final EntityPlayerSP thePlayer2 = mc.thePlayer;
                            responseTime = netHandler2.getPlayerInfo(EntityPlayer.getOfflineUUID(mc.getSession().getUsername())).getResponseTime();
                            break Label_1526;
                        }
                    }
                    responseTime = 0;
                }
                final int ping = responseTime;
                Gui.drawRect(3.0, 3.0, 56 + fr.getStringWidth(String.valueOf(nameprotect.isToggled() ? NameProtect.formattedName : mc.getSession().getUsername()) + " |" + ping + "ms"), 13.0, 1342177280);
                Gui.drawRect(2.0, 2.0, 57 + fr.getStringWidth(String.valueOf(nameprotect.isToggled() ? NameProtect.formattedName : mc.getSession().getUsername()) + " |" + ping + "ms"), 14.0, 1342177280);
                fr.drawStringWithShadow(String.valueOf(ping) + "ms", (float)(60 + fr.getStringWidth(nameprotect.isToggled() ? NameProtect.formattedName : mc.getSession().getUsername())), 4.0, (ping > 150) ? Color.RED.getRGB() : Color.GREEN.getRGB());
                fr.drawStringWithShadow("re", 4.0f, 4.0, -1);
                fr.drawStringWithShadow("sense", 16.0f, 4.0, rainbow ? this.rainbowMode(1) : Revamp.getClientCOLOR().getRGB());
                fr.drawStringWithShadow(" | " + (nameprotect.isToggled() ? NameProtect.formattedName : mc.getSession().getUsername()) + " §r|", 45.0f, 4.0, -1);
            }
            else if (this.getSetting("Title Mode", "HUD").getCurrentMode().equalsIgnoreCase("Jello")) {
                GL11.glPushMatrix();
                GL11.glScalef(2.0f, 2.0f, 2.0f);
                fr.drawStringWithShadow(Revamp.getNAME(), 2.0f, 3.0, rainbow ? this.rainbowMode(0) : Revamp.getClientCOLOR().getRGB());
                GL11.glPopMatrix();
                fr.drawStringWithShadow(Revamp.getVERSION(), 4.0f, 23.0, rainbow ? this.rainbowMode(1) : Revamp.getClientCOLOR().getRGB());
            }
        }
        if (this.getSetting("Arraylist", "HUD").isToggled()) {
            if (this.getSetting("Arraylist Mode", "HUD").getCurrentMode().equalsIgnoreCase("Revamp")) {
                int count = 0;
                for (final Module k : modules) {
                    final double offset2 = count * fr.FONT_HEIGHT + 4;
                    final String casecheck = this.getSetting("Lowercase", "HUD").isToggled() ? k.getNameWithOutSpace().toLowerCase() : k.getNameWithOutSpace();
                    if (k.slide != sr.getScaledWidth() - fr.getStringWidth(casecheck) + sr.getScaledWidth()) {
                        final Module module = k;
                        ++module.slide;
                    }
                    Gui.drawRect(sr.getScaledWidth() / (this.getSetting("Animations Mode", "HUD").getCurrentMode().equalsIgnoreCase("Revamp") ? k.slide : 1000) + sr.getScaledWidth() - 2, offset2, sr.getScaledWidth(), fr.FONT_HEIGHT + offset2, rainbow ? this.rainbowMode(count) : Revamp.getClientCOLOR().getRGB());
                    fr.drawStringWithShadow(casecheck, (float)(sr.getScaledWidth() / (this.getSetting("Animations Mode", "HUD").getCurrentMode().equalsIgnoreCase("Revamp") ? k.slide : 1000) - fr.getStringWidth(casecheck) + sr.getScaledWidth() - 4), offset2, rainbow ? this.rainbowMode(count) : Revamp.getClientCOLOR().getRGB());
                    ++count;
                }
            }
            else if (this.getSetting("Arraylist Mode", "HUD").getCurrentMode().equalsIgnoreCase("Clean")) {
                int count = 0;
                for (final Module k : modules) {
                    final double offset2 = count * 10 + fr.FONT_HEIGHT + 4;
                    final String casecheck = this.getSetting("Lowercase", "HUD").isToggled() ? k.getNameWithOutSpace().toLowerCase() : k.getNameWithOutSpace();
                    if (k.slide != sr.getScaledWidth() - fr.getStringWidth(casecheck) + sr.getScaledWidth()) {
                        final Module module2 = k;
                        ++module2.slide;
                    }
                    if (k.getName().equalsIgnoreCase(modules.get(0).getName())) {
                        Gui.drawRect(sr.getScaledWidth() / (this.getSetting("Animations Mode", "HUD").getCurrentMode().equalsIgnoreCase("Revamp") ? k.slide : 1000) + sr.getScaledWidth() - fr.getStringWidth(casecheck) - 6, 0.0, sr.getScaledWidth(), 1.0, rainbow ? this.rainbowMode(count) : Revamp.getClientCOLOR().getRGB());
                    }
                    Gui.drawRect(sr.getScaledWidth() / (this.getSetting("Animations Mode", "HUD").getCurrentMode().equalsIgnoreCase("Revamp") ? k.slide : 1000) + sr.getScaledWidth() - 1, offset2 - 13.0, sr.getScaledWidth(), fr.FONT_HEIGHT + offset2 - 11.0, rainbow ? this.rainbowMode(count) : Revamp.getClientCOLOR().getRGB());
                    Gui.drawRect(sr.getScaledWidth() / (this.getSetting("Animations Mode", "HUD").getCurrentMode().equalsIgnoreCase("Revamp") ? k.slide : 1000) - fr.getStringWidth(casecheck) + sr.getScaledWidth() - 6, offset2 - 12.0, sr.getScaledWidth() - 1, fr.FONT_HEIGHT + offset2 - 11.0, Integer.MIN_VALUE);
                    fr.drawStringWithShadow(casecheck, (float)(sr.getScaledWidth() / (this.getSetting("Animations Mode", "HUD").getCurrentMode().equalsIgnoreCase("Revamp") ? k.slide : 1000) - fr.getStringWidth(casecheck) + sr.getScaledWidth() - 3), offset2 - 11.0, rainbow ? this.rainbowMode(count) : Revamp.getClientCOLOR().getRGB());
                    ++count;
                }
            }
            else if (this.getSetting("Arraylist Mode", "HUD").getCurrentMode().equalsIgnoreCase("Stacking")) {
                int count = 0;
                for (final Module k : modules) {
                    final double offset2 = count * 10 + fr.FONT_HEIGHT + 4;
                    final String casecheck = this.getSetting("Lowercase", "HUD").isToggled() ? k.getNameWithOutSpace().toLowerCase() : k.getNameWithOutSpace();
                    if (k.slide != sr.getScaledWidth() - fr.getStringWidth(casecheck) + sr.getScaledWidth()) {
                        final Module module3 = k;
                        ++module3.slide;
                    }
                    Gui.drawRect(sr.getScaledWidth() / (this.getSetting("Animations Mode", "HUD").getCurrentMode().equalsIgnoreCase("Revamp") ? k.slide : 1000) - fr.getStringWidth(casecheck) + sr.getScaledWidth() - 4, offset2 - 14.0, sr.getScaledWidth(), fr.FONT_HEIGHT + offset2 - 11.0, rainbow ? this.rainbowMode(count) : Revamp.getClientCOLOR().getRGB());
                    Gui.drawRect(sr.getScaledWidth() / (this.getSetting("Animations Mode", "HUD").getCurrentMode().equalsIgnoreCase("Revamp") ? k.slide : 1000) - fr.getStringWidth(casecheck) + sr.getScaledWidth() - 3, offset2 - 13.0, sr.getScaledWidth() - 1, fr.FONT_HEIGHT + offset2 - 12.0, Integer.MIN_VALUE);
                    if (k.getName().equalsIgnoreCase(modules.get(0).getName())) {
                        Gui.drawRect(sr.getScaledWidth() / (this.getSetting("Animations Mode", "HUD").getCurrentMode().equalsIgnoreCase("Revamp") ? k.slide : 1000) + sr.getScaledWidth() - fr.getStringWidth(casecheck) - 4, 0.0, sr.getScaledWidth(), 1.0, rainbow ? this.rainbowMode(count) : Revamp.getClientCOLOR().getRGB());
                    }
                    fr.drawStringWithShadow(casecheck, (float)(sr.getScaledWidth() / (this.getSetting("Animations Mode", "HUD").getCurrentMode().equalsIgnoreCase("Revamp") ? k.slide : 1000) - fr.getStringWidth(casecheck) + sr.getScaledWidth() - 1), offset2 - 12.0, rainbow ? this.rainbowMode(count) : Revamp.getClientCOLOR().getRGB());
                    ++count;
                }
            }
        }
    }
    
    private Setting getSetting(final String setting, final String module) {
        return Revamp.settingManager.getSetting(Revamp.moduleManager.getModuleByName(module), setting);
    }
    
    private int rainbowMode(final int offset) {
        if (this.getSetting("Rainbow Mode", "HUD").getCurrentMode().equalsIgnoreCase("Regular")) {
            return Rainbow.rainbow(this.getSetting("Rainbow Seconds", "HUD").getCurrentValue(), 0.5f, 1.0f, offset * 200);
        }
        return Rainbow.breath(this.getSetting("Rainbow Seconds", "HUD").getCurrentValue(), offset, 1.2, 0.3, (Revamp.getClientCOLOR() == Color.BLACK) ? new Color(50, 50, 50) : Revamp.getClientCOLOR());
    }
}
