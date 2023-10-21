package fiveware.modules.impl.render;

import fiveware.events.Event;
import fiveware.modules.Category;
import fiveware.modules.Module;
import fiveware.userinterfaces.clickgui.settings.Setting;

public class HUDModule extends Module {
   public Setting shadow = new Setting("Shadow", true, this);
   public Setting title = new Setting("Title", true, this);
   public Setting btitle = new Setting("Bold Title", true, this);
   public Setting titlemode = new Setting("Title Mode", new String[]{"None", "One Letter", "All", "Jello"}, "All", this);
   public Setting titletext = new Setting("Title Text", "fiveware", this);
   public Setting arraylist = new Setting("Arraylist", true, this);
   public Setting align = new Setting("Arraylist Align", new String[]{"Left", "Right"}, "Left", this);
   public Setting outline = new Setting("Outline", true, this);
   public Setting outlinemode = new Setting("Outline Mode", new String[]{"Fiveware", "Old Tenacity"}, "Fiveware", this);
   public Setting bg = new Setting("Background", true, this);
   public Setting important = new Setting("Important", true, this);
   public Setting sound = new Setting("Sound", false, this);
   public Setting x = new Setting("Pos X", 5.0F, 0.0F, 25.0F, true, this);
   public Setting y = new Setting("Pos Y", 20.0F, 0.0F, 25.0F, true, this);
   public Setting showinfo = new Setting("Show Information", false, this);
   public Setting spaced = new Setting("Spaced Name", true, this);
   public Setting lowercase = new Setting("Lowercase", false, this);
   public Setting hotbar = new Setting("Hotbar", true, this);
   public Setting showfps = new Setting("Show FPS", true, this);
   public Setting showxyz = new Setting("Show XYZ", false, this);
   public Setting showbps = new Setting("Show BPS", true, this);
   public Setting rainbow = new Setting("Rainbow", true, this);
   public Setting rainbowmode = new Setting("Rainbow Mode", new String[]{"Regular", "Mixed", "Astolfo"}, "Regular", this);

   public HUDModule() {
      super("HUD", "HUD", "Shows title, arraylist and more.", -1, 0, Category.RENDER);
   }

   public void onEvent(Event event) {
      if (this.title.isToggled()) {
         this.titlemode.visible = true;
         this.titletext.visible = true;
         this.btitle.visible = true;
      } else {
         this.titlemode.visible = false;
         this.titletext.visible = false;
         this.btitle.visible = false;
      }

      if (this.arraylist.isToggled()) {
         this.showinfo.visible = true;
         this.lowercase.visible = true;
         this.outline.visible = true;
         this.bg.visible = true;
         this.align.visible = true;
         this.spaced.visible = true;
         if (this.outline.isToggled()) {
            this.outlinemode.visible = true;
         } else {
            this.outlinemode.visible = false;
         }

         this.x.visible = true;
         this.y.visible = true;
         this.important.visible = true;
         this.sound.visible = true;
      } else {
         this.sound.visible = false;
         this.showinfo.visible = false;
         this.lowercase.visible = false;
         this.outline.visible = false;
         this.bg.visible = false;
         this.align.visible = false;
         this.spaced.visible = false;
         this.outlinemode.visible = false;
         this.x.visible = false;
         this.y.visible = false;
         this.important.visible = false;
      }

      if (this.hotbar.isToggled()) {
         this.showfps.visible = true;
         this.showxyz.visible = true;
         this.showbps.visible = true;
      } else {
         this.showfps.visible = false;
         this.showxyz.visible = false;
         this.showbps.visible = false;
      }

      if (this.rainbow.isToggled()) {
         this.rainbowmode.visible = true;
      } else {
         this.rainbowmode.visible = false;
      }

   }
}
