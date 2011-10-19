package de.alws11.KnuthMorrisPratt;

import de.alws11.IDataProvider;
import de.alws11.IMatchFound;
import de.alws11.ISearch;
import de.alws11.MatchFoundArgs;

import java.util.ArrayList;
import java.util.List;

public class Searcher implements ISearch {
    public boolean findAllMatches;
    private IIndexStore _prefixes;

    public Searcher(IIndexStore prefixes) {
        findAllMatches = true;
        _prefixes = prefixes;
    }

    public List<Long> search(IDataProvider source, IDataProvider pattern) {
        final List<Long> findings = new ArrayList<Long>();
        PrefixAnalysis.forPattern(pattern, _prefixes);
        SearchAlgorithm.onData(source, pattern, _prefixes, new IMatchFound() {
            public void newMatch(MatchFoundArgs e) {
                findings.add(e.position);
                e.shouldContinue = findAllMatches;
            }
        });
        return findings;
    }
}
