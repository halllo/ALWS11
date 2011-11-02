package de.alws11.KnuthMorrisPratt;

import de.alws11.*;

import java.util.ArrayList;
import java.util.List;

public class Searcher implements ISearch {
    public boolean findAllMatches;
    private IIndexStore _indices;
    private IDataProvider _pattern;

    public Searcher(IIndexStore indices) {
        findAllMatches = true;
        _indices = indices;
    }

    public ISearch forPattern(IDataProvider pattern) {
        if (pattern != null)
            return forPattern(new AsymmetricDataProvider(pattern));
        else
            return forPattern(null);
    }

    public ISearch forPattern(AsymmetricDataProvider pattern) {
        _pattern = pattern;
        if (_pattern != null) PrefixAnalysis.forPattern(pattern, _indices);
        return this;
    }

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
