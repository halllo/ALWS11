package de.alws11;

/**
 * Created by IntelliJ IDEA.
 * User: Manuel
 * Date: 09.10.11
 * Time: 17:17
 * To change this template use File | Settings | File Templates.
 */
public class Helper {
    public static long[] ArrayOfLongs(long... longs) {
        return longs;
    }

    public static boolean AreSame(long[] array1, long[] array2) {
        boolean seed = true;
        for (int i = 0; i < array1.length; i++) {
            seed = seed && (array1[i] == array2[i]);
        }
        return seed;
    }
}
