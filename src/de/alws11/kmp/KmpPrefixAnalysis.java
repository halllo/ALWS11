package de.alws11.kmp;

import de.alws11.IDataProvider;
import de.alws11.IPrefixStore;

class KmpPrefixAnalysis {
    public static void ForPattern(IDataProvider pattern, IPrefixStore prefixes) {
        prefixes.RequestSize(pattern.Size() + 1);
        long i = 0;
        long j = -1;
        prefixes.SetPosition(i, j);
        while (i < pattern.Size()) {
            while (j >= 0 && !pattern.GetPosition(j).equals(pattern.GetPosition(i))) {
                j = prefixes.GetPosition(j);
            }
            i = i + 1;
            j = j + 1;
            prefixes.SetPosition(i, j);
        }
    }
}
