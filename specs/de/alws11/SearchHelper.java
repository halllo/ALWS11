package de.alws11;

import de.alws11.data.IndexData;
import de.alws11.data.StringData;

import java.util.List;

public class SearchHelper {
    public static List<Long> findAll(String rawText, String rawPattern) {
        return find(rawText, rawPattern, true);
    }

    public static List<Long> find(String rawText, String rawPattern, boolean all) {
        IDataProvider text = new StringData(rawText);
        IDataProvider pattern = new StringData(rawPattern);
        ISearch search = getSearchProvider(all);
        return search.forPattern(pattern).inSource(text);
    }

    public static ISearch getSearchProvider(boolean findAllOccurrences) {
        IIndexStore indices = new IndexData();
        return getKnuthMorrisPrattSearcher(indices, findAllOccurrences);
    }

    public static ISearch getKnuthMorrisPrattSearcher(IIndexStore indices, boolean findAllOccurrences) {
        de.alws11.KnuthMorrisPratt.Searcher searcher = new de.alws11.KnuthMorrisPratt.Searcher(indices);
        searcher.findAllMatches = findAllOccurrences;
        return searcher;
    }
}
