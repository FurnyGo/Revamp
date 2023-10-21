package fiveware.events.impl;

import fiveware.events.Event;

public class EventKeyPress extends Event {
   private int key;

   public EventKeyPress(int key) {
      this.key = key;
   }

   public int getKey() {
      return this.key;
   }
}
