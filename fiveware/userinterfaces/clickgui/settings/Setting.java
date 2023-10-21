package fiveware.userinterfaces.clickgui.settings;

import fiveware.Fiveware;
import fiveware.events.impl.EventSetting;
import fiveware.modules.Module;
import java.util.Arrays;

public class Setting {
   public String name;
   public String currentMode;
   public String currentTyped;
   public String[] modes;
   public float currentValue;
   public float minValue;
   public float maxValue;
   public boolean toggled;
   public boolean onlyInteger;
   public boolean visible = true;
   public int key;
   public Module module;
   public Setting.Type type;

   public Setting(String name, String typed, Module module) {
      this.name = name;
      this.currentTyped = typed;
      this.module = module;
      this.type = Setting.Type.TYPE;
      Fiveware.getINSTANCE();
      Fiveware.settingManager.registerSetting(this);
   }

   public Setting(String name, int key, Module module) {
      this.name = name;
      this.key = key;
      this.module = module;
      this.type = Setting.Type.KEY;
      Fiveware.getINSTANCE();
      Fiveware.settingManager.registerSetting(this);
   }

   public Setting(String name, boolean toggled, Module module) {
      this.name = name;
      this.toggled = toggled;
      this.module = module;
      this.type = Setting.Type.CHECKBOX;
      Fiveware.getINSTANCE();
      Fiveware.settingManager.registerSetting(this);
   }

   public Setting(String name, String[] modes, String currentMode, Module module) {
      Arrays.sort(modes);
      this.name = name;
      this.modes = modes;
      this.currentMode = currentMode;
      this.module = module;
      this.type = Setting.Type.COMBOBOX;
      Fiveware.getINSTANCE();
      Fiveware.settingManager.registerSetting(this);
   }

   public Setting(String name, float currentValue, float minValue, float maxValue, boolean onlyInt, Module module) {
      this.name = name;
      this.currentValue = currentValue;
      this.minValue = minValue;
      this.maxValue = maxValue;
      this.onlyInteger = onlyInt;
      this.module = module;
      this.type = Setting.Type.SLIDER;
      Fiveware.getINSTANCE();
      Fiveware.settingManager.registerSetting(this);
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getCurrentMode() {
      return this.currentMode;
   }

   public void setCurrentMode(String currentMode) {
      this.currentMode = currentMode;
   }

   public String[] getModes() {
      return this.modes;
   }

   public void setModes(String[] modes) {
      this.modes = modes;
   }

   public float getCurrentValue() {
      return this.currentValue;
   }

   public void setCurrentValue(float currentValue) {
      (new EventSetting(this)).onFire();
      this.currentValue = currentValue;
   }

   public float getMinValue() {
      return this.minValue;
   }

   public void setMinValue(float minValue) {
      this.minValue = minValue;
   }

   public float getMaxValue() {
      return this.maxValue;
   }

   public void setMaxValue(float maxValue) {
      this.maxValue = maxValue;
   }

   public boolean isToggled() {
      return this.toggled;
   }

   public void setToggled(boolean toggled) {
      this.toggled = toggled;
   }

   public int getKey() {
      return this.key;
   }

   public void setKey(int key) {
      this.key = key;
   }

   public boolean isOnlyInteger() {
      return this.onlyInteger;
   }

   public void setOnlyInteger(boolean onlyInteger) {
      this.onlyInteger = onlyInteger;
   }

   public boolean isVisible() {
      return this.visible;
   }

   public void setVisible(boolean visible) {
      this.visible = visible;
   }

   public Module getModule() {
      return this.module;
   }

   public void setModule(Module module) {
      this.module = module;
   }

   public Setting.Type getType() {
      return this.type;
   }

   public String getTyped() {
      return this.currentTyped;
   }

   public void setTyped(String currentTyped) {
      this.currentTyped = currentTyped;
   }

   public static enum Type {
      CHECKBOX,
      COMBOBOX,
      SLIDER,
      KEY,
      TYPE;
   }
}
