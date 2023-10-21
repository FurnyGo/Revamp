package fiveware.modules.impl.other;

import fiveware.modules.Category;
import fiveware.modules.Module;

public class MemoryFix extends Module {
   public MemoryFix() {
      super("Memory Fix", "MemoryFix", "Prevents memory leaking.", -1, 0, Category.OTHER);
   }
}
