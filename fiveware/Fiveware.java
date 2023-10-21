package fiveware;

import fiveware.command.CommandManager;
import fiveware.events.CMDEvent;
import fiveware.events.impl.EventChat;
import fiveware.file.FileManager;
import fiveware.friend.FriendManager;
import fiveware.interfaces.Methods;
import fiveware.modules.Module;
import fiveware.modules.ModuleManager;
import fiveware.userinterfaces.clickgui.ClickGUI;
import fiveware.userinterfaces.clickgui.settings.SettingManager;
import fiveware.utilities.misc.Randomizer;
import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.util.ChatComponentText;
import org.lwjgl.opengl.Display;
import viamcp.ViaMCP;

public class Fiveware {
   public static Fiveware INSTANCE = new Fiveware();
   public static ModuleManager moduleManager;
   public static FileManager fileManager;
   public static SettingManager settingManager;
   public static CommandManager commandManager;
   public static ClickGUI clickGUI;
   public static FriendManager friendManager;
   private static Color clientCOLOR;
   private static String NAME = "fiveware";
   private static String VERSION = "1.0";
   private static String AUTHOR = "xlzxq";
   private static String USERNAME = "unknown";
   private static String WEBSITE = "fiveware.lol";
   private static String LUNAR_ID;
   private static File DIR;

   static {
      clientCOLOR = Color.RED;
      DIR = new File(System.getProperty("user.home"), NAME);
      LUNAR_ID = Randomizer.randomLength(7, true);
   }

   public void setup(boolean reload) {
      Display.setTitle(NAME);
      if (!DIR.exists()) {
         DIR.mkdirs();
      }

      if (!reload) {
         ViaMCP.getInstance().start();
         ViaMCP.getInstance().initAsyncSlider();
      }

      settingManager = new SettingManager();
      moduleManager = new ModuleManager();
      clickGUI = new ClickGUI();
      if (!reload) {
         commandManager = new CommandManager();
         friendManager = new FriendManager();
      }

      fileManager = new FileManager();
      fileManager.readAllFiles();
   }

   public void shutdown() {
      fileManager.writeAllFiles();
   }

   public static List<String> getOnlinePlayers() {
      List<String> players = new ArrayList();
      Iterator var2 = Minecraft.getMinecraft().thePlayer.sendQueue.getPlayerInfoMap().iterator();

      while(var2.hasNext()) {
         NetworkPlayerInfo entity = (NetworkPlayerInfo)var2.next();
         players.add(entity.getGameProfile().getName());
      }

      players.remove(Methods.mc.getSession().getUsername());
      return players;
   }

   public static Fiveware getINSTANCE() {
      return INSTANCE;
   }

   public static ModuleManager getModuleManager() {
      return moduleManager;
   }

   public static ArrayList<Module> getModules() {
      return getModuleManager().modules;
   }

   public static String getLUNARID() {
      return LUNAR_ID;
   }

   public static ArrayList<Module> getToggledModules() {
      ArrayList<Module> modules = new ArrayList();
      Iterator var2 = getModules().iterator();

      while(var2.hasNext()) {
         Module m = (Module)var2.next();
         if (m.isToggled()) {
            modules.add(m);
         }
      }

      return modules;
   }

   public static Color getClientCOLOR() {
      return clientCOLOR;
   }

   public static void setClientCOLOR(Color clientCOLOR) {
      Fiveware.clientCOLOR = clientCOLOR;
   }

   public static String getNAME() {
      return NAME;
   }

   public static String getVERSION() {
      return VERSION;
   }

   public static String getAUTHOR() {
      return AUTHOR;
   }

   public static String getUSERNAME() {
      return USERNAME;
   }

   public static void setUSERNAME(String username) {
      USERNAME = username;
   }

   public static String getWEBSITE() {
      return WEBSITE;
   }

   public static File getDIR() {
      return DIR;
   }

   public static void onEvent(CMDEvent event) {
      if (event instanceof EventChat) {
         commandManager.chat((EventChat)event);
      }

      Iterator var2 = moduleManager.modules.iterator();

      while(var2.hasNext()) {
         Module m = (Module)var2.next();
         if (m.toggled) {
            m.onChatEvent(event);
         }
      }

   }

   public static void sendChatMessage(boolean error, String message) {
      Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("§c§l" + getNAME() + " §7[" + (error ? "§4!" : "§8?") + "§7] §f» §r" + message));
   }
}
