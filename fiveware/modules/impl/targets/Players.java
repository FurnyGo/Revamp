package fiveware.modules.impl.targets;

import fiveware.modules.Category;
import fiveware.modules.Module;

public class Players extends Module {
   public Players() {
      super("Players", "Players", "Allows you to attack players.", -1, 0, Category.TARGETS);
   }
}
