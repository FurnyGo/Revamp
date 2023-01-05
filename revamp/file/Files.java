// 
// Decompiled by Procyon v0.5.36
// 

package revamp.file;

import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import revamp.interfaces.IFile;
import revamp.Revamp;
import java.io.File;
import revamp.interfaces.Methods;

public abstract class Files implements Methods
{
    public File file;
    public final File DIR;
    
    public File getFile() {
        return this.file;
    }
    
    public Files() {
        this.DIR = new File(Files.mc.mcDataDir + "/" + Revamp.getNAME() + "/saves");
        final IFile iFile = this.getClass().getAnnotation(IFile.class);
        this.file = new File(this.DIR, String.valueOf(iFile.name()) + ".rvmp");
    }
    
    public abstract void readFile(final BufferedReader p0) throws IOException;
    
    public abstract void writeFile(final FileWriter p0) throws IOException;
}
