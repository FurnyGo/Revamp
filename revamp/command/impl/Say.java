// 
// Decompiled by Procyon v0.5.36
// 

package revamp.command.impl;

import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.client.Minecraft;
import revamp.modules.ModuleManager;
import revamp.Revamp;
import revamp.command.Command;

public class Say extends Command
{
    public Say() {
        super("Say", "Sends a packet to say anything in chat.", new String[] { "s" });
    }
    
    @Override
    public void onCommand(final String[] args, final String command) {
        if (args.length == 0) {
            Revamp.getINSTANCE();
            final ModuleManager moduleManager = Revamp.moduleManager;
            ModuleManager.addChatMessage(true, "§cInvalid! Try §7.s(ay) <message> §cto send a chat §cmessage.");
        }
        if (args.length > 0) {
            Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C01PacketChatMessage(String.join(" ", (CharSequence[])args)));
        }
    }
}
