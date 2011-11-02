package de.alws11.Naive;

import de.alws11.*;

import java.util.ArrayList;
import java.util.List;

public class Searcher implements ISearch {
    public boolean findAllMatches;
    private IDataProvider _pattern;

    public Searcher() {
        findAllMatches = true;
    }

    public ISearch forPattern(IDataProvider pattern) {
        if (pattern != null)
            return forPattern(new AsymmetricDataProvider(pattern, pattern));
        else
            return forPattern(null);
    }

    public ISearch forPattern(AsymmetricDataProvider pattern) {
        _pattern = pattern;
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
        SearchAlgorithm.start(source, _pattern, new IMatchFound() {
            public void newMatch(MatchFoundArgs e) {
                findings.add(e.position);
                e.shouldContinue = findAllMatches;
            }
        });
    }
}
