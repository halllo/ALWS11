package de.alws11.Naive;

import de.alws11.IDataProvider;
import de.alws11.IMatchFound;
import de.alws11.MatchFoundArgs;

public class SearchAlgorithm {
    public static void start(IDataProvider data, IDataProvider pattern, IMatchFound found) {
        if (pattern.size() <= 0 || data.size() <= 0 || data.size() < pattern.size())
            return;
        for (long baseIndex = 0; baseIndex < data.size(); baseIndex++) {
            if (matches(data, pattern, baseIndex)) {
                MatchFoundArgs match = notifyMatch(found, baseIndex);
                if (!match.shouldContinue) break;
            }
        }
    }

    private static MatchFoundArgs notifyMatch(IMatchFound found, long position) {
        MatchFoundArgs match = new MatchFoundArgs();
        match.position = position;
        found.newMatch(match);
        return match;
    }

    private static boolean matches(IDataProvider data, IDataProvider pattern, long baseIndex) {
        for (long patternIndex = 0; patternIndex < pattern.size(); patternIndex++) {
            if (baseIndex + patternIndex >= data.size()) return false;
            if (data.getPosition(baseIndex + patternIndex) != (pattern.getPosition(patternIndex))) return false;
            if (patternIndex == pattern.size() - 1) return true;
        }
        return false;
    }
}
