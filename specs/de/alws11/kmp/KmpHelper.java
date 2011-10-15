package de.alws11.kmp;

import de.alws11.IDataProvider;
import de.alws11.data.PrefixData;
import de.alws11.data.StringData;

import java.util.List;

public class KmpHelper {
    public static long[] prefixesFor(String pattern) {
        IDataProvider patternData = new StringData(pattern);
        PrefixData prefixes = new PrefixData();
        KmpPrefixAnalysis.forPattern(patternData, prefixes);
        return prefixes.getRaw();
    }

    public static List<Long> findAll(String rawText, String rawPattern) {
        return find(rawText, rawPattern, true);
    }

    public static List<Long> find(String rawText, String rawPattern, boolean all) {
        IDataProvider text = new StringData(rawText);
        IDataProvider pattern = new StringData(rawPattern);
        KmpSearcher search = new KmpSearcher(new PrefixData());
        search.findAllMatches = all;
        return search.search(text, pattern);
    }
}
