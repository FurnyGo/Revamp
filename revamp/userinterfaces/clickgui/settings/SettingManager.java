// 
// Decompiled by Procyon v0.5.36
// 

package revamp.userinterfaces.clickgui.settings;

import java.util.Iterator;
import revamp.modules.Module;
import java.util.ArrayList;

public class SettingManager
{
    public ArrayList<Setting> settings;
    
    public SettingManager() {
        this.settings = new ArrayList<Setting>();
    }
    
    public void registerSetting(final Setting setting) {
        this.settings.add(setting);
    }
    
    public ArrayList<Setting> getSetting() {
        return this.settings;
    }
    
    public Setting getSetting(final Module module, final String name) {
        for (final Setting setting : this.getSetting()) {
            if (setting.getModule().equals(module) && setting.name.equalsIgnoreCase(name)) {
                return setting;
            }
        }
        return null;
    }
}
