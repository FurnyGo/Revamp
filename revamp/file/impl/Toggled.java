// 
// Decompiled by Procyon v0.5.36
// 

package revamp.file.impl;

import java.util.Iterator;
import java.io.FileWriter;
import java.io.IOException;
import revamp.modules.Module;
import revamp.Revamp;
import java.io.BufferedReader;
import revamp.interfaces.IFile;
import revamp.file.Files;

@IFile(name = "toggled")
public class Toggled extends Files
{
    @Override
    public void readFile(final BufferedReader bufferedReader) throws IOException {
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            final String[] split = line.split("=");
            final Module module = Revamp.moduleManager.getModuleByName(split[0]);
            if (module != null) {
                module.setToggled(Boolean.parseBoolean(split[1]));
            }
        }
    }
    
    @Override
    public void writeFile(final FileWriter fileWriter) throws IOException {
        for (final Module module : Revamp.moduleManager.getModules()) {
            fileWriter.write(String.valueOf(module.getName()) + "=" + module.isToggled() + "\n");
        }
    }
}
