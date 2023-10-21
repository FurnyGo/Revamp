package fiveware.events.impl;

import fiveware.events.CMDEvent;

public class EventChat extends CMDEvent {
   public String message;

   public EventChat(String message) {
      this.message = message;
   }

   public String getMessage() {
      return this.message;
   }

   public void setMessage(String message) {
      this.message = message;
   }
}
