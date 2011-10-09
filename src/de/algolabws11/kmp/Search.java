package de.algolabws11.kmp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Manuel
 * Date: 09.10.11
 * Time: 17:49
 * To change this template use File | Settings | File Templates.
 */
public class Search {
    public static List<Integer> InText(String[] text, String[] pattern, int[] prefixes) {
        List<Integer> findings = new ArrayList<Integer>();
        int i = 0;
        int j = 0;
        while (i < text.length) {
            while (j >= 0 && !text[i].equals(pattern[j])) {
                j = prefixes[j];
            }
            i = i + 1;
            j = j + 1;
            if (j == pattern.length) {
                findings.add(i - pattern.length);
                j = prefixes[j];
            }
        }
        return findings;
    }
}
