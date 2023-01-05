// 
// Decompiled by Procyon v0.5.36
// 

package revamp.userinterfaces.clickgui;

import revamp.Revamp;
import revamp.modules.Module;
import revamp.userinterfaces.clickgui.settings.Setting;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.Minecraft;

public abstract class Element
{
    public Minecraft mc;
    public FontRenderer fr;
    public Setting setting;
    public int x;
    public int y;
    public int width;
    public int height;
    public boolean extended;
    
    public Element() {
        this.mc = Minecraft.getMinecraft();
        this.fr = this.mc.fontRendererObj;
    }
    
    public void updatePosition(final int x, final int y, final int width, final int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    public abstract void drawScreen(final int p0, final int p1, final float p2);
    
    public abstract void keyTyped(final char p0, final int p1);
    
    public abstract void mouseClicked(final int p0, final int p1, final int p2);
    
    public abstract void mouseReleased(final int p0, final int p1, final int p2);
    
    public static Setting getSetting(final Module module, final String name) {
        return Revamp.settingManager.getSetting(module, name);
    }
    
    public static Module moduleName(final String name) {
        return Revamp.moduleManager.getModuleByName(name);
    }
}
