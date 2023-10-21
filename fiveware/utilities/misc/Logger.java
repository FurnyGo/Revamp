package fiveware.utilities.misc;

import fiveware.utilities.time.Time;
import java.util.List;

public class Logger {
   private static String name = "WARE";

   public static void log(String information) {
      System.out.println("[" + Time.currentTime(true, ":") + "] [Client thread/" + name + "]: " + information);
   }

   public static void log(int information) {
      System.out.println("[" + Time.currentTime(true, ":") + "] [Client thread/" + name + "]: " + information);
   }

   public static void log(double information) {
      System.out.println("[" + Time.currentTime(true, ":") + "] [Client thread/" + name + "]: " + information);
   }

   public static void log(float information) {
      System.out.println("[" + Time.currentTime(true, ":") + "] [Client thread/" + name + "]: " + information);
   }

   public static void log(long information) {
      System.out.println("[" + Time.currentTime(true, ":") + "] [Client thread/" + name + "]: " + information);
   }

   public static void log(boolean information) {
      System.out.println("[" + Time.currentTime(true, ":") + "] [Client thread/" + name + "]: " + information);
   }

   public static void log(List<String> information) {
      System.out.println("[" + Time.currentTime(true, ":") + "] [Client thread/" + name + "]: " + information);
   }
}
