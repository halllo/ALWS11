package de.alws11.kmp;

import de.alws11.IIndexable;

/**
 * Created by IntelliJ IDEA.
 * User: Manuel
 * Date: 09.10.11
 * Time: 16:44
 * To change this template use File | Settings | File Templates.
 */
public class PrefixAnalysis {
    public static long[] ForPattern(IIndexable pattern) {
        long[] prefixes = new long[(int)(pattern.Size() + 1)];
        long i = 0;
        long j = -1;
        prefixes[(int)i] = j;
        while (i < pattern.Size()) {
            while (j >= 0 && !pattern.AtIndex(j).equals(pattern.AtIndex(i))) {
                j = prefixes[(int)j];
            }
            i = i + 1;
            j = j + 1;
            prefixes[(int)i] = j;
        }
        return prefixes;
    }
}
