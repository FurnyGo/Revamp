// 
// Decompiled by Procyon v0.5.36
// 

package revamp.utilities.impl.misc;

import java.util.List;
import revamp.utilities.impl.time.Time;

public class Logger
{
    public static void log(final String information) {
        System.out.println("[" + Time.currentTime(true, ":") + "] [Client thread/RVMP]: " + information);
    }
    
    public static void log(final int information) {
        System.out.println("[" + Time.currentTime(true, ":") + "] [Client thread/RVMP]: " + information);
    }
    
    public static void log(final double information) {
        System.out.println("[" + Time.currentTime(true, ":") + "] [Client thread/RVMP]: " + information);
    }
    
    public static void log(final float information) {
        System.out.println("[" + Time.currentTime(true, ":") + "] [Client thread/RVMP]: " + information);
    }
    
    public static void log(final long information) {
        System.out.println("[" + Time.currentTime(true, ":") + "] [Client thread/RVMP]: " + information);
    }
    
    public static void log(final boolean information) {
        System.out.println("[" + Time.currentTime(true, ":") + "] [Client thread/RVMP]: " + information);
    }
    
    public static void log(final List<String> information) {
        System.out.println("[" + Time.currentTime(true, ":") + "] [Client thread/RVMP]: " + information);
    }
}
