package fiveware.file.impl;

import fiveware.Fiveware;
import fiveware.file.Files;
import fiveware.interfaces.IFile;
import fiveware.modules.Module;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

@IFile(
   name = "binds"
)
public class Binds extends Files {
   public void readFile(BufferedReader bufferedReader) throws IOException {
      String line;
      while((line = bufferedReader.readLine()) != null) {
         String[] args = line.split("=");
         Module module = Fiveware.moduleManager.getModuleByName(args[0]);
         if (module != null && args.length == 2) {
            module.setKey(Integer.parseInt(args[1]));
         }
      }

   }

   public void writeFile(FileWriter fileWriter) throws IOException {
      Iterator var3 = Fiveware.moduleManager.getModules().iterator();

      while(var3.hasNext()) {
         Module module = (Module)var3.next();
         fileWriter.write(module.getNameWithOutSpace() + "=" + module.getKey() + "\n");
      }

   }
}
