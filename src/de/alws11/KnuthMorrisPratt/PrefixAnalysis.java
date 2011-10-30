package de.alws11.KnuthMorrisPratt;

import de.alws11.AsymmetricDataProvider;
import de.alws11.IIndexStore;

public class PrefixAnalysis {
    public static void forPattern(AsymmetricDataProvider pattern, IIndexStore prefixes) {
        prefixes.setRequiredSize(pattern.size() + 1);
        long i = 0;
        long j = -1;
        prefixes.pushIndex(j);
        while (i < pattern.size()) {
            while (j >= 0 && pattern.getPosition(i) != pattern.getAsymmetricPosition(j)) {
                j = prefixes.getIndex(j);
            }
            i = i + 1;
            j = j + 1;
            prefixes.pushIndex(j);
        }
        prefixes.doneCreating();
    }
}
