package fiveware.modules.impl.other;

import fiveware.Fiveware;
import fiveware.events.Event;
import fiveware.events.impl.EventPacket;
import fiveware.events.impl.EventUpdate;
import fiveware.interfaces.Methods;
import fiveware.modules.Category;
import fiveware.modules.Module;
import fiveware.userinterfaces.clickgui.settings.Setting;
import fiveware.utilities.misc.Randomizer;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.S02PacketChat;

public class Killsults extends Module implements Methods {
   public Setting mode = new Setting("Insult Mode", new String[]{"Advertisement", "Insults", "Sus"}, "Advertisement", this);

   public Killsults() {
      super("Killsults", "Killsults", "Insults people apon killing them.", -1, 0, Category.OTHER);
   }

   public void onEvent(Event event) {
      if (this.isToggled()) {
         if (event instanceof EventUpdate) {
            this.setInfo(this.information() ? "§7" + this.mode.getCurrentMode() + (this.leftSide() ? " §r" : "") : "");
         }

         if (event instanceof EventPacket && ((EventPacket)event).chatPacket() instanceof S02PacketChat) {
            String text = ((S02PacketChat)((EventPacket)event).chatPacket()).getChatComponent().getUnformattedText();
            if (text.contains(this.getUsername()) && !this.getPlayer().isDead) {
               Iterator var4 = this.getWorld().loadedEntityList.iterator();

               while(var4.hasNext()) {
                  Entity e = (Entity)var4.next();
                  if (e instanceof EntityPlayer && e != this.getPlayer() && text.contains(e.getName())) {
                     this.getPlayer().sendChatMessage((String)this.insults(e.getName()).get(Randomizer.randomInt(0, this.insults(e.getName()).size())));
                  }
               }
            }
         }

      }
   }

   public List<String> insults(String name) {
      List insults;
      if (this.mode.getCurrentMode().equalsIgnoreCase("Advertisement")) {
         insults = Arrays.asList(Fiveware.getNAME() + " goes insane on " + name, name + " just died to " + Fiveware.getNAME() + " " + Fiveware.getVERSION(), name + ", wanna kill me? Just get " + Fiveware.getNAME(), "Tired of losing, " + name + "? Get " + Fiveware.getNAME() + " today!", "Download " + Fiveware.getNAME() + " to kick more ass then ever!", "Why " + Fiveware.getNAME() + "? Cause it is the addition of pure skill and incredible abilities", "I'm not racist or anything, but I only like " + Fiveware.getNAME() + " users " + name + ".", "What client should I choose, " + name + "? " + Fiveware.getNAME() + " or " + Fiveware.getNAME() + "?", "I don't hack I just " + Fiveware.getNAME(), "Look a divinity! He definitely must use " + Fiveware.getNAME() + "!", "In need of a cute present for Christmas, " + name + "? " + Fiveware.getNAME() + " is all you need!", "I have a good " + Fiveware.getNAME() + " config, don't blame me " + name, Fiveware.getNAME() + " never dies, unlike Technoblade", "I dont hack I just have the " + Fiveware.getNAME() + " gaming chair " + name, "Stop Hackustating me cuz im just " + Fiveware.getNAME(), "Imagine using anything but " + Fiveware.getNAME() + " " + name, "No hax just beta testing the anti-cheat with " + Fiveware.getNAME() + " " + name, "Don't forget to report me for " + Fiveware.getNAME() + " on the forums " + name + "!", "How come a noob like you not use " + Fiveware.getNAME() + ", " + name + "?", "Behind every " + Fiveware.getNAME() + " user is a swag human being, even cooler than " + name, name + "'s client sucks, he should get " + Fiveware.getNAME(), "Well, someone didnt use " + Fiveware.getNAME() + " (" + name + ")");
      } else if (this.mode.getCurrentMode().equalsIgnoreCase("Insults")) {
         insults = Arrays.asList("You’re the reason God created the middle finger " + name, "You’re a gray sprinkle on a rainbow cupcake " + name, "Bye. Hope to see you never " + name, "Grab a straw, because you suck " + name, "You are proof God has a sense of humor " + name, "Don’t worry about me. Worry about your skills " + name, "I hope your minecraft gf brings a date to your funeral ", "Sorry, not sorry " + name, "I like the way you try killing me " + name, "Left click to fight, by the way " + name);
      } else {
         insults = Arrays.asList("Just casually pounding " + name + " in this game", "I'm a yandere, and I love " + name + " :)", "If you want me to peg you again, just ask " + name, "you gonna be riding this blocky dick all night " + name + ":)", "If this client had a cum module ill use it in " + name + " ;)", name + "'s mother lookin juicy tho :)", "Next time ill use a hack client in BED " + name + " ;))", "Sorry daddy, I didn't mean to toggle aura on you " + name, "If you were words on a page, you would be fine print " + name, "I wanna fuck " + name + "'s blocky ass harder next time :)", "I hope to see you and me in the next SlipperyYT animation " + name, "I'll make your aim shaky next time you're in a fight with me " + name, "I'm going crazy just looking at " + name, "I wanna give you a virtual kiss " + name);
      }

      return insults;
   }
}
