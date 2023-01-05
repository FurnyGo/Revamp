// 
// Decompiled by Procyon v0.5.36
// 

package revamp.updater;

import java.io.InputStream;
import java.io.IOException;
import java.io.FileOutputStream;
import java.net.URL;
import revamp.Revamp;
import java.io.File;
import revamp.utilities.impl.misc.Logger;

public class Updater extends Thread
{
    public static int percentage;
    
    static {
        Updater.percentage = 0;
    }
    
    public static void update() {
        Logger.log("Received update request!");
        final String clientFolder = String.valueOf(new File("versions").getAbsolutePath()) + "\\" + Revamp.getNAME();
        final File file = new File(clientFolder);
        if (!file.exists()) {
            file.mkdir();
            Logger.log("Made file directory");
        }
        try {
            final InputStream stream = new URL("https://github.com/frlgerlxeyew/sputithlwure/raw/main/cutruvufroha/jechijebroma/Revamp.jar").openStream();
            Updater.percentage = (int)Math.floor(Math.random() * 5.0 + 18.0);
            Logger.log("Opened URL stream");
            final FileOutputStream filestream = new FileOutputStream(new File(String.valueOf(clientFolder) + "\\" + Revamp.getNAME() + ".jar"));
            Updater.percentage = (int)Math.floor(Math.random() * 5.0 + 38.0);
            Logger.log("Opened file stream");
            int length = -1;
            final byte[] buffer = new byte[1024];
            Updater.percentage = (int)Math.floor(Math.random() * 5.0 + 58.0);
            Logger.log("Replacing contents");
            while ((length = stream.read(buffer)) > -1) {
                filestream.write(buffer, 0, length);
            }
            filestream.close();
            Updater.percentage = (int)Math.floor(Math.random() * 5.0 + 78.0);
            Logger.log("Closed file stream");
            stream.close();
            Updater.percentage = 100;
            Logger.log("Closed URL stream");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        Logger.log("Finished! Shutting down.");
        Revamp.getINSTANCE().shutdown();
        System.exit(0);
    }
}
