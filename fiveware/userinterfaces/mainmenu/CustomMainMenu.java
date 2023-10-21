package fiveware.userinterfaces.mainmenu;

import fiveware.Fiveware;
import java.io.IOException;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import viamcp.gui.GuiProtocolSelector;

public class CustomMainMenu extends GuiScreen implements GuiYesNoCallback {
   public void initGui() {
      int j = (int)((double)this.height / 2.6D);
      this.buttonList.add(new GuiButton(1, this.width / 2 - 100, j, I18n.format("menu.singleplayer").toLowerCase()));
      this.buttonList.add(new GuiButton(2, this.width / 2 - 100, j + 24, I18n.format("menu.multiplayer").toLowerCase()));
      this.buttonList.add(new GuiButton(4, this.width / 2 - 100, j + 48, "alt login"));
      this.buttonList.add(new GuiButton(0, this.width / 2 - 100, j + 84, 98, 20, I18n.format("menu.options").toLowerCase().replace("...", "")));
      this.buttonList.add(new GuiButton(3, this.width / 2 + 2, j + 84, 98, 20, I18n.format("menu.quit").toLowerCase()));
      this.buttonList.add(new GuiButton(5, this.width / 2 - 47, j + 107, 98, 20, "version"));
      this.mc.func_181537_a(false);
   }

   protected void actionPerformed(GuiButton button) throws IOException {
      switch(button.id) {
      case 0:
         this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
         break;
      case 1:
         this.mc.displayGuiScreen(new GuiSelectWorld(this));
         break;
      case 2:
         this.mc.displayGuiScreen(new GuiMultiplayer(this));
         break;
      case 3:
         this.mc.shutdown();
         break;
      case 4:
         this.mc.displayGuiScreen(new GuiAltLogin());
         break;
      case 5:
         this.mc.displayGuiScreen(new GuiProtocolSelector(this));
      }

   }

   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
      this.mc.getTextureManager().bindTexture(new ResourceLocation("fiveware/background.png"));
      drawModalRectWithCustomSizedTexture(0, 0, (float)mouseX, (float)mouseY, 2304, 1296, (float)(this.width * 2), (float)(this.height * 2));
      this.drawString(this.fontRendererObj, Fiveware.getNAME() + " " + Fiveware.getVERSION(), 2, this.height - 10, -1);
      this.drawString(this.fontRendererObj, "created by " + Fiveware.getAUTHOR(), this.width - this.fontRendererObj.getStringWidth("created by " + Fiveware.getAUTHOR()) - 2, this.height - 10, -1);
      super.drawScreen(mouseX, mouseY, partialTicks);
   }
}
