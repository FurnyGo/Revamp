package fiveware.file;

import fiveware.Fiveware;
import fiveware.interfaces.IFile;
import fiveware.interfaces.Methods;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public abstract class Files implements Methods {
   public File file;
   public final File DIR;

   public File getFile() {
      return this.file;
   }

   public Files() {
      this.DIR = new File(mc.mcDataDir + "/" + Fiveware.getNAME() + "/saves");
      IFile iFile = (IFile)this.getClass().getAnnotation(IFile.class);
      this.file = new File(this.DIR, iFile.name() + ".5ware");
   }

   public abstract void readFile(BufferedReader var1) throws IOException;

   public abstract void writeFile(FileWriter var1) throws IOException;
}
