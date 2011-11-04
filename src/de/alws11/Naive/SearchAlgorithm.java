package de.alws11.Naive;

import de.alws11.IDataProvider;
import de.alws11.IMatchFound;
import de.alws11.MatchFoundArgs;

/**
 * This class provides the functionality of a naive search algorithm. The algorithm works with O(m*n) with m being the length of the text and n being the length of the search pattern.
 */
public class SearchAlgorithm {
    /**
     * This method searches for a search pattern in a data source.
     *
     * @param data    This parameter is the data search in which a pattern is searched for.
     * @param pattern This parameter is the search pattern which is used to search for in the data source.
     * @param found   This parameter provides the callback which gets invoked for every match that was found.
     */
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
