// 
// Decompiled by Procyon v0.5.36
// 

package revamp.command;

import java.util.Iterator;
import revamp.modules.ModuleManager;
import revamp.Revamp;
import revamp.events.impl.EventChat;
import java.util.Collection;
import java.util.Arrays;
import revamp.command.impl.Friend;
import revamp.command.impl.File;
import revamp.command.impl.ClientColor;
import revamp.command.impl.Info;
import revamp.command.impl.Reload;
import revamp.command.impl.Help;
import revamp.command.impl.Description;
import revamp.command.impl.Toggle;
import revamp.command.impl.Say;
import revamp.command.impl.Bind;
import java.util.ArrayList;
import java.util.List;

public class CommandManager
{
    public static List<Command> commands;
    public String prefix;
    
    static {
        CommandManager.commands = new ArrayList<Command>();
    }
    
    public CommandManager() {
        this.prefix = this.getPrefix();
        CommandManager.commands.addAll(Arrays.asList(new Bind(), new Say(), new Toggle(), new Description(), new Help(), new Reload(), new Info(), new ClientColor(), new File(), new Friend()));
    }
    
    public static List<Command> getCMDS() {
        return CommandManager.commands;
    }
    
    public void chat(final EventChat event) {
        String message = event.getMessage();
        if (!message.startsWith(this.prefix)) {
            return;
        }
        event.setCancelled(true);
        message = message.substring(this.prefix.length());
        boolean foundCommand = false;
        if (message.split(" ").length > 0) {
            for (final Command command : CommandManager.commands) {
                if (command.alias.contains(message.split(" ")[0]) || command.name.equalsIgnoreCase(message.split(" ")[0])) {
                    command.onCommand(Arrays.copyOfRange(message.split(" "), 1, message.split(" ").length), message);
                    foundCommand = true;
                    break;
                }
            }
        }
        if (!foundCommand) {
            Revamp.getINSTANCE();
            final ModuleManager moduleManager = Revamp.moduleManager;
            ModuleManager.addChatMessage(true, "§cInvalid Command! §rTry §7.help §rfor a list of commands!");
        }
    }
    
    public String getPrefix() {
        return (this.prefix == null) ? "." : this.prefix;
    }
    
    public void setPrefix(final String prefix) {
        this.prefix = prefix;
    }
}
