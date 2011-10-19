package de.alws11.KnuthMorrisPratt;

import de.alws11.IDataProvider;
import de.alws11.data.PrefixData;
import de.alws11.data.StringData;

public class PrefixHelper {
    public static long[] prefixesOf(String pattern) {
        IDataProvider patternData = new StringData(pattern);
        PrefixData prefixes = new PrefixData();
        de.alws11.KnuthMorrisPratt.PrefixAnalysis.forPattern(patternData, prefixes);
        return prefixes.getRaw();
    }
}
