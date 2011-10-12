package de.alws11.kmp;

import de.alws11.IDataProvider;
import de.alws11.IMatchFound;
import de.alws11.IPrefixStore;

class KmpSearch {
    public static void InText(IDataProvider text, IDataProvider pattern, IPrefixStore prefixes, IMatchFound found) {
        long i = 0;
        long j = 0;
        while (i < text.Size()) {
            while (j >= 0 && !text.GetPosition(i).equals(pattern.GetPosition(j))) {
                j = prefixes.GetPosition(j);
            }
            i = i + 1;
            j = j + 1;
            if (j == pattern.Size()) {
                found.NewMatch(i - pattern.Size());
                j = prefixes.GetPosition(j);
            }
        }
    }
}
