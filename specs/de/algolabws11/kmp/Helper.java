package de.algolabws11.kmp;

/**
 * Created by IntelliJ IDEA.
 * User: Manuel
 * Date: 09.10.11
 * Time: 17:17
 * To change this template use File | Settings | File Templates.
 */
public class Helper {
    public static String[] ArrayOfStrings(String word) {
        String[] arrayOfStrings = new String[word.length()];
        for (int i = 0; i < word.length(); i++) {
            arrayOfStrings[i] = "" + word.charAt(i);
        }
        return arrayOfStrings;
    }

    public static int[] ArrayOfInts(int... ints) {
        return ints;
    }

    public static boolean AreSame(int[] array1, int[] array2) {
        boolean seed = true;
        for (int i = 0; i < array1.length; i++) {
            seed = seed && (array1[i] == array2[i]);
        }
        return seed;
    }
}
