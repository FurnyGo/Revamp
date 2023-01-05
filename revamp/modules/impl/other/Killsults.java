// 
// Decompiled by Procyon v0.5.36
// 

package revamp.modules.impl.other;

import java.util.Arrays;
import net.minecraft.client.Minecraft;
import revamp.Revamp;
import java.util.List;
import io.netty.util.internal.ThreadLocalRandom;
import net.minecraft.network.play.server.S02PacketChat;
import revamp.events.impl.EventPacket;
import revamp.events.impl.EventUpdate;
import revamp.events.Event;
import revamp.modules.Category;
import revamp.userinterfaces.clickgui.settings.Setting;
import revamp.interfaces.Methods;
import revamp.modules.Module;

public class Killsults extends Module implements Methods
{
    public Setting servermode;
    public Setting insultmode;
    
    public Killsults() {
        super("Killsults", "Killsults", "Insults people apon killing them.", -1, 0, Category.OTHER);
        this.servermode = new Setting("Server Mode", new String[] { "PikaNetwork", "Hypixel", "Minemora", "Vanilla" }, "Minemora", this);
        this.insultmode = new Setting("Insult Mode", new String[] { "Advertisement", "Insults", "Sus" }, "Advertisement", this);
    }
    
    @Override
    public void onEvent(final Event event) {
        if (!this.isToggled()) {
            return;
        }
        if (event instanceof EventUpdate) {
            this.setNameWithOutSpace("Killsults" + (this.information() ? (" §7" + this.servermode.getCurrentMode()) : ""));
        }
        if (event instanceof EventPacket && ((EventPacket)event).chatPacket() instanceof S02PacketChat) {
            final S02PacketChat packet = ((EventPacket)event).chatPacket();
            final String text = packet.getChatComponent().getUnformattedText();
            if (this.servermode.getCurrentMode().equalsIgnoreCase("PikaNetwork") && (text.contains("has been killed by " + this.getUsername()) || text.contains("was hunted down by " + this.getUsername()) || text.contains("stampled on a trap set by " + this.getUsername()) || text.contains("was thrown in to a volcano by " + this.getUsername()))) {
                this.getPlayer().sendChatMessage("!" + this.insults().get(ThreadLocalRandom.current().nextInt(0, this.insults().size())));
            }
            if (this.servermode.getCurrentMode().equalsIgnoreCase("Hypixel") && (text.contains("was killed by " + this.getUsername()) || text.contains("was thrown into the void by " + this.getUsername()) || text.contains("was thrown off a cliff by " + this.getUsername()) || text.contains("was turned into space dust by " + this.getUsername()))) {
                this.getPlayer().sendChatMessage("/shout " + this.insults().get(ThreadLocalRandom.current().nextInt(0, this.insults().size())));
            }
            if (this.servermode.getCurrentMode().equalsIgnoreCase("Minemora") && text.contains(String.valueOf(this.getUsername()) + " asesino a")) {
                this.getPlayer().sendChatMessage(this.insults().get(ThreadLocalRandom.current().nextInt(0, this.insults().size())));
            }
            if (this.servermode.getCurrentMode().equalsIgnoreCase("Vanilla") && text.contains("was slain by " + this.getUsername())) {
                this.getPlayer().sendChatMessage(this.insults().get(ThreadLocalRandom.current().nextInt(0, this.insults().size())));
            }
        }
    }
    
    private List<String> insults() {
        List<String> insults;
        if (this.insultmode.getCurrentMode().equalsIgnoreCase("Advertisement")) {
            insults = Arrays.asList(String.valueOf(Revamp.getNAME()) + " recoded goes insane", "You just died to " + Revamp.getNAME() + " " + Revamp.getVERSION(), "Me and the boys moving to OP client aka " + Revamp.getNAME(), "Wanna kill me? Just get " + Revamp.getNAME(), "Tired of losing? Get " + Revamp.getNAME() + " today!", "Flux? Vape? Nah i'm just using " + Revamp.getNAME(), "Currently using the " + Minecraft.getDebugFPS() + "fps master client called " + Revamp.getNAME(), "Download " + Revamp.getNAME() + " to kick more ass then ever!", "Why " + Revamp.getNAME() + "? Cause it is the addition of pure skill and incredible abilities", "I'm not racist or anything, but I only like " + Revamp.getNAME() + " users.", "What client should I choose? " + Revamp.getNAME() + " or " + Revamp.getNAME() + "?", "I don't hack I just " + Revamp.getNAME(), "Look a divinity! He definitely must use " + Revamp.getNAME() + "!", "In need of a cute present for Christmas? " + Revamp.getNAME() + " is all you need!", "I have a good " + Revamp.getNAME() + " config, don't blame me", String.valueOf(Revamp.getNAME()) + " never dies, unlike Technoblade", "Maybe I will be " + Revamp.getNAME() + ", I am already " + Revamp.getNAME(), "I dont hack, I just have the " + Revamp.getNAME() + " gaming chair", "Stop Hackustating me cuz im just " + Revamp.getNAME(), "Imagine using anything but " + Revamp.getNAME(), "No hax just beta testing the anti-cheat with " + Revamp.getNAME(), "Don't forget to report me for " + Revamp.getNAME() + " on the forums!", "don't use " + Revamp.getNAME() + "? ok boomer", "How come a noob like you not use " + Revamp.getNAME() + "?", "Behind every " + Revamp.getNAME() + " user, is an incredibly cool human being. Trust me, cooler than you.", "Your client sucks, just get " + Revamp.getNAME(), "Well, someone didnt use " + Revamp.getNAME());
        }
        else if (this.insultmode.getCurrentMode().equalsIgnoreCase("Insults")) {
            insults = Arrays.asList("You\u2019re the reason God created the middle finger", "You\u2019re a gray sprinkle on a rainbow cupcake", "Bye. Hope to see you never", "Grab a straw, because you suck", "You are proof God has a sense of humor", "Don\u2019t worry about me. Worry about your skills", "If you were an inanimate object, you\u2019d be a participation trophy", "I hope your minecraft gf brings a date to your funeral", "Sorry, not sorry", "I like the way you try killing me", "Left click to fight, by the way");
        }
        else {
            insults = Arrays.asList("You're so horny I had to kill you", "Make sure next time YOU rape me, hehe", "Just casually pounding you in this game", "I'm a yandere, and I love you :)", "If you want me to peg you again, just ask ok?", "you gonna be riding this blocky dick all night :)", "If this client had a cum module ill use it in YOU ;)", "Your mother lookin juicy tho :)", "Next time ill use a hack client in BED ;))", "Sorry daddy, I didn't mean to toggle aura on you", "If you were words on a page, you would be fine print", "I wanna fuck your blocky ass harder next time :)", "I hope to see you and me in the next SlipperYT animation", "I'll make your aim shaky next time you're in a fight with me :)", "I'm going crazy just looking at you", ":heart:", "I wanna give you a virtual kiss :>");
        }
        return insults;
    }
}
