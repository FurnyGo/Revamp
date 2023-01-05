// 
// Decompiled by Procyon v0.5.36
// 

package revamp.utilities.impl.misc;

import java.util.List;
import java.util.Arrays;
import java.util.Random;
import io.netty.util.internal.ThreadLocalRandom;

public class Randomizer
{
    public static int randomInt(final int min, final int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }
    
    public static double randomDouble(final double min, final double max) {
        return ThreadLocalRandom.current().nextDouble(min, max);
    }
    
    public static long randomLong(final long min, final long max) {
        return ThreadLocalRandom.current().nextLong(min, max);
    }
    
    public static char randomABC() {
        return (char)(new Random().nextInt(26) + 97);
    }
    
    public static String randomWord() {
        final List<String> word = Arrays.asList("strap", "cruel", "blame", "store", "brown", "guess", "rugby", "virus", "petty", "place", "thigh", "panic", "feast", "orbit", "study", "break", "grace", "steam", "braid", "curve", "throw", "great", "theme", "breed", "judge", "elect", "siege", "waste", "alarm", "money", "march", "fleet", "means", "spite", "start", "slime", "sweat", "trend", "water", "exact", "guilt", "chaos", "laser", "black", "tense", "steel", "trunk", "other", "field", "relax", "city", "week", "loot", "poll", "girl", "swop", "moon", "game", "mean", "bare", "dawn", "soar", "fair", "mail", "fine", "file", "rain", "flow", "line", "cafe", "lose", "seal", "goat", "coal", "duke", "left", "warm", "lane", "tail", "wire", "worm", "dive", "sink", "lazy", "rack", "wine", "hole", "herb", "thaw", "past", "bang", "suit", "soap", "roll", "hope", "lake", "heat", "cold", "have", "pity", "escape", "random", "future", "bullet", "bridge", "winter", "ignore", "debate", "cancer", "behave", "stress", "heroin", "broken", "decade", "pillow", "crisis", "cattle", "relief", "cheque", "banish", "kidney", "avenue", "appear", "stream", "lonely", "indoor", "damage", "dilute", "shiver", "remain", "poison", "latest", "horror", "direct", "filter", "bronze", "narrow", "create", "honest", "porter", "script", "quaint", "dollar", "empire", "factor", "inject", "master", "regret", "mosaic", "revive", "shot", "chance", "fare", "cash", "list", "left", "chalk", "pound", "straw", "still", "ask", "coat", "twist", "lip", "grief", "age", "smell", "pass", "freight", "ward", "flesh", "slow", "flex", "fine", "doll", "swell", "find", "cup", "cook", "tempt", "rain", "curl", "switch", "grip", "straight", "sleep", "cheque", "tone", "gain", "chair", "leash", "trance", "pack", "thigh", "fax", "bus", "rare", "bride", "rock");
        return word.get(randomInt(0, word.size()));
    }
}
