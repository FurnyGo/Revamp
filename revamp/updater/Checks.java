// 
// Decompiled by Procyon v0.5.36
// 

package revamp.updater;

import java.io.IOException;
import revamp.userinterfaces.misc.Login;
import revamp.Revamp;

public class Checks
{
    public static boolean versionCheck() {
        if (Revamp.getVERSION().contains("Beta")) {
            return false;
        }
        boolean outdated = false;
        try {
            final String url = Login.callURL("https://pastebin.com/raw/7wE5PBVs");
            outdated = !url.equalsIgnoreCase(Revamp.getVERSION());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return outdated;
    }
}
