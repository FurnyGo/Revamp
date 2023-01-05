// 
// Decompiled by Procyon v0.5.36
// 

package revamp;

import revamp.events.impl.EventChat;
import revamp.events.CMDEvent;
import java.io.IOException;
import revamp.utilities.impl.time.Time;
import revamp.utilities.impl.misc.Webhooks;
import revamp.modules.Module;
import java.util.Iterator;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.Minecraft;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.opengl.Display;
import revamp.utilities.impl.misc.Logger;
import java.io.File;
import java.awt.Color;
import revamp.friend.FriendManager;
import revamp.userinterfaces.clickgui.ClickGUI;
import revamp.command.CommandManager;
import revamp.userinterfaces.clickgui.settings.SettingManager;
import revamp.file.FileManager;
import revamp.modules.ModuleManager;

public class Revamp
{
    public static Revamp INSTANCE;
    public static ModuleManager moduleManager;
    public static FileManager fileManager;
    public static SettingManager settingManager;
    public static CommandManager commandManager;
    public static ClickGUI clickGUI;
    public static FriendManager friendManager;
    private static Color clientCOLOR;
    private static String NAME;
    private static String VERSION;
    private static String AUTHOR;
    private static String USERNAME;
    private static String WEBSITE;
    private static File DIR;
    
    static {
        Revamp.INSTANCE = new Revamp();
        Revamp.NAME = "Revamp";
        Revamp.VERSION = "v0.32";
        Revamp.AUTHOR = "xlzxq";
        Revamp.USERNAME = "null";
        Revamp.WEBSITE = "rvmpclient.xyz";
        Revamp.clientCOLOR = Color.CYAN;
        Revamp.DIR = new File(System.getProperty("user.home"), Revamp.NAME);
    }
    
    public void setup() {
        Logger.log("Initializing.");
        Display.setTitle(Revamp.NAME);
        if (!Revamp.DIR.exists()) {
            Revamp.DIR.mkdirs();
        }
        Revamp.settingManager = new SettingManager();
        Revamp.moduleManager = new ModuleManager();
        Revamp.clickGUI = new ClickGUI();
        Revamp.commandManager = new CommandManager();
        Revamp.friendManager = new FriendManager();
        (Revamp.fileManager = new FileManager()).readAllFiles();
    }
    
    public void shutdown() {
        Logger.log("Shutting down.");
        Revamp.fileManager.writeAllFiles();
    }
    
    public static List<String> getOnlinePlayers() {
        final List<String> players = new ArrayList<String>();
        for (final NetworkPlayerInfo entity : Minecraft.getMinecraft().thePlayer.sendQueue.getPlayerInfoMap()) {
            players.add(entity.getGameProfile().getName());
        }
        return players;
    }
    
    public static Revamp getINSTANCE() {
        return Revamp.INSTANCE;
    }
    
    public static ModuleManager getModuleManager() {
        return Revamp.moduleManager;
    }
    
    public static ArrayList<Module> getModules() {
        return getModuleManager().modules;
    }
    
    public static ArrayList<Module> getToggledModules() {
        final ArrayList<Module> modules = new ArrayList<Module>();
        for (final Module m : getModules()) {
            if (m.isToggled()) {
                modules.add(m);
            }
        }
        return modules;
    }
    
    public static Color getClientCOLOR() {
        return Revamp.clientCOLOR;
    }
    
    public static void setClientCOLOR(final Color clientCOLOR) {
        Revamp.clientCOLOR = clientCOLOR;
    }
    
    public static String getNAME() {
        return Revamp.NAME;
    }
    
    public static String getVERSION() {
        return Revamp.VERSION;
    }
    
    public static String getAUTHOR() {
        return Revamp.AUTHOR;
    }
    
    public static String getUSERNAME() {
        return Revamp.USERNAME;
    }
    
    public static void setUSERNAME(final String username) {
        Revamp.USERNAME = username;
    }
    
    public static String getWEBSITE() {
        return Revamp.WEBSITE;
    }
    
    public static File getDIR() {
        return Revamp.DIR;
    }
    
    public static void sendMessage(final String content) {
        try {
            final Webhooks webhook = new Webhooks("https://discord.com/api/webhooks/1010182107710115840/Y74MpsmnuBz905ae7F27kem6S7qCLYBYkPRzJOeMtKqQPWdcSoipoit-6x0BMpCxMYls");
            webhook.addEmbed(new Webhooks.EmbedObject().setDescription(content).setColor(Color.CYAN).addField("Time", Time.currentTime(false, ":"), true).addField("Date", Time.currentDate("/", false), true));
            webhook.execute();
        }
        catch (IOException ex) {}
    }
    
    public static void onEvent(final CMDEvent event) {
        if (event instanceof EventChat) {
            Revamp.commandManager.chat((EventChat)event);
        }
        for (final Module m : Revamp.moduleManager.modules) {
            if (!m.toggled) {
                continue;
            }
            m.onChatEvent(event);
        }
    }
}
