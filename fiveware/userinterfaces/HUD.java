package fiveware.userinterfaces;

import fiveware.Fiveware;
import fiveware.modules.Category;
import fiveware.modules.Module;
import fiveware.userinterfaces.clickgui.settings.Setting;
import fiveware.userinterfaces.test.NotificationManager;
import fiveware.utilities.render.Rainbow;
import fiveware.utilities.time.Time;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class HUD extends GuiIngame {
   float f1;
   float f2;

   public HUD(Minecraft mcIn) {
      super(mcIn);
   }

   public void renderGameOverlay(float p_175180_1_) {
      super.renderGameOverlay(p_175180_1_);
      NotificationManager.render();
      if (Fiveware.moduleManager.getModuleByName("HUD").isToggled()) {
         this.drawHUD();
      }

   }

   private void drawHUD() {
      Minecraft mc = Minecraft.getMinecraft();
      ScaledResolution sr = new ScaledResolution(mc);
      FontRenderer fr = mc.fontRendererObj;
      boolean rainbow = this.getSetting("Rainbow").isToggled();
      String name;
      if (this.getSetting("Hotbar").isToggled()) {
         double bps = Math.hypot(mc.thePlayer.posX - mc.thePlayer.prevPosX, mc.thePlayer.posZ - mc.thePlayer.prevPosZ) * (double)mc.timer.timerSpeed * 20.0D;
         String xyz = Math.round(mc.thePlayer.posX) + " " + Math.round(mc.thePlayer.posY) + " " + Math.round(mc.thePlayer.posZ);
         float offset;
         int i;
         if (this.getSetting("Show XYZ").isToggled()) {
            offset = 0.0F;

            for(i = 0; i < "[XYZ]".length(); ++i) {
               name = String.valueOf("[XYZ]".charAt(i));
               this.drawString(name, 2.0F + offset + this.f2 * 2.0F, (float)(sr.getScaledHeight() - 10), rainbow ? this.rainbowMode(i) : -1);
               this.drawString(xyz, 31.0F + this.f2 * 2.0F, (float)(sr.getScaledHeight() - 10), -1);
               offset = (float)((double)offset + (double)fr.getStringWidth(name) + 0.04D);
            }
         }

         if (this.getSetting("Show BPS").isToggled()) {
            offset = 0.0F;

            for(i = 0; i < "[BPS]".length(); ++i) {
               name = String.valueOf("[BPS]".charAt(i));
               this.drawString(name, 2.0F + offset + this.f2 * 2.0F, (float)(sr.getScaledHeight() - (this.getSetting("Show XYZ").isToggled() ? 20 : 10)), rainbow ? this.rainbowMode(i + 2) : Fiveware.getClientCOLOR().getRGB());
               this.drawString(String.valueOf((double)Math.round(bps * 100.0D) / 100.0D), 31.0F + this.f2 * 2.0F, (float)(sr.getScaledHeight() - (this.getSetting("Show XYZ").isToggled() ? 20 : 10)), -1);
               offset = (float)((double)offset + (double)fr.getStringWidth(name) + 0.04D);
            }
         }

         if (this.getSetting("Show FPS").isToggled()) {
            offset = 0.0F;

            for(i = 0; i < "[FPS]".length(); ++i) {
               name = String.valueOf("[FPS]".charAt(i));
               this.drawString(name, 2.0F + offset + this.f2 * 2.0F, (float)(sr.getScaledHeight() - (this.getSetting("Show XYZ").isToggled() ? (this.getSetting("Show BPS").isToggled() ? 30 : 20) : (this.getSetting("Show BPS").isToggled() ? 20 : 10))), rainbow ? this.rainbowMode(i + 4) : Fiveware.getClientCOLOR().getRGB());
               this.drawString(String.valueOf(Minecraft.getDebugFPS()), 31.0F + this.f2 * 2.0F, (float)(sr.getScaledHeight() - (this.getSetting("Show XYZ").isToggled() ? (this.getSetting("Show BPS").isToggled() ? 30 : 20) : (this.getSetting("Show BPS").isToggled() ? 20 : 10))), -1);
               offset = (float)((double)offset + (double)fr.getStringWidth(name) + 0.04D);
            }
         }

         if (mc.currentScreen instanceof GuiChat && this.f1 != 13.0F) {
            ++this.f1;
         } else if (!(mc.currentScreen instanceof GuiChat) && this.f1 != 0.0F) {
            --this.f1;
         }

         if (mc.currentScreen instanceof GuiChat && this.f2 != -60.0F) {
            this.f2 -= 2.0F;
         } else if (!(mc.currentScreen instanceof GuiChat) && this.f2 != 0.0F) {
            this.f2 += 2.0F;
         }

         this.drawString("§f" + Fiveware.getUSERNAME() + "§r | §f" + Time.currentTime(true, ":"), (float)(sr.getScaledWidth() - 50 - fr.getStringWidth(Fiveware.getUSERNAME())), (float)(sr.getScaledHeight() - 10) - this.f1, rainbow ? this.rainbowMode(0) : Fiveware.getClientCOLOR().getRGB());
      }

      if (!mc.gameSettings.showDebugInfo) {
         ArrayList<Module> modules = new ArrayList();
         boolean lowercase = this.getSetting("Lowercase").isToggled();
         boolean spaced = this.getSetting("Spaced Name").isToggled();
         boolean left = this.getSetting("Arraylist Align").getCurrentMode().equalsIgnoreCase("Left");
         boolean oldtena = this.getSetting("Outline Mode").getCurrentMode().equalsIgnoreCase("Old Tenacity");
         Iterator var11 = Fiveware.getModules().iterator();

         while(true) {
            Module m;
            do {
               do {
                  if (!var11.hasNext()) {
                     modules.sort((m1, m2) -> {
                        return fr.getStringWidth(lowercase ? (left ? m2.getInfo().toLowerCase() : "") + (spaced ? m2.getName().toLowerCase() : m2.getNameWithOutSpace().toLowerCase()) + (left ? "" : (m2.getInfo().isEmpty() ? "" : " ") + m2.getInfo().toLowerCase()) : (left ? m2.getInfo() : "") + (spaced ? m2.getName() : m2.getNameWithOutSpace()) + (left ? "" : (m2.getInfo().isEmpty() ? "" : " ") + m2.getInfo())) - fr.getStringWidth(lowercase ? (left ? m1.getInfo().toLowerCase() : "") + (spaced ? m1.getName().toLowerCase() : m1.getNameWithOutSpace().toLowerCase()) + (left ? "" : (m1.getInfo().isEmpty() ? "" : " ") + m1.getInfo().toLowerCase()) : (left ? m1.getInfo() : "") + (spaced ? m1.getName() : m1.getNameWithOutSpace()) + (left ? "" : (m1.getInfo().isEmpty() ? "" : " ") + m1.getInfo()));
                     });
                     if (this.getSetting("Title").isToggled()) {
                        float offset;
                        int i;
                        String character;
                        name = this.getSetting("Title Text").getTyped().isEmpty() ? "fiveware" : this.getSetting("Title Text").getTyped();
                        String var24;
                        label322:
                        switch((var24 = this.getSetting("Title Mode").getCurrentMode().toLowerCase()).hashCode()) {
                        case -135332064:
                           if (var24.equals("one letter")) {
                              GL11.glPushMatrix();
                              GL11.glScalef(1.5F, 1.5F, 1.5F);
                              this.drawString((this.getSetting("Bold Title").isToggled() ? "§l" : "") + name.substring(0, 1) + "§f" + (this.getSetting("Bold Title").isToggled() ? "§l" : "") + name.substring(1), 4.0F, 4.0F, rainbow ? this.rainbowMode(1) : Fiveware.getClientCOLOR().getRGB());
                              GL11.glPopMatrix();
                           }
                           break;
                        case 96673:
                           if (!var24.equals("all")) {
                              break;
                           }

                           offset = 0.0F;
                           i = 0;

                           while(true) {
                              if (i >= name.length()) {
                                 break label322;
                              }

                              character = String.valueOf(name.charAt(i));
                              GL11.glPushMatrix();
                              GL11.glScalef(1.5F, 1.5F, 1.5F);
                              this.drawString((this.getSetting("Bold Title").isToggled() ? "§l" : "") + character, 4.0F + offset, 4.0F, rainbow ? this.rainbowMode(i) : Fiveware.getClientCOLOR().getRGB());
                              GL11.glPopMatrix();
                              offset = (float)((double)offset + (double)fr.getStringWidth(character) + (this.getSetting("Bold Title").isToggled() ? 0.49D : 0.0D));
                              ++i;
                           }
                        case 3387192:
                           if (var24.equals("none")) {
                              GL11.glPushMatrix();
                              GL11.glScalef(1.5F, 1.5F, 1.5F);
                              this.drawString((this.getSetting("Bold Title").isToggled() ? "§l" : "") + name, 4.0F, 4.0F, Fiveware.getClientCOLOR().getRGB());
                              GL11.glPopMatrix();
                           }
                           break;
                        case 101009364:
                           if (var24.equals("jello")) {
                              GlStateManager.pushMatrix();
                              GlStateManager.scale(0.2D, 0.2D, 0.2D);
                              GlStateManager.enableBlend();
                              mc.getTextureManager().bindTexture(new ResourceLocation("fiveware/jello.png"));
                              drawModalRectWithCustomSizedTexture(-40, -15, 0.0F, 0.0F, 660, 181, 660.0F, 181.0F);
                              GlStateManager.disableBlend();
                              GlStateManager.popMatrix();
                           }
                        }

                        if (name.equals("fiveware") && !this.getSetting("Title Mode").getCurrentMode().equalsIgnoreCase("Jello")) {
                           offset = 0.0F;

                           for(i = 0; i < Fiveware.getVERSION().length(); ++i) {
                              character = String.valueOf(Fiveware.getVERSION().charAt(i));
                              this.drawString(character, (float)(this.getSetting("Bold Title").isToggled() ? 78 : 72) + offset, 4.0F, rainbow ? this.rainbowMode(i) : Fiveware.getClientCOLOR().getRGB());
                              offset += (float)fr.getStringWidth(character);
                           }
                        }
                     }

                     if (this.getSetting("Arraylist").isToggled()) {
                        int count = 0;

                        for(Iterator var27 = modules.iterator(); var27.hasNext(); ++count) {
                           Module m = (Module)var27.next();
                           double offset = (double)(count * 10 + fr.FONT_HEIGHT + 4);
                           boolean right = this.getSetting("Arraylist Align").getCurrentMode().equalsIgnoreCase("Right");
                           int x = (int)this.getSetting("Pos X").getCurrentValue();
                           int y = right ? (int)this.getSetting("Pos Y").getCurrentValue() : -((int)this.getSetting("Pos y").getCurrentValue());
                           String name = lowercase ? (left ? m.getInfo().toLowerCase() : "") + (spaced ? m.getName().toLowerCase() : m.getNameWithOutSpace().toLowerCase()) + (left ? "" : (m.getInfo().isEmpty() ? "" : " ") + m.getInfo().toLowerCase()) : (left ? m.getInfo() : "") + (spaced ? m.getName() : m.getNameWithOutSpace()) + (left ? "" : (m.getInfo().isEmpty() ? "" : " ") + m.getInfo());
                           if (right) {
                              if (this.getSetting("Background").isToggled()) {
                                 Gui.drawRect((double)(sr.getScaledWidth() / 1000 - fr.getStringWidth(name) + sr.getScaledWidth() - 3 - x), offset - 13.0D + (double)y, (double)(sr.getScaledWidth() - x), (double)fr.FONT_HEIGHT + offset - 12.0D + (double)y, Integer.MIN_VALUE);
                              }

                              if (this.getSetting("Outline").isToggled()) {
                                 if (!oldtena) {
                                    if (m.getName().equalsIgnoreCase(((Module)modules.get(0)).getName())) {
                                       Gui.drawRect((double)(sr.getScaledWidth() - fr.getStringWidth(name) - 3 - x), (double)(y - 1), (double)(sr.getScaledWidth() - x), (double)y, rainbow ? this.rainbowMode(count) : Fiveware.getClientCOLOR().getRGB());
                                    }

                                    Gui.drawRect((double)(sr.getScaledWidth() - 1 - x), offset - 13.0D + (double)y, (double)(sr.getScaledWidth() - x), (double)fr.FONT_HEIGHT + offset - 12.0D + (double)y, rainbow ? this.rainbowMode(count) : Fiveware.getClientCOLOR().getRGB());
                                 } else {
                                    Gui.drawRect((double)(sr.getScaledWidth() - fr.getStringWidth(name) - 3 - x), offset - 13.0D + (double)y, (double)(sr.getScaledWidth() - fr.getStringWidth(name)) - 4.5D - (double)x, (double)fr.FONT_HEIGHT + offset - 12.0D + (double)y, rainbow ? this.rainbowMode(count) : Fiveware.getClientCOLOR().getRGB());
                                 }
                              }

                              this.drawString(name, (float)(sr.getScaledWidth() / 1000 - fr.getStringWidth(name) + sr.getScaledWidth() - 1 - x), (float)(offset - 12.0D) + (float)y, rainbow ? this.rainbowMode(count) : Fiveware.getClientCOLOR().getRGB());
                           } else {
                              if (this.getSetting("Background").isToggled()) {
                                 Gui.drawRect((double)(fr.getStringWidth(name) + 3 + x), offset - 13.0D - (double)y, (double)x, (double)fr.FONT_HEIGHT + offset - 12.0D - (double)y, Integer.MIN_VALUE);
                              }

                              if (this.getSetting("Outline").isToggled()) {
                                 if (!oldtena) {
                                    if (m.getName().equalsIgnoreCase(((Module)modules.get(0)).getName())) {
                                       Gui.drawRect((double)(fr.getStringWidth(name) + 3 + x), offset - 14.0D - (double)y, (double)x, offset - 13.0D - (double)y, rainbow ? this.rainbowMode(count) : Fiveware.getClientCOLOR().getRGB());
                                    }

                                    Gui.drawRect((double)x, offset - 13.0D - (double)y, (double)(1 + x), (double)fr.FONT_HEIGHT + offset - 12.0D - (double)y, rainbow ? this.rainbowMode(count) : Fiveware.getClientCOLOR().getRGB());
                                 } else {
                                    Gui.drawRect((double)(fr.getStringWidth(name) + 5 + x), offset - 13.0D - (double)y, (double)(fr.getStringWidth(name) + 3 + x), (double)fr.FONT_HEIGHT + offset - 12.0D - (double)y, rainbow ? this.rainbowMode(count) : Fiveware.getClientCOLOR().getRGB());
                                 }
                              }

                              this.drawString(name, (float)(2 + x), (float)(offset - 12.0D) - (float)y, rainbow ? this.rainbowMode(count) : Fiveware.getClientCOLOR().getRGB());
                           }
                        }
                     }

                     return;
                  }

                  m = (Module)var11.next();
               } while(!m.isToggled());
            } while((!this.getSetting("Important").isToggled() || m.getCategory() == Category.RENDER) && this.getSetting("Important").isToggled());

            modules.add(m);
         }
      }
   }

   private Setting getSetting(String setting) {
      return Fiveware.settingManager.getSetting(Fiveware.moduleManager.getModuleByName("HUD"), setting);
   }

   private int rainbowMode(int offset) {
      if (this.getSetting("Rainbow Mode").getCurrentMode().equalsIgnoreCase("Regular")) {
         return Rainbow.rainbow(5.0F, 0.5F, 1.0F, (long)(offset * 200));
      } else {
         return this.getSetting("Rainbow Mode").getCurrentMode().equalsIgnoreCase("Astolfo") ? Rainbow.astolfo((float)(offset * 100), 3000.0F) : Rainbow.mixed(new Color(this.getMixed(1, "R"), this.getMixed(1, "G"), this.getMixed(1, "B")), new Color(this.getMixed(2, "R"), this.getMixed(2, "G"), this.getMixed(2, "B")), (float)offset);
      }
   }

   private int drawString(String text, float x, float y, int color) {
      FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
      return this.getSetting("Shadow").isToggled() ? fr.drawStringWithShadow(text, x, (double)y, color) : fr.drawString(text, x, y, color);
   }

   private float getMixed(int number, String rgb) {
      return Fiveware.settingManager.getSetting(Fiveware.moduleManager.getModuleByName("Mixed"), "Color " + number + " " + rgb).getCurrentValue();
   }
}
