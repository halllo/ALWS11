package de.alws11.kmp;

import de.alws11.IIndexable;

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
    public static List<Long> InText(IIndexable text, IIndexable pattern, long[] prefixes) {
        List<Long> findings = new ArrayList<Long>();
        long i = 0;
        long j = 0;
        while (i < text.Size()) {
            while (j >= 0 && !text.AtIndex(i).equals(pattern.AtIndex(j))) {
                j = prefixes[(int)j];
            }
            i = i + 1;
            j = j + 1;
            if (j == pattern.Size()) {
                findings.add(i - pattern.Size());
                j = prefixes[(int)j];
            }
        }
        return findings;
    }
}
