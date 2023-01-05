// 
// Decompiled by Procyon v0.5.36
// 

package revamp.modules.impl.render;

import revamp.events.impl.EventText;
import revamp.events.impl.EventUpdate;
import revamp.events.Event;
import revamp.modules.Category;
import revamp.userinterfaces.clickgui.settings.Setting;
import revamp.interfaces.Methods;
import revamp.modules.Module;

public class NameProtect extends Module implements Methods
{
    public static String formattedName;
    public Setting name;
    
    static {
        NameProtect.formattedName = "";
    }
    
    public NameProtect() {
        super("NameProtect", "NameProtect", "Protects your name.", -1, 0, Category.RENDER);
        this.name = new Setting("Name", "&3RVMPClient", this);
    }
    
    @Override
    public void onEvent(final Event event) {
        if (!this.isToggled()) {
            return;
        }
        if (event instanceof EventUpdate) {
            NameProtect.formattedName = this.name.getTyped();
            if (this.name.getTyped().contains("&")) {
                NameProtect.formattedName = NameProtect.formattedName.replaceAll("&", "§");
            }
            this.setNameWithOutSpace("NameProtect" + (this.information() ? (" §7" + NameProtect.formattedName) : ""));
        }
        if (event instanceof EventText && ((EventText)event).text.contains(this.getUsername())) {
            ((EventText)event).text = ((EventText)event).text.replaceAll(this.getUsername(), NameProtect.formattedName);
        }
    }
}
