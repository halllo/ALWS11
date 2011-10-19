package de.alws11;

import de.alws11.data.PrefixData;
import de.alws11.data.StringData;

import java.util.List;

public class SearchHelper {
    public static List<Long> findAll(String rawText, String rawPattern) {
        return find(rawText, rawPattern, true);
    }

    public static List<Long> find(String rawText, String rawPattern, boolean all) {
        IDataProvider text = new StringData(rawText);
        IDataProvider pattern = new StringData(rawPattern);
        return getSearchProvider(all).search(text, pattern);
    }

    private static ISearch getSearchProvider(boolean findAllOccurrences) {
        de.alws11.KnuthMorrisPratt.Searcher searcher = new de.alws11.KnuthMorrisPratt.Searcher(new PrefixData());
        searcher.findAllMatches = findAllOccurrences;
        return searcher;
    }
}
