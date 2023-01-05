// 
// Decompiled by Procyon v0.5.36
// 

package revamp.userinterfaces.mainmenu;

import net.minecraft.entity.Entity;
import fr.litarvan.openauth.microsoft.MicrosoftAuthResult;
import revamp.utilities.impl.misc.Randomizer;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticationException;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticator;
import net.minecraft.util.Session;
import com.mojang.authlib.AuthenticationService;
import com.mojang.authlib.Agent;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import java.util.UUID;
import org.lwjgl.input.Keyboard;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.Minecraft;
import com.mojang.authlib.UserAuthentication;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.GuiScreen;

public class GuiAltLogin extends GuiScreen
{
    private GuiTextField username;
    private GuiTextField password;
    private UserAuthentication auth;
    private Minecraft mc;
    
    public GuiAltLogin() {
        this.mc = Minecraft.getMinecraft();
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        final ScaledResolution sr = new ScaledResolution(this.mc);
        this.mc.getTextureManager().bindTexture(new ResourceLocation("revamp/background.jpg"));
        Gui.drawModalRectWithCustomSizedTexture(0, 0, 0.0f, 0.0f, this.width, this.height, (float)this.width, (float)this.height);
        final String info = "§b" + this.mc.getSession().getUsername() + " §3| §b" + (this.password.getText().isEmpty() ? "Cracked" : "Microsoft");
        this.drawString(this.mc.fontRendererObj, info, this.width / 2 - this.fontRendererObj.getStringWidth(info) + 55, sr.getScaledHeight() / 2 - 70, -1);
        this.drawString(this.mc.fontRendererObj, "Username", this.width / 2 - 60, sr.getScaledHeight() / 2 - 60, -1);
        this.drawString(this.mc.fontRendererObj, "Password", this.width / 2 - 60, sr.getScaledHeight() / 2 - 26, -1);
        this.username.drawTextBox();
        this.password.drawTextBox();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    @Override
    public void initGui() {
        final ScaledResolution sr = new ScaledResolution(this.mc);
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, this.width / 2 - 50 - 10, this.height / 2 + 9, 120, 20, "Login"));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 50 - 10, this.height / 2 + 32, 120, 20, "Generate Cracked"));
        this.buttonList.add(new GuiButton(2, this.width / 2 - 50 - 10, this.height / 2 + 55, 120, 20, "Exit"));
        (this.username = new GuiTextField(100, this.fontRendererObj, this.width / 2 - 50 - 10, sr.getScaledHeight() / 2 - 50, 120, 20)).setFocused(true);
        (this.password = new GuiTextField(100, this.fontRendererObj, this.width / 2 - 50 - 10, sr.getScaledHeight() / 2 - 16, 120, 20)).setFocused(false);
        Keyboard.enableRepeatEvents(true);
        final AuthenticationService authService = (AuthenticationService)new YggdrasilAuthenticationService(this.mc.getProxy(), UUID.randomUUID().toString());
        this.auth = authService.createUserAuthentication(Agent.MINECRAFT);
        authService.createMinecraftSessionService();
    }
    
    @Override
    protected void actionPerformed(final GuiButton button) {
        switch (button.id) {
            case 0: {
                if (this.username.getText() == " ") {
                    break;
                }
                if (this.password.getText().isEmpty()) {
                    try {
                        this.auth.logOut();
                        this.mc.session = new Session(this.username.getText(), this.username.getText(), "0", "mojang");
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
                try {
                    final MicrosoftAuthenticator authenticator = new MicrosoftAuthenticator();
                    final MicrosoftAuthResult result = authenticator.loginWithCredentials(this.username.getText(), this.password.getText());
                    this.mc.session = new Session(result.getProfile().getName(), result.getProfile().getId(), result.getAccessToken(), "legacy");
                    System.out.printf("Logged in with '%s'%n", result.getProfile().getName());
                }
                catch (MicrosoftAuthenticationException e2) {
                    e2.printStackTrace();
                }
                break;
            }
            case 1: {
                final String username = String.valueOf(Randomizer.randomWord()) + Randomizer.randomWord();
                try {
                    this.auth.logOut();
                    this.mc.session = new Session(username, username, "0", "mojang");
                }
                catch (Exception e3) {
                    e3.printStackTrace();
                }
                break;
            }
            case 2: {
                this.mc.displayGuiScreen(new CustomMainMenu());
                break;
            }
        }
    }
    
    @Override
    protected void keyTyped(final char character, final int key) {
        try {
            super.keyTyped(character, key);
        }
        catch (Exception e) {
            e.printStackTrace();
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
            this.actionPerformed(this.buttonList.get(0));
        }
        this.username.textboxKeyTyped(character, key);
        this.password.textboxKeyTyped(character, key);
    }
    
    @Override
    protected void mouseClicked(final int x2, final int y2, final int button) {
        try {
            super.mouseClicked(x2, y2, button);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        this.username.mouseClicked(x2, y2, button);
        this.password.mouseClicked(x2, y2, button);
    }
    
    @Override
    public void onGuiClosed() {
        this.mc.entityRenderer.loadEntityShader(null);
        Keyboard.enableRepeatEvents(false);
    }
}
