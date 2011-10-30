package de.alws11.KnuthMorrisPratt;

import de.alws11.AsymmetricDataProvider;
import de.alws11.data.IndexData;
import de.alws11.data.StringData;

public class PrefixHelper {
    public static long[] prefixesOf(String pattern) {
        IndexData prefixes = new IndexData();
        AsymmetricDataProvider asymmetricPattern = new AsymmetricDataProvider(
                new StringData(pattern), new StringData(pattern));
        de.alws11.KnuthMorrisPratt.PrefixAnalysis.forPattern(asymmetricPattern, prefixes);
        return prefixes.getRaw();
    }
}
