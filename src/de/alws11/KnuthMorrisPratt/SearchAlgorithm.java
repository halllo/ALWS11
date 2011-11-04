package de.alws11.KnuthMorrisPratt;

import de.alws11.IDataProvider;
import de.alws11.IMatchFound;
import de.alws11.IReadOnlyIndexStore;
import de.alws11.MatchFoundArgs;

/**
 * This class provides the functionality of the Knuth-Morris-Pratt search algorithm. The algorithm works with O(m) with m being the length of the text and n being the length of the search pattern.
 */
public class SearchAlgorithm {
    /**
     * This method searches for a search pattern in a data source.
     *
     * @param data     This parameter is the data search in which a pattern is searched for.
     * @param pattern  This parameter is the search pattern which is used to search for in the data source.
     * @param prefixes This parameter is the result of the next function, which is used to move the pattern along by the maximum number of characters possible.
     * @param found    This parameter provides the callback which gets invoked for every match that was found.
     */
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
