package fiveware.events.impl;

import fiveware.events.Event;

public class EventRender3D extends Event {
   private float partialTicks;

   public EventRender3D(float partialTicks) {
      this.partialTicks = partialTicks;
   }

   public float getPartialTicks() {
      return this.partialTicks;
   }
}
