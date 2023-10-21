package fiveware.userinterfaces.mainmenu;

import com.mojang.authlib.Agent;
import com.mojang.authlib.AuthenticationService;
import com.mojang.authlib.UserAuthentication;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import fiveware.Fiveware;
import fiveware.utilities.misc.Randomizer;
import fr.litarvan.openauth.microsoft.MicrosoftAuthResult;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticationException;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticator;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Session;
import org.lwjgl.input.Keyboard;

public class GuiAltLogin extends GuiScreen {
   private GuiTextField username;
   private GuiTextField password;
   private UserAuthentication auth;
   private Minecraft mc = Minecraft.getMinecraft();
   private GuiAltLogin.LoginMethod mode;
   // $FF: synthetic field
   private static volatile int[] $SWITCH_TABLE$fiveware$userinterfaces$mainmenu$GuiAltLogin$LoginMethod;

   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
      ScaledResolution sr = new ScaledResolution(this.mc);
      this.mc.getTextureManager().bindTexture(new ResourceLocation("fiveware/background.png"));
      drawModalRectWithCustomSizedTexture(0, 0, (float)mouseX, (float)mouseY, 2304, 1296, (float)(this.width * 2), (float)(this.height * 2));
      if (!this.username.getText().isEmpty()) {
         if (this.username.getText().contains(":") && this.username.getText().contains("@")) {
            this.mode = GuiAltLogin.LoginMethod.COMBO;
         } else if (!this.username.getText().contains(":") && this.username.getText().contains("@")) {
            this.mode = GuiAltLogin.LoginMethod.MICROSOFT;
         } else if (!this.username.getText().contains(":") && !this.username.getText().contains("@")) {
            this.mode = GuiAltLogin.LoginMethod.CRACKED;
         } else {
            this.mode = GuiAltLogin.LoginMethod.UNKNOWN;
         }
      } else {
         this.mode = GuiAltLogin.LoginMethod.UNKNOWN;
      }

      String info = "§c" + this.mc.getSession().getUsername() + " §4| §c" + this.mode.toString().toLowerCase();
      this.drawCenteredString(this.mc.fontRendererObj, info, (float)(this.width / 2), (float)(this.height / 2 - 35), -1);
      this.username.drawTextBox();
      this.password.drawTextBox();
      if (!this.username.isFocused() && this.username.getText().isEmpty()) {
         this.drawCenteredString(this.mc.fontRendererObj, "email, combo, username", (float)(this.width / 2 - 65), (float)(sr.getScaledHeight() / 2 + 6), -2139062144);
      }

      if (!this.password.isFocused() && this.password.getText().isEmpty()) {
         this.drawCenteredString(this.mc.fontRendererObj, "password", (float)(this.width / 2 + 65), (float)(sr.getScaledHeight() / 2 + 6), -2139062144);
      }

      this.drawString(this.fontRendererObj, Fiveware.getNAME() + " " + Fiveware.getVERSION(), 2, this.height - 10, -1);
      this.drawString(this.fontRendererObj, "created by " + Fiveware.getAUTHOR(), this.width - this.fontRendererObj.getStringWidth("created by " + Fiveware.getAUTHOR()) - 2, this.height - 10, -1);
      super.drawScreen(mouseX, mouseY, partialTicks);
   }

   public void initGui() {
      ScaledResolution sr = new ScaledResolution(this.mc);
      this.buttonList.clear();
      this.buttonList.add(new GuiButton(0, this.width / 2 - 50 - 10, this.height / 2 - 25, 120, 20, "login"));
      this.buttonList.add(new GuiButton(1, this.width / 2 - 50 - 10, this.height / 2 + 25, 120, 20, "generate cracked"));
      this.buttonList.add(new GuiButton(2, this.width / 2 - 50 - 10, this.height / 2 + 48, 120, 20, "exit"));
      (this.username = new GuiTextField(100, this.fontRendererObj, this.width / 2 - 127, sr.getScaledHeight() / 2, 125, 20)).setFocused(true);
      (this.password = new GuiTextField(100, this.fontRendererObj, this.width / 2 + 3, sr.getScaledHeight() / 2, 125, 20)).setFocused(false);
      this.username.setMaxStringLength(256);
      this.password.setMaxStringLength(256);
      Keyboard.enableRepeatEvents(true);
      AuthenticationService authService = new YggdrasilAuthenticationService(this.mc.getProxy(), UUID.randomUUID().toString());
      this.auth = authService.createUserAuthentication(Agent.MINECRAFT);
      authService.createMinecraftSessionService();
   }

   protected void actionPerformed(GuiButton button) {
      switch(button.id) {
      case 0:
         MicrosoftAuthenticator authenticator;
         MicrosoftAuthResult result;
         switch($SWITCH_TABLE$fiveware$userinterfaces$mainmenu$GuiAltLogin$LoginMethod()[this.mode.ordinal()]) {
         case 1:
            String[] combo = this.username.getText().split(":");

            try {
               authenticator = new MicrosoftAuthenticator();
               result = authenticator.loginWithCredentials(combo[0], combo[1]);
               this.mc.session = new Session(result.getProfile().getName(), result.getProfile().getId(), result.getAccessToken(), "legacy");
            } catch (MicrosoftAuthenticationException var8) {
               var8.printStackTrace();
            }

            return;
         case 2:
            try {
               authenticator = new MicrosoftAuthenticator();
               result = authenticator.loginWithCredentials(this.username.getText(), this.password.getText());
               this.mc.session = new Session(result.getProfile().getName(), result.getProfile().getId(), result.getAccessToken(), "legacy");
            } catch (MicrosoftAuthenticationException var7) {
               var7.printStackTrace();
            }

            return;
         case 3:
            try {
               this.auth.logOut();
               this.mc.session = new Session(this.username.getText(), this.username.getText(), "0", "mojang");
            } catch (Exception var6) {
               var6.printStackTrace();
            }

            return;
         case 4:
         default:
            return;
         }
      case 1:
         String username = Randomizer.randomWord() + Randomizer.randomWord();

         try {
            this.auth.logOut();
            this.mc.session = new Session(username, username, "0", "mojang");
         } catch (Exception var5) {
            var5.printStackTrace();
         }
         break;
      case 2:
         this.mc.displayGuiScreen(new CustomMainMenu());
      }

   }

   protected void keyTyped(char character, int key) {
      try {
         super.keyTyped(character, key);
      } catch (Exception var4) {
         var4.printStackTrace();
      }

      if (character == '\t' && !this.username.isFocused()) {
         this.username.setFocused(true);
         this.password.setFocused(false);
      }

      if (character == '\t' && !this.password.isFocused()) {
         this.password.setFocused(true);
         this.username.setFocused(false);
      }

      if (character == '\r') {
         this.actionPerformed((GuiButton)this.buttonList.get(0));
      }

      this.username.textboxKeyTyped(character, key);
      this.password.textboxKeyTyped(character, key);
   }

   protected void mouseClicked(int x2, int y2, int button) {
      try {
         super.mouseClicked(x2, y2, button);
      } catch (Exception var5) {
         var5.printStackTrace();
      }

      this.username.mouseClicked(x2, y2, button);
      this.password.mouseClicked(x2, y2, button);
   }

   public void onGuiClosed() {
      this.mc.entityRenderer.loadEntityShader((Entity)null);
      Keyboard.enableRepeatEvents(false);
   }

   // $FF: synthetic method
   static int[] $SWITCH_TABLE$fiveware$userinterfaces$mainmenu$GuiAltLogin$LoginMethod() {
      int[] var10000 = $SWITCH_TABLE$fiveware$userinterfaces$mainmenu$GuiAltLogin$LoginMethod;
      if (var10000 != null) {
         return var10000;
      } else {
         int[] var0 = new int[GuiAltLogin.LoginMethod.values().length];

         try {
            var0[GuiAltLogin.LoginMethod.COMBO.ordinal()] = 1;
         } catch (NoSuchFieldError var4) {
         }

         try {
            var0[GuiAltLogin.LoginMethod.CRACKED.ordinal()] = 3;
         } catch (NoSuchFieldError var3) {
         }

         try {
            var0[GuiAltLogin.LoginMethod.MICROSOFT.ordinal()] = 2;
         } catch (NoSuchFieldError var2) {
         }

         try {
            var0[GuiAltLogin.LoginMethod.UNKNOWN.ordinal()] = 4;
         } catch (NoSuchFieldError var1) {
         }

         $SWITCH_TABLE$fiveware$userinterfaces$mainmenu$GuiAltLogin$LoginMethod = var0;
         return var0;
      }
   }

   public static enum LoginMethod {
      COMBO,
      MICROSOFT,
      CRACKED,
      UNKNOWN;
   }
}
