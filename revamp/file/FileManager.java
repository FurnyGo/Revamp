// 
// Decompiled by Procyon v0.5.36
// 

package revamp.file;

import java.io.Reader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Iterator;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Arrays;
import revamp.file.impl.Friends;
import revamp.file.impl.Settings;
import revamp.file.impl.Binds;
import revamp.file.impl.Toggled;
import revamp.Revamp;
import java.util.ArrayList;
import java.io.File;
import revamp.interfaces.Methods;

public class FileManager implements Methods
{
    public final File DIR;
    public ArrayList<Files> files;
    
    public FileManager() {
        this.DIR = new File(FileManager.mc.mcDataDir + "/" + Revamp.getNAME() + "/saves");
        (this.files = new ArrayList<Files>()).addAll(Arrays.asList(new Toggled(), new Binds(), new Settings(), new Friends()));
    }
    
    public void writeFile(final Class<? extends Files> clazz) {
        if (!this.DIR.exists()) {
            this.DIR.mkdirs();
        }
        for (final Files file : this.files) {
            if (file.getClass().equals(clazz)) {
                if (!file.getFile().exists()) {
                    try {
                        file.getFile().createNewFile();
                    }
                    catch (IOException ex) {}
                }
                try {
                    final FileWriter fileWriter = new FileWriter(file.getFile());
                    file.writeFile(fileWriter);
                    fileWriter.close();
                }
                catch (IOException ex2) {}
            }
        }
    }
    
    public void writeAllFiles() {
        if (!this.DIR.exists()) {
            this.DIR.mkdirs();
        }
        for (final Files file : this.files) {
            if (!file.getFile().exists()) {
                try {
                    file.getFile().createNewFile();
                }
                catch (IOException ex) {}
            }
            try {
                final FileWriter fileWriter = new FileWriter(file.getFile());
                file.writeFile(fileWriter);
                fileWriter.close();
            }
            catch (IOException ex2) {}
        }
    }
    
    public void readAllFiles() {
        if (this.DIR.exists()) {
            for (final Files file : this.files) {
                if (!file.getFile().exists()) {
                    try {
                        file.getFile().createNewFile();
                    }
                    catch (IOException ex) {}
                }
                try {
                    final BufferedReader reader = new BufferedReader(new FileReader(file.getFile()));
                    file.readFile(reader);
                    reader.close();
                }
                catch (IOException ex2) {}
            }
        }
    }
}
