package de.alws11.KnuthMorrisPratt;

import de.alws11.*;

import java.util.ArrayList;
import java.util.List;

/**
 * This class provides search functionality by harnessing the Knuth-Morris-Pratt algorithm, which requires an analyses of the search pattern in order to improve search performance. The algorithm works with O(m+n) with m being the length of the text and n being the length of the search pattern.
 */
public class Searcher implements ISearch {
    /**
     * This field indicates whether the search should stop after the first match was found. TRUE finds all matches, FALSE stops after first match. TRUE is default.
     */
    public boolean findAllMatches;
    private IIndexStore _indices;
    private IDataProvider _pattern;

    /**
     * This constructor initializes the search.
     *
     * @param indices This parameter is used as the storage data structure for indices.
     */
    public Searcher(IIndexStore indices) {
        findAllMatches = true;
        _indices = indices;
    }

    /**
     * This method registers a search pattern with the algorithm. The pattern is used as single data provider for asymmetric lookup. The pattern is also used to calculate the next function of the Knuth-Morris-Pratt algorithm.
     *
     * @param pattern This pattern is used to search for occurrences.
     * @return The same instance is returned.
     */
    public ISearch forPattern(IDataProvider pattern) {
        if (pattern != null)
            return forPattern(new AsymmetricDataProvider(pattern));
        else
            return forPattern(null);
    }

    /**
     * This method registers a search pattern with the algorithm. The pattern is used for asymmetric lookup. The pattern is also used to calculate the next function of the Knuth-Morris-Pratt algorithm.
     *
     * @param pattern This pattern is used to search for occurrences.
     * @return The same instance is returned.
     */
    public ISearch forPattern(AsymmetricDataProvider pattern) {
        _pattern = pattern;
        if (_pattern != null) PrefixAnalysis.forPattern(pattern, _indices);
        return this;
    }

    /**
     * This method starts the search algorithm.
     *
     * @param source This parameter is the source in which the pattern is searched.
     * @return The method returns a list of all start positions of found matches. If the pattern has not been set, the method returns an empty list immediately.
     */
    public List<Long> inSource(IDataProvider source) {
        if (_pattern == null)
            return new ArrayList<Long>();
        else {
            return searchInSource(source);
        }
    }

    private List<Long> searchInSource(IDataProvider source) {
        List<Long> findings = new ArrayList<Long>();
        startSearchAlgorithm(source, findings);
        return findings;
    }

    private void startSearchAlgorithm(IDataProvider source, final List<Long> findings) {
        SearchAlgorithm.start(source, _pattern, _indices.asReadOnly(), new IMatchFound() {
            public void newMatch(MatchFoundArgs e) {
                findings.add(e.position);
                e.shouldContinue = findAllMatches;
            }
        });
    }
}
