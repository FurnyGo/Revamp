package fiveware.file.impl;

import fiveware.Fiveware;
import fiveware.file.Files;
import fiveware.interfaces.IFile;
import fiveware.modules.Module;
import fiveware.userinterfaces.clickgui.settings.Setting;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

@IFile(
   name = "settings"
)
public class Settings extends Files {
   // $FF: synthetic field
   private static volatile int[] $SWITCH_TABLE$fiveware$userinterfaces$clickgui$settings$Setting$Type;

   public void readFile(BufferedReader bufferedReader) throws IOException {
      String line;
      while((line = bufferedReader.readLine()) != null) {
         String[] args = line.split("=");
         Module module = Fiveware.moduleManager.getModuleByName(args[0]);
         Setting setting = Fiveware.settingManager.getSetting(module, args[1]);
         if (setting != null) {
            switch($SWITCH_TABLE$fiveware$userinterfaces$clickgui$settings$Setting$Type()[setting.getType().ordinal()]) {
            case 1:
               setting.setToggled(Boolean.parseBoolean(args[2]));
               break;
            case 2:
               setting.setCurrentMode(args[2]);
               break;
            case 3:
               setting.setCurrentValue(Float.parseFloat(args[2]));
               break;
            case 4:
               setting.setKey(Integer.parseInt(args[2]));
               break;
            case 5:
               setting.setTyped(args[2]);
            }
         }
      }

   }

   public void writeFile(FileWriter fileWriter) throws IOException {
      Iterator var3 = Fiveware.moduleManager.getModules().iterator();

      while(true) {
         Module module;
         do {
            if (!var3.hasNext()) {
               return;
            }

            module = (Module)var3.next();
         } while(module == null);

         Iterator var5 = Fiveware.settingManager.getSetting().iterator();

         while(var5.hasNext()) {
            Setting setting = (Setting)var5.next();
            if (setting.getModule().equals(module)) {
               Object arg = "";
               switch($SWITCH_TABLE$fiveware$userinterfaces$clickgui$settings$Setting$Type()[setting.getType().ordinal()]) {
               case 1:
                  arg = setting.isToggled();
                  break;
               case 2:
                  arg = setting.getCurrentMode();
                  break;
               case 3:
                  arg = setting.getCurrentValue();
                  break;
               case 4:
                  arg = setting.getKey();
                  break;
               case 5:
                  arg = setting.getTyped();
               }

               fileWriter.write(module.getNameWithOutSpace() + "=" + setting.getName() + "=" + arg + "\n");
            }
         }
      }
   }

   // $FF: synthetic method
   static int[] $SWITCH_TABLE$fiveware$userinterfaces$clickgui$settings$Setting$Type() {
      int[] var10000 = $SWITCH_TABLE$fiveware$userinterfaces$clickgui$settings$Setting$Type;
      if (var10000 != null) {
         return var10000;
      } else {
         int[] var0 = new int[Setting.Type.values().length];

         try {
            var0[Setting.Type.CHECKBOX.ordinal()] = 1;
         } catch (NoSuchFieldError var5) {
         }

         try {
            var0[Setting.Type.COMBOBOX.ordinal()] = 2;
         } catch (NoSuchFieldError var4) {
         }

         try {
            var0[Setting.Type.KEY.ordinal()] = 4;
         } catch (NoSuchFieldError var3) {
         }

         try {
            var0[Setting.Type.SLIDER.ordinal()] = 3;
         } catch (NoSuchFieldError var2) {
         }

         try {
            var0[Setting.Type.TYPE.ordinal()] = 5;
         } catch (NoSuchFieldError var1) {
         }

         $SWITCH_TABLE$fiveware$userinterfaces$clickgui$settings$Setting$Type = var0;
         return var0;
      }
   }
}
