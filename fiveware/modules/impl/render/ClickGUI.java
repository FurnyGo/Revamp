package fiveware.modules.impl.render;

import fiveware.Fiveware;
import fiveware.events.Event;
import fiveware.interfaces.Methods;
import fiveware.modules.Category;
import fiveware.modules.Module;
import fiveware.userinterfaces.clickgui.settings.Setting;
import net.minecraft.client.gui.GuiScreen;

public class ClickGUI extends Module implements Methods {
   public Setting rainbow = new Setting("Rainbow", true, this);
   public Setting rainbowmode = new Setting("Rainbow Mode", new String[]{"Regular", "Astolfo", "Mixed"}, "Regular", this);
   public Setting blur = new Setting("Blur", true, this);
   public Setting anime = new Setting("Anime", false, this);
   public Setting mode = new Setting("Character", new String[]{"Astolfo", "Killua", "Levi", "Eren", "Rias", "ZeroTwo", "Yor", "Esdeath"}, "Astolfo", this);

   public ClickGUI() {
      super("Click GUI", "ClickGUI", "Renders the ClickGUI.", -1, 54, Category.RENDER);
   }

   public void onEnable() {
      if (this.getCurrentScreen() == null) {
         Fiveware.getINSTANCE();
         this.displayScreen(Fiveware.clickGUI);
      }

   }

   public void onEvent(Event event) {
      if (this.anime.isToggled()) {
         this.mode.visible = true;
      } else {
         this.mode.visible = false;
      }

      if (this.rainbow.isToggled()) {
         this.rainbowmode.visible = true;
      } else {
         this.rainbowmode.visible = false;
      }

   }

   public void onDisable() {
      GuiScreen var10000 = this.getCurrentScreen();
      Fiveware.getINSTANCE();
      if (var10000 == Fiveware.clickGUI) {
         this.getPlayer().closeScreen();
      }

   }
}
