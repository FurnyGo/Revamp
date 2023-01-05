// 
// Decompiled by Procyon v0.5.36
// 

package revamp.modules;

import net.minecraft.util.IChatComponent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.client.Minecraft;
import revamp.Revamp;
import java.util.List;
import java.util.Iterator;
import java.util.Collection;
import java.util.Arrays;
import revamp.modules.impl.targets.Friends;
import revamp.modules.impl.render.ESP;
import revamp.modules.impl.render.HUDModule;
import revamp.modules.impl.render.Cape;
import revamp.modules.impl.movement.Step;
import revamp.modules.impl.combat.Aura;
import revamp.modules.impl.world.Timer;
import revamp.modules.impl.targets.Mobs;
import revamp.modules.impl.movement.Flight;
import revamp.modules.impl.world.Eagle;
import revamp.modules.impl.targets.Teams;
import revamp.modules.impl.player.NoFall;
import revamp.modules.impl.combat.Reach;
import revamp.modules.impl.other.Panic;
import revamp.modules.impl.movement.Speed;
import revamp.modules.impl.movement.Sprint;
import revamp.modules.impl.combat.AntiBot;
import revamp.modules.impl.world.Fucker;
import revamp.modules.impl.targets.Others;
import revamp.modules.impl.targets.Animals;
import revamp.modules.impl.player.Stealer;
import revamp.modules.impl.render.ClickGUI;
import revamp.modules.impl.combat.Velocity;
import revamp.modules.impl.targets.Players;
import revamp.modules.impl.other.Spammer;
import revamp.modules.impl.other.Killsults;
import revamp.modules.impl.combat.Criticals;
import revamp.modules.impl.combat.Hitboxes;
import revamp.modules.impl.movement.Scaffold;
import revamp.modules.impl.player.AutoArmor;
import revamp.modules.impl.player.InventoryManager;
import revamp.modules.impl.movement.SafeWalk;
import revamp.modules.impl.render.Ambience;
import revamp.modules.impl.targets.Invisibles;
import revamp.modules.impl.render.NameTags;
import revamp.modules.impl.render.Fullbright;
import revamp.modules.impl.world.AutoTool;
import revamp.modules.impl.world.FastPlace;
import revamp.modules.impl.render.TargetHUD;
import revamp.modules.impl.render.Animations;
import revamp.modules.impl.render.NoHurtCam;
import revamp.modules.impl.movement.KeepSprint;
import revamp.modules.impl.movement.FastLadder;
import revamp.modules.impl.render.ItemPhysics;
import revamp.modules.impl.combat.AutoClicker;
import revamp.modules.impl.player.NoSlowdown;
import revamp.modules.impl.render.ScoreboardModule;
import revamp.modules.impl.render.NameProtect;
import revamp.modules.impl.player.InventoryMove;
import revamp.modules.impl.other.MurderMystery;
import java.util.ArrayList;

public class ModuleManager
{
    public ArrayList<Module> modules;
    
    public ModuleManager() {
        (this.modules = new ArrayList<Module>()).addAll(Arrays.asList(new MurderMystery(), new InventoryMove(), new NameProtect(), new ScoreboardModule(), new NoSlowdown(), new AutoClicker(), new ItemPhysics(), new FastLadder(), new KeepSprint(), new NoHurtCam(), new Animations(), new TargetHUD(), new FastPlace(), new AutoTool(), new Fullbright(), new NameTags(), new Invisibles(), new Ambience(), new SafeWalk(), new InventoryManager(), new AutoArmor(), new Scaffold(), new Hitboxes(), new Criticals(), new Killsults(), new Spammer(), new Players(), new Velocity(), new ClickGUI(), new Stealer(), new Animals(), new Others(), new Fucker(), new AntiBot(), new Sprint(), new Speed(), new Panic(), new Reach(), new NoFall(), new Teams(), new Eagle(), new Flight(), new Mobs(), new Timer(), new Aura(), new Step(), new Cape(), new HUDModule(), new ESP(), new Friends()));
    }
    
    public ArrayList<Module> getModules() {
        return this.modules;
    }
    
    public Module getModuleByName(final String name) {
        for (final Module m : this.getModules()) {
            if (!m.getName().trim().equalsIgnoreCase(name) && !m.toString().trim().equalsIgnoreCase(name.trim())) {
                continue;
            }
            return m;
        }
        return null;
    }
    
    public List<Module> getModuleByCategory(final Category c) {
        final List<Module> modules = new ArrayList<Module>();
        for (final Module m : this.getModules()) {
            if (m.getCategory() == c) {
                modules.add(m);
            }
        }
        return modules;
    }
    
    public static void addChatMessage(final boolean error, String message) {
        message = "§3§l" + Revamp.getNAME() + " §7[" + (error ? "§4!" : "§8?") + "§7] §f» §r" + message;
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(message));
    }
}
