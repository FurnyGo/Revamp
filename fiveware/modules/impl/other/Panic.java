package fiveware.modules.impl.other;

import fiveware.Fiveware;
import fiveware.modules.Category;
import fiveware.modules.Module;
import java.util.Iterator;

public class Panic extends Module {
   public Panic() {
      super("Panic", "Panic", "Turns off all modules.", -1, 0, Category.OTHER);
   }

   public void onEnable() {
      Iterator var2 = Fiveware.getModules().iterator();

      while(var2.hasNext()) {
         Module module = (Module)var2.next();
         module.onDisable();
         module.setToggled(false);
      }

   }
}
