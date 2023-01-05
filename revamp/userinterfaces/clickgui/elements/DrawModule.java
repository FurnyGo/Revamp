// 
// Decompiled by Procyon v0.5.36
// 

package revamp.userinterfaces.clickgui.elements;

import org.lwjgl.input.Keyboard;
import java.awt.Color;
import revamp.utilities.impl.render.Rainbow;
import net.minecraft.client.gui.Gui;
import java.util.Iterator;
import revamp.userinterfaces.clickgui.elements.guisettings.DrawType;
import revamp.userinterfaces.clickgui.elements.guisettings.DrawKey;
import revamp.userinterfaces.clickgui.elements.guisettings.DrawSlider;
import revamp.userinterfaces.clickgui.elements.guisettings.DrawComboBox;
import revamp.userinterfaces.clickgui.elements.guisettings.DrawCheckBox;
import revamp.userinterfaces.clickgui.settings.Setting;
import revamp.Revamp;
import revamp.modules.Module;
import revamp.userinterfaces.clickgui.Element;
import java.util.ArrayList;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.Minecraft;

public class DrawModule
{
    private final Minecraft mc;
    private final FontRenderer fr;
    public final ArrayList<Element> elements;
    public Module module;
    public int x;
    public int y;
    public int width;
    public int height;
    public boolean extended;
    
    public DrawModule(final Module module) {
        this.mc = Minecraft.getMinecraft();
        this.fr = Minecraft.getMinecraft().fontRendererObj;
        this.elements = new ArrayList<Element>();
        this.module = module;
        for (final Setting setting : Revamp.settingManager.getSetting()) {
            if (setting.getModule() == module) {
                if (setting.getType() == Setting.Type.CHECKBOX) {
                    this.elements.add(new DrawCheckBox(setting));
                }
                if (setting.getType() == Setting.Type.COMBOBOX) {
                    this.elements.add(new DrawComboBox(setting));
                }
                if (setting.getType() == Setting.Type.SLIDER) {
                    this.elements.add(new DrawSlider(setting));
                }
                if (setting.getType() == Setting.Type.KEY) {
                    this.elements.add(new DrawKey(setting));
                }
                if (setting.getType() != Setting.Type.TYPE) {
                    continue;
                }
                this.elements.add(new DrawType(setting));
            }
        }
    }
    
    public void updatePosition(final int x, final int y, final int width, final int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        Gui.drawRect(this.x - 2, this.y, this.x + 102, this.y + this.height, -1157627904);
        if (this.isHovered(mouseX, mouseY)) {
            Gui.drawRect(this.x, this.y, this.x + 100, this.y + this.height, 0);
        }
        final int textColor = this.module.isToggled() ? (Element.getSetting(Element.moduleName("ClickGUI"), "Rainbow").isToggled() ? Rainbow.rainbow(4.0f, 0.5f, 1.0f, 200L) : this.module.getCategory().getCategoryColor()) : new Color(-1).getRGB();
        this.fr.drawStringWithShadow(this.module.getName(), (float)(this.x + 5), this.y + this.height / 2 - this.fr.FONT_HEIGHT / 2 + 1, textColor);
        int settingWidth = 0;
        for (final Element element : this.elements) {
            this.fr.drawStringWithShadow(this.extended ? "-" : "+", (float)(this.x + 90), this.y + this.height / 2 - this.fr.FONT_HEIGHT / 2 + 1, textColor);
            final String settingName = element.setting.getName();
            if (element.setting.getType() == Setting.Type.CHECKBOX) {
                final String string = settingName;
                final int offset = 10;
                if (settingWidth < this.fr.getStringWidth(string) + this.height - 4 + offset) {
                    settingWidth = this.fr.getStringWidth(string) + this.height - 4 + offset;
                }
            }
            if (element.setting.getType() == Setting.Type.COMBOBOX) {
                final String string = String.valueOf(settingName) + (element.extended ? "-" : "+");
                if (settingWidth < this.fr.getStringWidth(string) + 22) {
                    settingWidth = this.fr.getStringWidth(string) + 22;
                }
                String[] modes;
                for (int length = (modes = element.setting.getModes()).length, i = 0; i < length; ++i) {
                    final String mode = modes[i];
                    final int offset2 = 10;
                    if (settingWidth < this.fr.getStringWidth(mode) + offset2) {
                        settingWidth = this.fr.getStringWidth(mode) + offset2;
                    }
                }
            }
            if (element.setting.getType() == Setting.Type.KEY) {
                final String typed = element.setting.getTyped();
                final DrawKey key = (DrawKey)element;
                final int offset3 = this.fr.getStringWidth(element.setting.getName()) + (key.isKeyTyped ? this.fr.getStringWidth("type...") : this.fr.getStringWidth(Keyboard.getKeyName(element.setting.getKey()))) + 12;
                if (settingWidth < this.fr.getStringWidth(typed) + offset3) {
                    settingWidth = this.fr.getStringWidth(typed) + offset3;
                }
            }
            if (element.setting.getType() == Setting.Type.TYPE) {
                final String typed = element.setting.getTyped();
                final int offset = this.fr.getStringWidth(element.setting.getName()) + 12;
                if (settingWidth < this.fr.getStringWidth(typed) + offset) {
                    settingWidth = this.fr.getStringWidth(typed) + offset;
                }
            }
            if (element.setting.getType() == Setting.Type.SLIDER) {
                final String string = String.valueOf(settingName) + "00.00";
                final int offset = 15;
                if (settingWidth >= this.fr.getStringWidth(string) + offset) {
                    continue;
                }
                settingWidth = this.fr.getStringWidth(string) + offset;
            }
        }
        int y = this.y;
        for (final Element element2 : this.elements) {
            if (element2.setting.isVisible() && this.extended) {
                element2.updatePosition(this.x + 102, y, settingWidth, this.height);
                element2.drawScreen(mouseX, mouseY, partialTicks);
                y += this.height;
                if (element2.setting.getType() != Setting.Type.COMBOBOX || !element2.extended) {
                    continue;
                }
                y += this.height * element2.setting.getModes().length;
            }
        }
    }
    
    public boolean isHovered(final int mouseX, final int mouseY) {
        return mouseX > this.x && mouseX < this.x + this.width && mouseY > this.y && mouseY < this.y + this.height;
    }
    
    public void keyTyped(final char typedChar, final int keyCode) {
        for (final Element element : this.elements) {
            if (element.setting.isVisible() && this.extended) {
                element.keyTyped(typedChar, keyCode);
            }
        }
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (this.isHovered(mouseX, mouseY)) {
            if (mouseButton == 0 && !Keyboard.isKeyDown(42)) {
                this.module.toggle();
            }
            if (mouseButton == 1) {
                this.extended = !this.extended;
            }
        }
        for (final Element element : this.elements) {
            if (element.setting.isVisible() && this.extended) {
                element.mouseClicked(mouseX, mouseY, mouseButton);
            }
        }
    }
    
    public void mouseReleased(final int mouseX, final int mouseY, final int state) {
        for (final Element element : this.elements) {
            if (element.setting.isVisible() && this.extended) {
                element.mouseReleased(mouseX, mouseY, state);
            }
        }
    }
}
