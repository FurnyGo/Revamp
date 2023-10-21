package fiveware.modules.impl.render;

import fiveware.events.Event;
import fiveware.events.impl.EventText;
import fiveware.events.impl.EventUpdate;
import fiveware.interfaces.Methods;
import fiveware.modules.Category;
import fiveware.modules.Module;
import fiveware.userinterfaces.clickgui.settings.Setting;

public class NameProtect extends Module implements Methods {
   public static String formattedName = "";
   public Setting name = new Setting("Name", "&bfiveware", this);

   public NameProtect() {
      super("Name Protect", "NameProtect", "Protects your name.", -1, 0, Category.RENDER);
   }

   public void onEvent(Event event) {
      if (this.isToggled()) {
         if (event instanceof EventUpdate) {
            formattedName = this.name.getTyped();
            if (this.name.getTyped().contains("&")) {
               formattedName = formattedName.replaceAll("&", "§");
            }

            this.setInfo(this.information() ? "§7" + formattedName + (this.leftSide() ? " §r" : "") : "");
         }

         if (event instanceof EventText && ((EventText)event).text.contains(this.getUsername())) {
            ((EventText)event).text = ((EventText)event).text.replaceAll(this.getUsername(), formattedName);
         }

      }
   }
}
