package fiveware.file;

import fiveware.Fiveware;
import fiveware.file.impl.Binds;
import fiveware.file.impl.Client;
import fiveware.file.impl.Friends;
import fiveware.file.impl.Settings;
import fiveware.file.impl.Toggled;
import fiveware.interfaces.Methods;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class FileManager implements Methods {
   public final File DIR;
   public ArrayList<Files> files;

   public FileManager() {
      this.DIR = new File(mc.mcDataDir + "/" + Fiveware.getNAME() + "/saves");
      this.files = new ArrayList();
      this.files.addAll(Arrays.asList(new Toggled(), new Binds(), new Settings(), new Friends(), new Client()));
   }

   public void writeFile(Class<? extends Files> clazz) {
      if (!this.DIR.exists()) {
         this.DIR.mkdirs();
      }

      Iterator var3 = this.files.iterator();

      while(var3.hasNext()) {
         Files file = (Files)var3.next();
         if (file.getClass().equals(clazz)) {
            if (!file.getFile().exists()) {
               try {
                  file.getFile().createNewFile();
               } catch (IOException var5) {
               }
            }

            try {
               FileWriter fileWriter = new FileWriter(file.getFile());
               file.writeFile(fileWriter);
               fileWriter.close();
            } catch (IOException var6) {
            }
         }
      }

   }

   public void writeAllFiles() {
      if (!this.DIR.exists()) {
         this.DIR.mkdirs();
      }

      Iterator var2 = this.files.iterator();

      while(var2.hasNext()) {
         Files file = (Files)var2.next();
         if (!file.getFile().exists()) {
            try {
               file.getFile().createNewFile();
            } catch (IOException var4) {
            }
         }

         try {
            FileWriter fileWriter = new FileWriter(file.getFile());
            file.writeFile(fileWriter);
            fileWriter.close();
         } catch (IOException var5) {
         }
      }

   }

   public void readAllFiles() {
      if (this.DIR.exists()) {
         Iterator var2 = this.files.iterator();

         while(var2.hasNext()) {
            Files file = (Files)var2.next();
            if (!file.getFile().exists()) {
               try {
                  file.getFile().createNewFile();
               } catch (IOException var5) {
               }
            }

            try {
               BufferedReader reader = new BufferedReader(new FileReader(file.getFile()));
               file.readFile(reader);
               reader.close();
            } catch (IOException var4) {
            }
         }
      }

   }
}
