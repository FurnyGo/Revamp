package fiveware.userinterfaces.clickgui;

import fiveware.Fiveware;
import fiveware.modules.Category;
import fiveware.userinterfaces.clickgui.elements.DrawCategory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class ClickGUI extends GuiScreen {
   public final Minecraft mc = Minecraft.getMinecraft();
   public final FontRenderer fr;
   public ArrayList<DrawCategory> drawCategories;
   public int x;
   public int y;
   public int width;
   public int height;

   public ClickGUI() {
      this.fr = this.mc.fontRendererObj;
      this.drawCategories = new ArrayList();
      this.x = 40;
      this.y = 40;
      this.width = 90;
      this.height = 15;
      Category[] var4;
      int var3 = (var4 = Category.values()).length;

      for(int var2 = 0; var2 < var3; ++var2) {
         Category category = var4[var2];
         this.drawCategories.add(new DrawCategory(category, this.x, this.y, this.width, this.height));
         this.y += 20;
      }

   }

   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
      ScaledResolution sr = new ScaledResolution(this.mc);
      fiveware.modules.impl.render.ClickGUI clickGui = (fiveware.modules.impl.render.ClickGUI)Fiveware.moduleManager.getModuleByName("ClickGUI");
      int offset = 0;

      for(Iterator var8 = this.drawCategories.iterator(); var8.hasNext(); ++offset) {
         DrawCategory drawCategory = (DrawCategory)var8.next();
         drawCategory.drawScreen(mouseX, mouseY, partialTicks, offset);
      }

      if (Fiveware.settingManager.getSetting(clickGui, "Anime").isToggled()) {
         this.mc.getTextureManager().bindTexture(new ResourceLocation("fiveware/anime/" + Fiveware.settingManager.getSetting(clickGui, "Character").getCurrentMode().toLowerCase() + ".png"));
         drawModalRectWithCustomSizedTexture(sr.getScaledWidth() - 300, sr.getScaledHeight() - 470, 0.0F, 0.0F, 300, 475, 300.0F, 475.0F);
      }

      if (Fiveware.settingManager.getSetting(clickGui, "Blur").isToggled() && OpenGlHelper.shadersSupported && this.mc.getRenderViewEntity() instanceof EntityPlayer) {
         if (this.mc.entityRenderer.theShaderGroup != null) {
            this.mc.entityRenderer.theShaderGroup.deleteShaderGroup();
         }

         this.mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/blur.json"));
      }

      super.drawScreen(mouseX, mouseY, partialTicks);
   }

   protected void keyTyped(char typedChar, int keyCode) throws IOException {
      Iterator var4 = this.drawCategories.iterator();

      while(var4.hasNext()) {
         DrawCategory drawCategory = (DrawCategory)var4.next();
         drawCategory.keyTyped(typedChar, keyCode);
      }

      super.keyTyped(typedChar, keyCode);
   }

   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
      Iterator var5 = this.drawCategories.iterator();

      while(var5.hasNext()) {
         DrawCategory drawCategory = (DrawCategory)var5.next();
         drawCategory.mouseClicked(mouseX, mouseY, mouseButton);
      }

      super.mouseClicked(mouseX, mouseY, mouseButton);
   }

   protected void mouseReleased(int mouseX, int mouseY, int state) {
      Iterator var5 = this.drawCategories.iterator();

      while(var5.hasNext()) {
         DrawCategory drawCategory = (DrawCategory)var5.next();
         drawCategory.mouseReleased(mouseX, mouseY, state);
      }

      super.mouseReleased(mouseX, mouseY, state);
   }

   public boolean doesGuiPauseGame() {
      return false;
   }

   public void onGuiClosed() {
      if (this.mc.entityRenderer.theShaderGroup != null) {
         this.mc.entityRenderer.theShaderGroup.deleteShaderGroup();
         this.mc.entityRenderer.theShaderGroup = null;
      }

      Fiveware.moduleManager.getModuleByName("ClickGUI").setToggled(false);
   }
}
