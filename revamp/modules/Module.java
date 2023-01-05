// 
// Decompiled by Procyon v0.5.36
// 

package revamp.modules;

import revamp.events.CMDEvent;
import revamp.events.Event;
import net.minecraft.client.Minecraft;

public class Module
{
    public Minecraft mc;
    public String name;
    public String nameWithOutSpace;
    private String description;
    private String info;
    private int color;
    private int key;
    public int slide;
    public boolean toggled;
    private Category category;
    
    public Module(final String name, final String nameWithOutSpace, final String description, final int color, final int key, final Category category) {
        this.mc = Minecraft.getMinecraft();
        this.info = "";
        this.slide = 0;
        this.name = name;
        this.nameWithOutSpace = nameWithOutSpace;
        this.description = description;
        this.color = color;
        this.key = key;
        this.category = category;
        this.toggled = false;
    }
    
    public void onEnable() {
    }
    
    public void onDisable() {
    }
    
    public void onEvent(final Event event) {
    }
    
    public void onChatEvent(final CMDEvent event) {
    }
    
    public void toggle() {
        this.toggled = !this.toggled;
        if (this.toggled) {
            this.onEnable();
            this.slide = 0;
        }
        else {
            this.onDisable();
            this.slide = 0;
        }
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(final String description) {
        this.description = description;
    }
    
    public String getInfo() {
        return this.info;
    }
    
    public void setInfo(final String info) {
        this.info = info;
    }
    
    public String getNameWithOutSpace() {
        return this.nameWithOutSpace;
    }
    
    public void setNameWithOutSpace(final String nameWithOutSpace) {
        this.nameWithOutSpace = nameWithOutSpace;
    }
    
    public int getColor() {
        return this.color;
    }
    
    public void setColor(final int color) {
        this.color = color;
    }
    
    public int getKey() {
        return this.key;
    }
    
    public void setKey(final int key) {
        this.key = key;
    }
    
    public boolean isToggled() {
        return this.toggled;
    }
    
    public void setToggled(final boolean toggled) {
        this.toggled = toggled;
    }
    
    public Category getCategory() {
        return this.category;
    }
    
    public void setCategory(final Category category) {
        this.category = category;
    }
}
