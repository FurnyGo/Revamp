package fiveware.userinterfaces.clickgui.settings;

import fiveware.modules.Module;
import java.util.ArrayList;
import java.util.Iterator;

public class SettingManager {
   public ArrayList<Setting> settings = new ArrayList();

   public void registerSetting(Setting setting) {
      this.settings.add(setting);
   }

   public ArrayList<Setting> getSetting() {
      return this.settings;
   }

   public Setting getSetting(Module module, String name) {
      Iterator var4 = this.getSetting().iterator();

      Setting setting;
      do {
         if (!var4.hasNext()) {
            return null;
         }

         setting = (Setting)var4.next();
      } while(!setting.getModule().equals(module) || !setting.name.equalsIgnoreCase(name));

      return setting;
   }
}
