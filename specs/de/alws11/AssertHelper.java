package de.alws11;

public class AssertHelper {
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

    public static boolean areSame(String array1, char[] array2, boolean strict) {
        boolean seed = true;
        for (int i = 0; i < array1.length(); i++) {
            seed = seed && (array1.charAt(i) == array2[i]);
        }
        if (strict)
            seed = seed && array1.length() == array2.length;
        return seed;
    }

    public static boolean areSame(String expected, IDataProvider data) {
        boolean seed = true;
        for (int i = 0; i < expected.length(); i++) {
            seed = seed && (expected.charAt(i) == data.getPosition(i));
        }
        seed = seed && expected.length() == data.size();
        return seed;
    }
}
