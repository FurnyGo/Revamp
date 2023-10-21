package fiveware.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.Minecraft;

public abstract class Command {
   public String name;
   public String description;
   public List<String> alias = new ArrayList();
   public Minecraft mc = Minecraft.getMinecraft();

   public Command(String name, String description, String... alias) {
      this.name = name;
      this.description = description;
      this.alias = Arrays.asList(alias);
   }

   public abstract void onCommand(String[] var1, String var2);

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getDescription() {
      return this.description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public List<String> getAlias() {
      return this.alias;
   }

   public void setAlias(List<String> alias) {
      this.alias = alias;
   }
}
