package fiveware.events.impl;

import fiveware.events.Event;

public class EventText extends Event {
   public String text;

   public EventText(String text) {
      this.text = text;
   }

   public String getText() {
      return this.text;
   }

   public void setText(String text) {
      this.text = text;
   }
}
