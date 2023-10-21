package fiveware.file.impl;

import fiveware.Fiveware;
import fiveware.file.Files;
import fiveware.interfaces.IFile;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;

@IFile(
   name = "client"
)
public class Client extends Files {
   public void readFile(BufferedReader bufferedReader) throws IOException {
      String line;
      while((line = bufferedReader.readLine()) != null) {
         String[] split = line.split("=");
         Fiveware.setClientCOLOR(new Color(Integer.parseInt(split[1]), Integer.parseInt(split[2]), Integer.parseInt(split[3])));
      }

   }

   public void writeFile(FileWriter fileWriter) throws IOException {
      fileWriter.write("COLOR=" + Fiveware.getClientCOLOR().toString().replace("r=", "").replace("g=", "").replace("b=", "").replace(",", "=").replace("java.awt.Color[", "").replace("]", "") + "\n");
   }
}
