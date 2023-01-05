// 
// Decompiled by Procyon v0.5.36
// 

package revamp.userinterfaces.clickgui.settings;

import revamp.events.impl.EventSetting;
import java.util.Arrays;
import revamp.Revamp;
import revamp.modules.Module;

public class Setting
{
    public String name;
    public String currentMode;
    public String currentTyped;
    public String[] modes;
    public float currentValue;
    public float minValue;
    public float maxValue;
    public boolean toggled;
    public boolean onlyInteger;
    public boolean visible;
    public int key;
    public Module module;
    public Type type;
    
    public Setting(final String name, final String typed, final Module module) {
        this.visible = true;
        this.name = name;
        this.currentTyped = typed;
        this.module = module;
        this.type = Type.TYPE;
        Revamp.getINSTANCE();
        Revamp.settingManager.registerSetting(this);
    }
    
    public Setting(final String name, final int key, final Module module) {
        this.visible = true;
        this.name = name;
        this.key = key;
        this.module = module;
        this.type = Type.KEY;
        Revamp.getINSTANCE();
        Revamp.settingManager.registerSetting(this);
    }
    
    public Setting(final String name, final boolean toggled, final Module module) {
        this.visible = true;
        this.name = name;
        this.toggled = toggled;
        this.module = module;
        this.type = Type.CHECKBOX;
        Revamp.getINSTANCE();
        Revamp.settingManager.registerSetting(this);
    }
    
    public Setting(final String name, final String[] modes, final String currentMode, final Module module) {
        this.visible = true;
        Arrays.sort(modes);
        this.name = name;
        this.modes = modes;
        this.currentMode = currentMode;
        this.module = module;
        this.type = Type.COMBOBOX;
        Revamp.getINSTANCE();
        Revamp.settingManager.registerSetting(this);
    }
    
    public Setting(final String name, final float currentValue, final float minValue, final float maxValue, final boolean onlyInt, final Module module) {
        this.visible = true;
        this.name = name;
        this.currentValue = currentValue;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.onlyInteger = onlyInt;
        this.module = module;
        this.type = Type.SLIDER;
        Revamp.getINSTANCE();
        Revamp.settingManager.registerSetting(this);
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public String getCurrentMode() {
        return this.currentMode;
    }
    
    public void setCurrentMode(final String currentMode) {
        this.currentMode = currentMode;
    }
    
    public String[] getModes() {
        return this.modes;
    }
    
    public void setModes(final String[] modes) {
        this.modes = modes;
    }
    
    public float getCurrentValue() {
        return this.currentValue;
    }
    
    public void setCurrentValue(final float currentValue) {
        new EventSetting(this).onFire();
        this.currentValue = currentValue;
    }
    
    public float getMinValue() {
        return this.minValue;
    }
    
    public void setMinValue(final float minValue) {
        this.minValue = minValue;
    }
    
    public float getMaxValue() {
        return this.maxValue;
    }
    
    public void setMaxValue(final float maxValue) {
        this.maxValue = maxValue;
    }
    
    public boolean isToggled() {
        return this.toggled;
    }
    
    public void setToggled(final boolean toggled) {
        this.toggled = toggled;
    }
    
    public int getKey() {
        return this.key;
    }
    
    public void setKey(final int key) {
        this.key = key;
    }
    
    public boolean isOnlyInteger() {
        return this.onlyInteger;
    }
    
    public void setOnlyInteger(final boolean onlyInteger) {
        this.onlyInteger = onlyInteger;
    }
    
    public boolean isVisible() {
        return this.visible;
    }
    
    public void setVisible(final boolean visible) {
        this.visible = visible;
    }
    
    public Module getModule() {
        return this.module;
    }
    
    public void setModule(final Module module) {
        this.module = module;
    }
    
    public Type getType() {
        return this.type;
    }
    
    public String getTyped() {
        return this.currentTyped;
    }
    
    public void setTyped(final String currentTyped) {
        this.currentTyped = currentTyped;
    }
    
    public enum Type
    {
        CHECKBOX("CHECKBOX", 0), 
        COMBOBOX("COMBOBOX", 1), 
        SLIDER("SLIDER", 2), 
        KEY("KEY", 3), 
        TYPE("TYPE", 4);
        
        private Type(final String name, final int ordinal) {
        }
    }
}
