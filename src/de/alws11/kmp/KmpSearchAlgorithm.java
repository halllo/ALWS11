package de.alws11.kmp;

import de.alws11.IDataProvider;
import de.alws11.IMatchFound;
import de.alws11.IIndexStore;
import de.alws11.MatchFoundArgs;

class KmpSearchAlgorithm {
    public static void OnData(IDataProvider data, IDataProvider pattern, IIndexStore prefixes, IMatchFound found) {
        long i = 0;
        long j = 0;
        while (i < data.Size()) {
            while (j >= 0 && !data.GetPosition(i).equals(pattern.GetPosition(j))) {
                j = prefixes.GetIndex(j);
            }
            i = i + 1;
            j = j + 1;
            if (j == pattern.Size()) {
                MatchFoundArgs match = NotifyMatch(found, i - pattern.Size());
                if (!match.Continue) break;
                j = prefixes.GetIndex(j);
            }
        }
    }

    private static MatchFoundArgs NotifyMatch(IMatchFound found, long position) {
        MatchFoundArgs match = new MatchFoundArgs();
        match.Position = position;
        found.NewMatch(match);
        return match;
    }
}
