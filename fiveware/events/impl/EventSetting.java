package fiveware.events.impl;

import fiveware.events.Event;
import fiveware.userinterfaces.clickgui.settings.Setting;

public class EventSetting extends Event {
   public Setting setting;

   public EventSetting(Setting setting) {
      this.setting = setting;
   }

   public Setting getSetting() {
      return this.setting;
   }
}
