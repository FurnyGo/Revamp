package fiveware.command;

import fiveware.Fiveware;
import fiveware.command.impl.Bind;
import fiveware.command.impl.ClientColor;
import fiveware.command.impl.Description;
import fiveware.command.impl.File;
import fiveware.command.impl.Friend;
import fiveware.command.impl.Help;
import fiveware.command.impl.Info;
import fiveware.command.impl.Reload;
import fiveware.command.impl.Say;
import fiveware.command.impl.Toggle;
import fiveware.command.impl.VClip;
import fiveware.events.impl.EventChat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class CommandManager {
   public static List<Command> commands = new ArrayList();
   public String prefix = this.getPrefix();

   public CommandManager() {
      commands.addAll(Arrays.asList(new Bind(), new Say(), new Toggle(), new Description(), new Help(), new Reload(), new Info(), new ClientColor(), new File(), new Friend(), new VClip()));
   }

   public static List<Command> getCMDS() {
      return commands;
   }

   public void chat(EventChat event) {
      String message = event.getMessage();
      if (message.startsWith(this.prefix)) {
         event.setCancelled(true);
         message = message.substring(this.prefix.length());
         boolean cmd = false;
         if (message.split(" ").length > 0) {
            label32: {
               Iterator var5 = commands.iterator();

               Command command;
               do {
                  if (!var5.hasNext()) {
                     break label32;
                  }

                  command = (Command)var5.next();
               } while(!command.alias.contains(message.split(" ")[0]) && !command.name.equalsIgnoreCase(message.split(" ")[0]));

               command.onCommand((String[])Arrays.copyOfRange(message.split(" "), 1, message.split(" ").length), message);
               cmd = true;
            }
         }

         if (!cmd) {
            Fiveware.getINSTANCE();
            Fiveware.sendChatMessage(true, "§cType §4.help §cfor a list of commands.");
         }

      }
   }

   public String getPrefix() {
      return this.prefix == null ? "." : this.prefix;
   }

   public void setPrefix(String prefix) {
      this.prefix = prefix;
   }
}
