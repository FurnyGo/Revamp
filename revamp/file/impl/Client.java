// 
// Decompiled by Procyon v0.5.36
// 

package revamp.file.impl;

import java.io.FileWriter;
import java.io.IOException;
import revamp.Revamp;
import java.awt.Color;
import java.io.BufferedReader;
import revamp.interfaces.IFile;
import revamp.file.Files;

@IFile(name = "client")
public class Client extends Files
{
    @Override
    public void readFile(final BufferedReader bufferedReader) throws IOException {
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            final String[] split = line.split("=");
            Revamp.setClientCOLOR(new Color(Integer.parseInt(split[1]), Integer.parseInt(split[2]), Integer.parseInt(split[3])));
        }
    }
    
    @Override
    public void writeFile(final FileWriter fileWriter) throws IOException {
        fileWriter.write("COLOR=" + Revamp.getClientCOLOR().toString().replace("r=", "").replace("g=", "").replace("b=", "").replace(",", "=").replace("java.awt.Color[", "").replace("]", "") + "\n");
    }
}
