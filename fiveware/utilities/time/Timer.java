package fiveware.utilities.time;

public class Timer {
   private long lastMS;

   public boolean isDelayComplete(long delay) {
      return System.currentTimeMillis() - this.lastMS >= delay;
   }

   public void setLastMS() {
      this.lastMS = System.currentTimeMillis();
   }

   public int convertToMS(int perSecond) {
      return 1000 / perSecond;
   }

   public long getCurrentMS() {
      return System.nanoTime() / 1000000L;
   }

   public long getLastMS() {
      return this.lastMS;
   }

   public boolean hasReached(long milliseconds) {
      return this.getCurrentMS() - this.lastMS >= milliseconds;
   }

   public void reset() {
      this.lastMS = this.getCurrentMS();
   }

   public void setLastMS(long currentMS) {
      this.lastMS = currentMS;
   }

   public long getTimeSinceReset() {
      return this.getCurrentMS() - this.lastMS;
   }
}
