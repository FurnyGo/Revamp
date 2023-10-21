package fiveware.modules;

import fiveware.Fiveware;
import fiveware.events.CMDEvent;
import fiveware.events.Event;
import fiveware.userinterfaces.test.Notification;
import fiveware.userinterfaces.test.NotificationManager;
import fiveware.utilities.misc.Sound;
import net.minecraft.client.Minecraft;

public class Module {
   public Minecraft mc = Minecraft.getMinecraft();
   public String name;
   public String nameWithOutSpace;
   private String description;
   private String info = "";
   private int color;
   private int key;
   public int motionX;
   public int motionY;
   public boolean toggled;
   private Category category;

   public Module(String name, String nameWithOutSpace, String description, int color, int key, Category category) {
      this.name = name;
      this.nameWithOutSpace = nameWithOutSpace;
      this.description = description;
      this.color = color;
      this.key = key;
      this.category = category;
      this.toggled = false;
   }

   public void onEnable() {
   }

   public void onDisable() {
   }

   public void onEvent(Event event) {
   }

   public void onChatEvent(CMDEvent event) {
   }

   public void toggle() {
      this.toggled = !this.toggled;
      if (this.toggled) {
         if (Fiveware.settingManager.getSetting(Fiveware.moduleManager.getModuleByName("HUD"), "Sound").isToggled()) {
            Sound.playSound(Module.class.getClassLoader().getResource("assets/minecraft/fiveware/sounds/on.wav"));
         }

         NotificationManager.show(new Notification(Notification.NotificationType.INFO, "Enabled", this.name, 1));
         this.onEnable();
      } else {
         if (Fiveware.settingManager.getSetting(Fiveware.moduleManager.getModuleByName("HUD"), "Sound").isToggled()) {
            Sound.playSound(Module.class.getClassLoader().getResource("assets/minecraft/fiveware/sounds/off.wav"));
         }

         NotificationManager.show(new Notification(Notification.NotificationType.INFO, "Disabled", this.name, 1));
         this.onDisable();
      }

   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getDescription() {
      return this.description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public String getInfo() {
      return this.info;
   }

   public void setInfo(String info) {
      this.info = info;
   }

   public String getNameWithOutSpace() {
      return this.nameWithOutSpace;
   }

   public void setNameWithOutSpace(String nameWithOutSpace) {
      this.nameWithOutSpace = nameWithOutSpace;
   }

   public int getColor() {
      return this.color;
   }

   public void setColor(int color) {
      this.color = color;
   }

   public int getKey() {
      return this.key;
   }

   public void setKey(int key) {
      this.key = key;
   }

   public boolean isToggled() {
      return this.toggled;
   }

   public void setToggled(boolean toggled) {
      this.toggled = toggled;
   }

   public Category getCategory() {
      return this.category;
   }

   public void setCategory(Category category) {
      this.category = category;
   }
}
