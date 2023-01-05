// 
// Decompiled by Procyon v0.5.36
// 

package revamp.events.impl;

import revamp.userinterfaces.clickgui.settings.Setting;
import revamp.events.Event;

public class EventSetting extends Event
{
    public Setting setting;
    
    public EventSetting(final Setting setting) {
        this.setting = setting;
    }
    
    public Setting getSetting() {
        return this.setting;
    }
}
