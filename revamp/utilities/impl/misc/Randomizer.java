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
        final List<String> word = Arrays.asList("strap", "cruel", "blame", "store", "brown", "guess", "rugby", "virus", "petty", "place", "thigh", "panic", "feast", "orbit", "study", "break", "grace", "steam", "braid", "curve", "throw", "great", "theme", "breed", "judge", "elect", "siege", "waste", "alarm", "money", "march", "fleet", "means", "spite", "start", "slime", "sweat", "trend", "water", "exact", "guilt", "chaos", "laser", "black", "tense", "steel", "trunk", "other", "field", "relax");
        return word.get(randomInt(0, word.size()));
    }
}
