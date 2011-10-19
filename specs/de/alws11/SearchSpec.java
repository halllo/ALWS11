package de.alws11;

import junit.framework.Assert;
import org.junit.Test;

import java.util.List;

public class SearchSpec {
    @Test
    public void wikipedia_sample1() throws Exception {
        List<Long> findings = SearchHelper.findAll("abababcbababcababcab", "ababcabab");
        Assert.assertTrue(findings.contains((long) 8));
    }

    @Test
    public void noOccurrence_noMatch() throws Exception {
        List<Long> findings = SearchHelper.findAll("abababcbababcababcab", "ababdabab");
        Assert.assertTrue(findings.size() == 0);
    }

    @Test
    public void twoOccurrences_twoMatches() throws Exception {
        List<Long> findings = SearchHelper.findAll("abb", "b");
        Assert.assertTrue(findings.contains((long) 1) && findings.contains((long) 2));
    }

    @Test
    public void twoInterlockedOccurrences_twoMatches() throws Exception {
        List<Long> findings = SearchHelper.findAll("abbb", "bb");
        Assert.assertTrue(findings.contains((long) 1) && findings.contains((long) 2));
    }

    @Test
    public void twoOccurrences_oneMatchThenStop() throws Exception {
        List<Long> findings = SearchHelper.find("abb", "b", false);
        Assert.assertTrue(findings.contains((long) 1) && !findings.contains((long) 2));
    }

    @Test
    public void patternLongerThanText_noMatch() throws Exception {
        List<Long> findings = SearchHelper.findAll("abb", "abbb");
        Assert.assertTrue(findings.size() == 0);
    }

    @Test
    public void patternAsLongAsTextOneOccurrence_oneMatch() throws Exception {
        List<Long> findings = SearchHelper.findAll("abb", "abb");
        Assert.assertTrue(findings.contains((long) 0));
    }

    @Test
    public void patternAsLongAsTextNoOccurrence_noMatch() throws Exception {
        List<Long> findings = SearchHelper.findAll("abb", "abc");
        Assert.assertTrue(findings.size() == 0);
    }

    @Test
    public void occurrenceAtBeginning_MatchAtBeginning() throws Exception {
        List<Long> findings = SearchHelper.findAll("baaaaa", "baa");
        Assert.assertTrue(findings.contains((long) 0));
    }

    @Test
    public void occurrenceAtEnd_MatchAtBeginning() throws Exception {
        List<Long> findings = SearchHelper.findAll("aaabaa", "baa");
        Assert.assertTrue(findings.contains((long) 3));
    }

    @Test
    public void occurrenceAtBeginning_MatchAtEnd() throws Exception {
        List<Long> findings = SearchHelper.findAll("aabaaa", "aab");
        Assert.assertTrue(findings.contains((long) 0));
    }

    @Test
    public void occurrenceAtEnd_MatchAtEnd() throws Exception {
        List<Long> findings = SearchHelper.findAll("aaaaab", "aab");
        Assert.assertTrue(findings.contains((long) 3));
    }

    @Test
    public void emptySearchData_noMatch() throws Exception {
        List<Long> findings = SearchHelper.findAll("", "ab");
        Assert.assertTrue(findings.size() == 0);
    }

    @Test
    public void emptySearchPattern_noMatch() throws Exception {
        List<Long> findings = SearchHelper.findAll("ab", "");
        Assert.assertTrue(findings.size() == 0);
    }

    @Test
    public void emptySearchDataEmptySearchPattern_noMatch() throws Exception {
        List<Long> findings = SearchHelper.findAll("", "");
        Assert.assertTrue(findings.size() == 0);
    }
}
