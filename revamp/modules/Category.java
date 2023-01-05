// 
// Decompiled by Procyon v0.5.36
// 

package revamp.modules;

import java.awt.Color;

public enum Category
{
    COMBAT("COMBAT", 0, "Combat"), 
    MOVEMENT("MOVEMENT", 1, "Movement"), 
    RENDER("RENDER", 2, "Render"), 
    PLAYER("PLAYER", 3, "Player"), 
    WORLD("WORLD", 4, "World"), 
    OTHER("OTHER", 5, "Other"), 
    TARGETS("TARGETS", 6, "Targets");
    
    public String name;
    private int index;
    
    public int getCategoryColor() {
        switch (this) {
            case COMBAT: {
                return Color.RED.darker().getRGB();
            }
            case MOVEMENT: {
                return Color.GREEN.getRGB();
            }
            case RENDER: {
                return Color.CYAN.getRGB();
            }
            case PLAYER: {
                return Color.MAGENTA.darker().getRGB();
            }
            case WORLD: {
                return Color.YELLOW.getRGB();
            }
            case OTHER: {
                return Color.LIGHT_GRAY.getRGB();
            }
            case TARGETS: {
                return Color.RED.getRGB();
            }
            default: {
                return 16777215;
            }
        }
    }
    
    private Category(final String name2, final int ordinal, final String name) {
        this.name = name;
    }
}
