package de.algolabws11.kmp;

/**
 * Created by IntelliJ IDEA.
 * User: Manuel
 * Date: 09.10.11
 * Time: 16:44
 * To change this template use File | Settings | File Templates.
 */
public class PrefixAnalysis {
    public static int[] ForPattern(String[] pattern) {
        int[] prefixes = new int[pattern.length + 1];
        int i = 0;
        int j = -1;
        prefixes[i] = j;
        while (i < pattern.length) {
            while (j >= 0 && !pattern[j].equals(pattern[i])) {
                j = prefixes[j];
            }
            i = i + 1;
            j = j + 1;
            prefixes[i] = j;
        }
        return prefixes;
    }
}
