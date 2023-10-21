package fiveware.events.impl;

import fiveware.events.Event;
import net.minecraft.network.Packet;

public class EventPacketSend extends Event {
   private Packet packet = null;

   public EventPacketSend(Packet packet) {
      this.setPacket(packet);
   }

   public Packet getPacket() {
      return this.packet;
   }

   public void setPacket(Packet packet) {
      this.packet = packet;
   }
}
