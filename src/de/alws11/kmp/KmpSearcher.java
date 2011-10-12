package de.alws11.kmp;

import de.alws11.IDataProvider;
import de.alws11.IMatchFound;
import de.alws11.ISearch;
import de.alws11.data.PrefixData;

import java.util.ArrayList;
import java.util.List;

public class KmpSearcher implements ISearch {
    public List<Long> Search(IDataProvider source, IDataProvider pattern) {
        final List<Long> findings = new ArrayList<Long>();
        PrefixData prefixes = new PrefixData();
        KmpPrefixAnalysis.ForPattern(pattern, prefixes);
        KmpSearch.InText(source, pattern, prefixes, new IMatchFound() {
            public void NewMatch(long position) {
                findings.add(position);
            }
        });
        return findings;
    }
}
