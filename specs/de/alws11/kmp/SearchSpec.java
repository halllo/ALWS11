package de.alws11.kmp;

import de.alws11.IDataProvider;
import de.alws11.data.PrefixData;
import de.alws11.data.StringData;
import junit.framework.Assert;
import org.junit.Test;

import java.util.List;

public class SearchSpec {
    private List<Long> FindAll(String rawText, String rawPattern) {
        return Find(rawText, rawPattern, true);
    }

    private List<Long> Find(String rawText, String rawPattern, boolean all) {
        IDataProvider text = new StringData(rawText);
        IDataProvider pattern = new StringData(rawPattern);
        KmpSearcher search = new KmpSearcher(new PrefixData());
        search.FindAllMatches = all;
        return search.Search(text, pattern);
    }

    @Test
    public void wikipedia_sample1() throws Exception {
        List<Long> findings = FindAll("abababcbababcababcab", "ababcabab");
        Assert.assertTrue(findings.contains((long) 8));
    }

    @Test
    public void noOccurrence_noMatch() throws Exception {
        List<Long> findings = FindAll("abababcbababcababcab", "ababdabab");
        Assert.assertTrue(findings.size() == 0);
    }

    @Test
    public void twoOccurrences_twoMatches() throws Exception {
        List<Long> findings = FindAll("abb", "b");
        Assert.assertTrue(findings.contains((long) 1) && findings.contains((long) 2));
    }

    @Test
    public void twoInterlockedOccurrences_twoMatches() throws Exception {
        List<Long> findings = FindAll("abbb", "bb");
        Assert.assertTrue(findings.contains((long) 1) && findings.contains((long) 2));
    }

    @Test
    public void twoOccurrences_oneMatchThenStop() throws Exception {
        List<Long> findings = Find("abb", "b", false);
        Assert.assertTrue(findings.contains((long) 1) && !findings.contains((long) 2));
    }

    @Test
    public void patternLongerThanText_noMatch() throws Exception {
        List<Long> findings = FindAll("abb", "abbb");
        Assert.assertTrue(findings.size() == 0);
    }

    @Test
    public void patternAsLongAsTextOneOccurrence_oneMatch() throws Exception {
        List<Long> findings = FindAll("abb", "abb");
        Assert.assertTrue(findings.contains((long) 0));
    }

    @Test
    public void patternAsLongAsTextNoOccurrence_noMatch() throws Exception {
        List<Long> findings = FindAll("abb", "abc");
        Assert.assertTrue(findings.size() == 0);
    }
}
