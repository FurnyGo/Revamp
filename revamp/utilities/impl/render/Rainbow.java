// 
// Decompiled by Procyon v0.5.36
// 

package revamp.utilities.impl.render;

import java.awt.Color;

public class Rainbow
{
    public static int rainbow(final float seconds, final float saturation, final float brightness, final long index) {
        final float hue = (System.currentTimeMillis() + index) % (int)(seconds * 1000.0f) / (seconds * 1000.0f);
        return Color.HSBtoRGB(hue, saturation, brightness);
    }
    
    public static int breath(final double offset, final Color color) {
        final double hue = Math.abs(Math.sin(System.currentTimeMillis() / 1.0E8 * 1.0 * 400000.0 + offset * 0.45)) / 2.0 + 1.0;
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();
        final int factor = (int)(1.0 / (1.0 - hue));
        if (red == 0 && green == 0 && blue == 0) {
            return new Color(factor, factor, factor, color.getAlpha()).hashCode();
        }
        if (red > 0 && red < hue) {
            red = factor;
        }
        if (green > 0 && green < hue) {
            green = factor;
        }
        if (blue > 0 && blue < hue) {
            blue = factor;
        }
        return new Color(Math.min((int)(red / hue), 255), Math.min((int)(green / hue), 255), Math.min((int)(blue / hue), 255), color.getAlpha()).hashCode();
    }
}
