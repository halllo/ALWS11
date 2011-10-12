package de.alws11.kmp;

import de.alws11.IDataProvider;
import de.alws11.IIndexStore;

class KmpPrefixAnalysis {
    public static void ForPattern(IDataProvider pattern, IIndexStore prefixes) {
        prefixes.RequiredSize(pattern.Size() + 1);
        long i = 0;
        long j = -1;
        prefixes.SetIndex(i, j);
        while (i < pattern.Size()) {
            while (j >= 0 && !pattern.GetPosition(j).equals(pattern.GetPosition(i))) {
                j = prefixes.GetIndex(j);
            }
            i = i + 1;
            j = j + 1;
            prefixes.SetIndex(i, j);
        }
    }
}
