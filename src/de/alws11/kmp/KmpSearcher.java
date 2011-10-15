package de.alws11.kmp;

import de.alws11.*;

import java.util.ArrayList;
import java.util.List;

public class KmpSearcher implements ISearch {
    public boolean findAllMatches;
    private IIndexStore _prefixes;

    public KmpSearcher(IIndexStore prefixes) {
        findAllMatches = true;
        _prefixes = prefixes;
    }

    public List<Long> search(IDataProvider source, IDataProvider pattern) {
        final List<Long> findings = new ArrayList<Long>();
        KmpPrefixAnalysis.forPattern(pattern, _prefixes);
        KmpSearchAlgorithm.onData(source, pattern, _prefixes, new IMatchFound() {
            public void newMatch(MatchFoundArgs e) {
                findings.add(e.position);
                e.shouldContinue = findAllMatches;
            }
        });
        return findings;
    }
}
