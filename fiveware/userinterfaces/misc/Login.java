package fiveware.userinterfaces.misc;

import fiveware.Fiveware;
import fiveware.userinterfaces.mainmenu.CustomMainMenu;
import java.io.IOException;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.codec.digest.DigestUtils;

public class Login extends GuiScreen {
   private GuiTextField id;
   private String status;
   private boolean goToMenu = false;

   public void initGui() {
      this.id = new GuiTextField(1, this.mc.fontRendererObj, this.width / 2 - 58, this.height / 2 + 6, 120, 15);
      this.buttonList.add(new GuiButton(0, this.width * 2, this.height / 3 * 2, "Continue"));
      ((GuiButton)this.buttonList.get(0)).visible = false;
      this.status = "Enter your Discord ID to get started:";
   }

   public void actionPerformed(GuiButton button) {
      switch(button.id) {
      case 0:
         Fiveware.setUSERNAME(this.id.getText());
         this.mc.displayGuiScreen(new CustomMainMenu());
      default:
      }
   }

   public void drawScreen(int x2, int y2, float z2) {
      this.mc.getTextureManager().bindTexture(new ResourceLocation("fiveware/background.png"));
      drawModalRectWithCustomSizedTexture(0, 0, (float)x2, (float)y2, 2304, 1296, (float)(this.width * 2), (float)(this.height * 2));
      this.id.drawTextBox();
      if (!this.id.isFocused() && this.id.getText().isEmpty()) {
         this.drawString(this.fontRendererObj, "ID", this.width / 2 - 4, this.height / 2 + 10, -8355712);
      }

      this.drawCenteredString(this.fontRendererObj, this.status, (float)(this.width / 2), (float)(this.height / 2 - 10), -1);
      super.drawScreen(x2, y2, z2);
   }

   public void mouseClicked(int x2, int y2, int z2) {
      try {
         super.mouseClicked(x2, y2, z2);
      } catch (IOException var5) {
         var5.printStackTrace();
      }

      this.id.mouseClicked(x2, y2, z2);
   }

   protected void keyTyped(char character, int key) {
      this.id.textboxKeyTyped(character, key);
      if (character == '\r') {
         this.actionPerformed((GuiButton)this.buttonList.get(0));
      }

   }

   public static String getHWID() {
      return DigestUtils.sha1Hex(System.getenv("COMPUTERNAME") + System.getProperty("user.name") + System.getenv("PROCESSOR_IDENTIFIER") + System.getenv("PROCESSOR_LEVEL"));
   }
}
