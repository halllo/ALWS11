package de.alws11;

public class Helper {
    public static long[] arrayOfLongs(long... longs) {
        return longs;
    }

    public static boolean areSame(long[] array1, long[] array2) {
        boolean seed = true;
        for (int i = 0; i < array1.length; i++) {
            seed = seed && (array1[i] == array2[i]);
        }
        return seed;
    }
}
