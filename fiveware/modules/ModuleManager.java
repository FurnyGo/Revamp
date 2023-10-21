package fiveware.modules;

import fiveware.modules.impl.combat.AimAssist;
import fiveware.modules.impl.combat.AntiBot;
import fiveware.modules.impl.combat.Aura;
import fiveware.modules.impl.combat.AutoClicker;
import fiveware.modules.impl.combat.Criticals;
import fiveware.modules.impl.combat.Hitboxes;
import fiveware.modules.impl.combat.Reach;
import fiveware.modules.impl.combat.Refill;
import fiveware.modules.impl.combat.TargetStrafe;
import fiveware.modules.impl.combat.Velocity;
import fiveware.modules.impl.combat.WTap;
import fiveware.modules.impl.movement.FastLadder;
import fiveware.modules.impl.movement.Flight;
import fiveware.modules.impl.movement.KeepSprint;
import fiveware.modules.impl.movement.SafeWalk;
import fiveware.modules.impl.movement.Scaffold;
import fiveware.modules.impl.movement.Speed;
import fiveware.modules.impl.movement.Sprint;
import fiveware.modules.impl.movement.Step;
import fiveware.modules.impl.other.AntiVoid;
import fiveware.modules.impl.other.AutoServer;
import fiveware.modules.impl.other.FlagDetection;
import fiveware.modules.impl.other.Killsults;
import fiveware.modules.impl.other.MemoryFix;
import fiveware.modules.impl.other.MurderMystery;
import fiveware.modules.impl.other.Panic;
import fiveware.modules.impl.other.Spammer;
import fiveware.modules.impl.other.Spoofer;
import fiveware.modules.impl.player.AutoArmor;
import fiveware.modules.impl.player.InventoryManager;
import fiveware.modules.impl.player.InventoryMove;
import fiveware.modules.impl.player.NoFall;
import fiveware.modules.impl.player.NoSlowdown;
import fiveware.modules.impl.player.Stealer;
import fiveware.modules.impl.render.Ambience;
import fiveware.modules.impl.render.Animations;
import fiveware.modules.impl.render.CameraNoClip;
import fiveware.modules.impl.render.Cape;
import fiveware.modules.impl.render.ClickGUI;
import fiveware.modules.impl.render.ESP;
import fiveware.modules.impl.render.Fullbright;
import fiveware.modules.impl.render.HUDModule;
import fiveware.modules.impl.render.ItemPhysics;
import fiveware.modules.impl.render.Mixed;
import fiveware.modules.impl.render.NameProtect;
import fiveware.modules.impl.render.NameTags;
import fiveware.modules.impl.render.NoHurtCam;
import fiveware.modules.impl.render.TargetHUD;
import fiveware.modules.impl.targets.Animals;
import fiveware.modules.impl.targets.Friends;
import fiveware.modules.impl.targets.Invisibles;
import fiveware.modules.impl.targets.Mobs;
import fiveware.modules.impl.targets.Others;
import fiveware.modules.impl.targets.Players;
import fiveware.modules.impl.targets.Teams;
import fiveware.modules.impl.world.AutoTool;
import fiveware.modules.impl.world.Eagle;
import fiveware.modules.impl.world.FastPlace;
import fiveware.modules.impl.world.Fucker;
import fiveware.modules.impl.world.Parkour;
import fiveware.modules.impl.world.Timer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class ModuleManager {
   public ArrayList<Module> modules = new ArrayList();

   public ModuleManager() {
      this.modules.addAll(Arrays.asList(new MurderMystery(), new InventoryMove(), new CameraNoClip(), new NameProtect(), new InventoryManager(), new NoSlowdown(), new TargetStrafe(), new AutoClicker(), new AimAssist(), new ItemPhysics(), new FastLadder(), new KeepSprint(), new FlagDetection(), new AutoServer(), new NoHurtCam(), new TargetHUD(), new NameTags(), new Animations(), new FastPlace(), new AutoTool(), new Fullbright(), new Invisibles(), new Ambience(), new SafeWalk(), new Scaffold(), new AutoArmor(), new Hitboxes(), new MemoryFix(), new Parkour(), new AntiVoid(), new Criticals(), new Spammer(), new Spoofer(), new Killsults(), new Players(), new Friends(), new Velocity(), new ClickGUI(), new Stealer(), new Animals(), new Others(), new Fucker(), new AntiBot(), new Sprint(), new Speed(), new Panic(), new Reach(), new NoFall(), new Teams(), new Eagle(), new Flight(), new Mobs(), new Timer(), new Refill(), new Step(), new Mixed(), new Cape(), new HUDModule(), new ESP(), new WTap(), new Aura()));
   }

   public ArrayList<Module> getModules() {
      return this.modules;
   }

   public Module getModuleByName(String name) {
      Iterator var3 = this.getModules().iterator();

      Module m;
      do {
         if (!var3.hasNext()) {
            return null;
         }

         m = (Module)var3.next();
      } while(!m.getNameWithOutSpace().trim().equalsIgnoreCase(name) && !m.toString().trim().equalsIgnoreCase(name.trim()));

      return m;
   }

   public List<Module> getModuleByCategory(Category c) {
      List<Module> modules = new ArrayList();
      Iterator var4 = this.getModules().iterator();

      while(var4.hasNext()) {
         Module m = (Module)var4.next();
         if (m.getCategory() == c) {
            modules.add(m);
         }
      }

      return modules;
   }
}
