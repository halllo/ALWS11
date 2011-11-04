package de.alws11.KnuthMorrisPratt;

import de.alws11.AsymmetricDataProvider;
import de.alws11.IIndexStore;

/**
 * This class provides the functionality to calculate the next function of the Knuth-Morris-Pratt algorithm. The function works with O(n) with n being the length of the search pattern.
 */
public class PrefixAnalysis {
    /**
     * This method calculates the indices of prefixes of the search pattern that happen to be index based postfixes as well.
     *
     * @param pattern  This parameter is the search pattern that is used to calculate indices from.
     * @param prefixes This parameter is the index store, in which the indices of the prefixes are stored.
     */
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
