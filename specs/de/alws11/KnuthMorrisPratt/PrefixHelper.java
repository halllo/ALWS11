package de.alws11.KnuthMorrisPratt;

import de.alws11.IDataProvider;
import de.alws11.data.IndexData;
import de.alws11.data.StringData;

public class PrefixHelper {
    public static long[] prefixesOf(String pattern) {
        IDataProvider patternData = new StringData(pattern);
        IndexData prefixes = new IndexData();
        de.alws11.KnuthMorrisPratt.PrefixAnalysis.forPattern(patternData, prefixes);
        return prefixes.getRaw();
    }
}
