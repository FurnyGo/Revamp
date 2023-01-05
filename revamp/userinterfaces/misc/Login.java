// 
// Decompiled by Procyon v0.5.36
// 

package revamp.userinterfaces.misc;

import java.security.MessageDigest;
import java.net.URLConnection;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.io.IOException;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import revamp.userinterfaces.mainmenu.CustomMainMenu;
import revamp.updater.Checks;
import revamp.Revamp;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.input.Keyboard;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.GuiScreen;

public class Login extends GuiScreen
{
    private GuiTextField username;
    
    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.username = new GuiTextField(1, this.mc.fontRendererObj, this.width / 2 - 100, this.height / 2, 200, 20);
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 3 * 2, "Continue"));
    }
    
    public void actionPerformed(final GuiButton button) {
        switch (button.id) {
            case 0: {
                try {
                    final String[] profile = callURL("https://pastebin.com/raw/0Kaii4WJ").split("==");
                    String[] array;
                    for (int length = (array = profile).length, i = 0; i < length; ++i) {
                        final String id = array[i];
                        if (id.equalsIgnoreCase(String.valueOf(this.username.getText()) + "+|+" + getHWID())) {
                            Revamp.sendMessage(String.valueOf(this.username.getText()) + " has logged into Revamp " + Revamp.getVERSION() + ".");
                            Revamp.setUSERNAME(this.username.getText());
                            if (!Checks.versionCheck()) {
                                this.mc.displayGuiScreen(new CustomMainMenu());
                            }
                            else {
                                this.mc.displayGuiScreen(new GuiUpdater());
                            }
                        }
                    }
                }
                catch (Exception e) {
                    Revamp.sendMessage(String.valueOf(this.username.getText()) + " has failed to login.");
                }
                break;
            }
        }
    }
    
    @Override
    public void drawScreen(final int x2, final int y2, final float z2) {
        this.mc.getTextureManager().bindTexture(new ResourceLocation("revamp/background.jpg"));
        Gui.drawModalRectWithCustomSizedTexture(0, 0, 0.0f, 0.0f, this.width, this.height, (float)this.width, (float)this.height);
        this.username.drawTextBox();
        this.drawCenteredString(this.fontRendererObj, "Please enter your username to get started:", (float)(this.width / 2), (float)(this.height / 2 - 15), -1);
        super.drawScreen(x2, y2, z2);
    }
    
    public void mouseClicked(final int x2, final int y2, final int z2) {
        try {
            super.mouseClicked(x2, y2, z2);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        this.username.mouseClicked(x2, y2, z2);
    }
    
    @Override
    protected void keyTyped(final char character, final int key) {
        this.username.textboxKeyTyped(character, key);
        if (character == '\r') {
            this.actionPerformed(this.buttonList.get(0));
        }
    }
    
    public static String callURL(final String url) throws IOException {
        final URLConnection urlConnection = new URL(url).openConnection();
        urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:102.0) Blink/107 Chrome/107.0");
        Throwable t = null;
        try {
            final BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
            try {
                final StringBuilder builder = new StringBuilder();
                String input;
                while ((input = reader.readLine()) != null) {
                    builder.append(input);
                }
                return builder.toString();
            }
            finally {
                if (reader != null) {
                    reader.close();
                }
            }
        }
        finally {
            if (t == null) {
                final Throwable exception;
                t = exception;
            }
            else {
                final Throwable exception;
                if (t != exception) {
                    t.addSuppressed(exception);
                }
            }
        }
    }
    
    public static String getHWID() {
        final StringBuffer string = new StringBuffer();
        try {
            final String plainHWID = String.valueOf(System.getenv("COMPUTERNAME")) + System.getProperty("user.name") + System.getenv("PROCESSOR_IDENTIFIER") + System.getenv("PROCESSOR_LEVEL");
            final MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(plainHWID.getBytes());
            final byte[] data = md5.digest();
            byte[] array;
            for (int length = (array = data).length, i = 0; i < length; ++i) {
                final byte byteData = array[i];
                final String hex = Integer.toHexString(0xFF & byteData);
                if (hex.length() == 1) {
                    string.append('0');
                }
                string.append(hex);
            }
            return string.toString();
        }
        catch (Exception e) {
            System.out.println("This user has an invalid HWID! Printing stack trace.");
            e.printStackTrace();
            return null;
        }
    }
}
