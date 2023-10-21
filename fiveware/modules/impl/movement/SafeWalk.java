package fiveware.modules.impl.movement;

import fiveware.modules.Category;
import fiveware.modules.Module;

public class SafeWalk extends Module {
   public SafeWalk() {
      super("Safe Walk", "SafeWalk", "Prevents you from falling off blocks.", -1, 0, Category.MOVEMENT);
   }
}
