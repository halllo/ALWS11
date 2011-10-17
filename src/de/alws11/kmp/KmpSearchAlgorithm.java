package de.alws11.kmp;

import de.alws11.IDataProvider;
import de.alws11.IMatchFound;
import de.alws11.MatchFoundArgs;

class KmpSearchAlgorithm {
    public static void onData(IDataProvider data, IDataProvider pattern, IIndexStore prefixes, IMatchFound found) {
        long i = 0;
        long j = 0;
        while (i < data.size()) {
            while (j >= 0 && !data.getPosition(i).equals(pattern.getPosition(j))) {
                j = prefixes.getIndex(j);
            }
            i = i + 1;
            j = j + 1;
            if (j == pattern.size()) {
                MatchFoundArgs match = notifyMatch(found, i - pattern.size());
                if (!match.shouldContinue) break;
                j = prefixes.getIndex(j);
            }
        }
    }

    private static MatchFoundArgs notifyMatch(IMatchFound found, long position) {
        MatchFoundArgs match = new MatchFoundArgs();
        match.position = position;
        found.newMatch(match);
        return match;
    }
}
