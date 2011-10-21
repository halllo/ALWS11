package de.alws11.KnuthMorrisPratt;

import de.alws11.IDataProvider;

public class PrefixAnalysis {
    public static void forPattern(IDataProvider pattern, IIndexStore prefixes) {
        prefixes.requiredSize(pattern.size() + 1);
        long i = 0;
        long j = -1;
        prefixes.pushIndex(j);
        while (i < pattern.size()) {
            while (j >= 0 && pattern.getPosition(j) != pattern.getPosition(i)) {
                j = prefixes.getIndex(j);
            }
            i = i + 1;
            j = j + 1;
            prefixes.pushIndex(j);
        }
        prefixes.doneCreating();
    }
}
