package fiveware.utilities.misc;

import io.netty.util.internal.ThreadLocalRandom;
import java.util.Arrays;
import java.util.List;

public class Randomizer {
   public static int randomInt(int min, int max) {
      return ThreadLocalRandom.current().nextInt(min, max);
   }

   public static double randomDouble(double min, double max) {
      return ThreadLocalRandom.current().nextDouble(min, max);
   }

   public static float randomFloat(float min, float max) {
      return (float)ThreadLocalRandom.current().nextDouble((double)min, (double)max);
   }

   public static long randomLong(long min, long max) {
      return ThreadLocalRandom.current().nextLong(min, max);
   }

   public static String randomLength(int length, boolean onlyABC) {
      String text = "";
      String characters = onlyABC ? "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM" : "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890:.,()[],./=-";

      for(int i = 0; i < length; ++i) {
         text = text + characters.charAt((int)Math.floor(Math.random() * (double)characters.length()));
      }

      return text;
   }

   public static String randomWord() {
      List<String> word = Arrays.asList("strap", "cruel", "blame", "store", "brown", "guess", "rugby", "virus", "petty", "place", "thigh", "panic", "feast", "orbit", "study", "break", "grace", "steam", "braid", "curve", "throw", "great", "theme", "breed", "judge", "elect", "siege", "waste", "alarm", "money", "march", "fleet", "means", "spite", "start", "slime", "sweat", "trend", "water", "exact", "guilt", "chaos", "laser", "black", "tense", "steel", "trunk", "other", "field", "relax", "city", "week", "loot", "poll", "girl", "swop", "moon", "game", "mean", "bare", "dawn", "soar", "fair", "mail", "fine", "file", "rain", "flow", "line", "cafe", "lose", "seal", "goat", "coal", "duke", "left", "warm", "lane", "tail", "wire", "worm", "dive", "sink", "lazy", "rack", "wine", "hole", "herb", "thaw", "past", "bang", "suit", "soap", "roll", "hope", "lake", "heat", "cold", "have", "pity", "escape", "random", "future", "bullet", "bridge", "winter", "ignore", "debate", "cancer", "behave", "stress", "heroin", "broken", "decade", "pillow", "crisis", "cattle", "relief", "cheque", "banish", "kidney", "avenue", "appear", "stream", "lonely", "indoor", "damage", "dilute", "shiver", "remain", "poison", "latest", "horror", "direct", "filter", "bronze", "narrow", "create", "honest", "porter", "script", "quaint", "dollar", "empire", "factor", "inject", "master", "regret", "mosaic", "revive", "shot", "chance", "fare", "cash", "list", "left", "chalk", "pound", "straw", "still", "ask", "coat", "twist", "lip", "grief", "age", "smell", "pass", "freight", "ward", "flesh", "slow", "flex", "fine", "doll", "swell", "find", "cup", "cook", "tempt", "rain", "curl", "switch", "grip", "straight", "sleep", "cheque", "tone", "gain", "chair", "leash", "trance", "pack", "thigh", "fax", "bus", "rare", "bride", "rock", "disgrace", "dawn", "stunning", "wear", "tin", "apathy", "army", "quarrel", "wedding", "glasses", "conviction", "fresh", "leave", "responsible", "bounce", "material", "executrix", "series", "tape", "approach", "welfare", "doctor", "resource", "hate", "sodium", "job", "facade", "gradient", "plaintiff", "pest", "assumption", "offender", "document", "garlic", "storm", "national", "emphasis", "desert", "develop", "patrol", "tread", "fax", "favor", "rank", "slap", "charm", "free", "cooperative", "triangle", "neutral");
      return (String)word.get(randomInt(0, word.size()));
   }
}
