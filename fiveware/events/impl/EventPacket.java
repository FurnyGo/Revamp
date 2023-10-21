package fiveware.events.impl;

import fiveware.events.Event;
import net.minecraft.network.Packet;

public class EventPacket extends Event {
   private Packet packet;

   public EventPacket(Packet packet) {
      this.packet = packet;
   }

   public Packet getPacket() {
      return this.packet;
   }

   public void setPacket(Packet packet) {
      this.packet = packet;
   }

   public <T extends Packet> T chatPacket() {
      return this.packet;
   }
}
