package de.alws11.kmp;

import de.alws11.*;
import de.alws11.data.PrefixData;

import java.util.ArrayList;
import java.util.List;

public class KmpSearcher implements ISearch {
    public boolean FindAllMatches;
    private IPrefixStore _prefixes;

    public KmpSearcher(IPrefixStore prefixes) {
        FindAllMatches = true;
        _prefixes = prefixes;
    }

    public List<Long> Search(IDataProvider source, IDataProvider pattern) {
        final List<Long> findings = new ArrayList<Long>();
        KmpPrefixAnalysis.ForPattern(pattern, _prefixes);
        KmpSearchAlgorithm.OnData(source, pattern, _prefixes, new IMatchFound() {
            public void NewMatch(MatchFoundArgs e) {
                findings.add(e.Position);
                e.Continue = FindAllMatches;
            }
        });
        return findings;
    }
}
