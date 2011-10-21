package de.alws11.KnuthMorrisPratt;

import de.alws11.IDataProvider;
import de.alws11.IMatchFound;
import de.alws11.MatchFoundArgs;

public class SearchAlgorithm {
    public static void start(IDataProvider data, IDataProvider pattern, IReadOnlyIndexStore prefixes, IMatchFound found) {
        if (pattern.size() > 0) {
            long i = 0;
            long j = 0;
            while (i < data.size()) {
                while (j >= 0 && data.getPosition(i) != pattern.getPosition(j)) {
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
    }

    private static MatchFoundArgs notifyMatch(IMatchFound found, long position) {
        MatchFoundArgs match = new MatchFoundArgs();
        match.position = position;
        found.newMatch(match);
        return match;
    }
}
