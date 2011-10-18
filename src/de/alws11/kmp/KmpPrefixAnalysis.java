package de.alws11.kmp;

import de.alws11.IDataProvider;

class KmpPrefixAnalysis {
    public static void forPattern(IDataProvider pattern, IIndexStore prefixes) {
        prefixes.requiredSize(pattern.size() + 1);
        long i = 0;
        long j = -1;
        prefixes.setIndex(i, j);
        while (i < pattern.size()) {
            while (j >= 0 && pattern.getPosition(j) != pattern.getPosition(i)) {
                j = prefixes.getIndex(j);
            }
            i = i + 1;
            j = j + 1;
            prefixes.setIndex(i, j);
        }
    }
}
