package fiveware.modules.impl.other;

import fiveware.Fiveware;
import fiveware.events.Event;
import fiveware.events.impl.EventUpdate;
import fiveware.interfaces.Methods;
import fiveware.modules.Category;
import fiveware.modules.Module;
import fiveware.userinterfaces.clickgui.settings.Setting;
import fiveware.utilities.misc.Randomizer;
import fiveware.utilities.time.Timer;
import java.util.Arrays;
import java.util.List;

public class Spammer extends Module implements Methods {
   private Timer timer = new Timer();
   public Setting delay = new Setting("Delay", 1.0F, 0.0F, 5.0F, false, this);
   public Setting nospam = new Setting("No Anti-Spam", true, this);
   public Setting length = new Setting("Length", 5.0F, 1.0F, 20.0F, true, this);
   public Setting message = new Setting("Message", "%everyone%, %advert%", this);

   public Spammer() {
      super("Spammer", "Spammer", "Spams at a delay with a chosen message.", -1, 0, Category.OTHER);
   }

   public void onEvent(Event event) {
      if (this.nospam.isToggled()) {
         this.length.visible = true;
      } else {
         this.length.visible = false;
      }

      if (this.isToggled()) {
         if (event instanceof EventUpdate) {
            if (!this.isIngame()) {
               return;
            }

            String players = (String)Fiveware.getOnlinePlayers().get(Randomizer.randomInt(0, Fiveware.getOnlinePlayers().size()));
            List<String> advert = Arrays.asList(Fiveware.getNAME() + " goes insane", "Me and the boys moving to OP client aka " + Fiveware.getNAME(), "Tired of losing? Get " + Fiveware.getNAME() + " today!", "FDP? Rise? Vape? Nah i'm just using " + Fiveware.getNAME(), "Download " + Fiveware.getNAME() + " to kick more ass then ever!", "Why " + Fiveware.getNAME() + "? Cause it is the addition of pure skill and incredible abilities", "What client should I choose? " + Fiveware.getNAME() + " or " + Fiveware.getNAME() + "?", "In need of a cute present for Christmas? " + Fiveware.getNAME() + " is all you need!", "I have a good " + Fiveware.getNAME() + " spam config, don't blame me", Fiveware.getNAME() + " never dies, unlike Technoblade", "Imagine using anything but " + Fiveware.getNAME(), "Just beta testing the anti-spam with " + Fiveware.getNAME(), "Don't forget to report me for " + Fiveware.getNAME() + " on the forums!", "don't use " + Fiveware.getNAME() + "? ok boomer", "How come a noob like you not use " + Fiveware.getNAME() + "?", "Behind every " + Fiveware.getNAME() + " user is cooler than you.", "Your client sucks, just get " + Fiveware.getNAME());
            if (this.timer.hasReached((long)(this.delay.getCurrentValue() * 1000.0F))) {
               if (players.equalsIgnoreCase(this.getUsername())) {
                  players = (String)Fiveware.getOnlinePlayers().get(Randomizer.randomInt(0, Fiveware.getOnlinePlayers().size()));
               }

               this.getPlayer().sendChatMessage(this.message.getTyped().replace("%everyone%", players).replace("%advert%", (CharSequence)advert.get(Randomizer.randomInt(0, advert.size()))) + (this.nospam.isToggled() ? " " + Randomizer.randomLength((int)this.length.getCurrentValue(), false) : ""));
               this.timer.reset();
            }
         }

      }
   }
}
