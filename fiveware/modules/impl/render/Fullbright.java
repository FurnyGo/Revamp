package fiveware.modules.impl.render;

import fiveware.events.Event;
import fiveware.events.impl.EventUpdate;
import fiveware.interfaces.Methods;
import fiveware.modules.Category;
import fiveware.modules.Module;
import fiveware.userinterfaces.clickgui.settings.Setting;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class Fullbright extends Module implements Methods {
   public Setting mode = new Setting("Mode", new String[]{"Potion", "Gamma"}, "Potion", this);

   public Fullbright() {
      super("Fullbright", "Fullbright", "Brightens the world.", -1, 0, Category.RENDER);
   }

   public void onEvent(Event event) {
      if (this.isToggled()) {
         if (event instanceof EventUpdate) {
            this.setInfo(this.information() ? "§7" + this.mode.getCurrentMode() + (this.leftSide() ? " §r" : "") : "");
            if (this.mode.getCurrentMode().equalsIgnoreCase("Potion")) {
               this.getPlayer().addPotionEffect(new PotionEffect(Potion.nightVision.getId(), 5210, 3));
               this.getGameSettings().gammaSetting = 1.0F;
            } else if (this.mode.getCurrentMode().equalsIgnoreCase("Gamma")) {
               this.getGameSettings().gammaSetting = 10.0F;
               this.getPlayer().removePotionEffect(Potion.nightVision.getId());
            }
         }

      }
   }

   public void onDisable() {
      this.getPlayer().removePotionEffect(Potion.nightVision.getId());
      this.getGameSettings().gammaSetting = 1.0F;
   }
}
