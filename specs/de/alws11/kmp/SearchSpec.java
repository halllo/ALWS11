package de.alws11.kmp;

import junit.framework.Assert;
import org.junit.Test;

import java.util.List;

public class SearchSpec {
    @Test
    public void wikipedia_sample1() throws Exception {
        List<Long> findings = KmpHelper.findAll("abababcbababcababcab", "ababcabab");
        Assert.assertTrue(findings.contains((long) 8));
    }

    @Test
    public void noOccurrence_noMatch() throws Exception {
        List<Long> findings = KmpHelper.findAll("abababcbababcababcab", "ababdabab");
        Assert.assertTrue(findings.size() == 0);
    }

    @Test
    public void twoOccurrences_twoMatches() throws Exception {
        List<Long> findings = KmpHelper.findAll("abb", "b");
        Assert.assertTrue(findings.contains((long) 1) && findings.contains((long) 2));
    }

    @Test
    public void twoInterlockedOccurrences_twoMatches() throws Exception {
        List<Long> findings = KmpHelper.findAll("abbb", "bb");
        Assert.assertTrue(findings.contains((long) 1) && findings.contains((long) 2));
    }

    @Test
    public void twoOccurrences_oneMatchThenStop() throws Exception {
        List<Long> findings = KmpHelper.find("abb", "b", false);
        Assert.assertTrue(findings.contains((long) 1) && !findings.contains((long) 2));
    }

    @Test
    public void patternLongerThanText_noMatch() throws Exception {
        List<Long> findings = KmpHelper.findAll("abb", "abbb");
        Assert.assertTrue(findings.size() == 0);
    }

    @Test
    public void patternAsLongAsTextOneOccurrence_oneMatch() throws Exception {
        List<Long> findings = KmpHelper.findAll("abb", "abb");
        Assert.assertTrue(findings.contains((long) 0));
    }

    @Test
    public void patternAsLongAsTextNoOccurrence_noMatch() throws Exception {
        List<Long> findings = KmpHelper.findAll("abb", "abc");
        Assert.assertTrue(findings.size() == 0);
    }

    @Test
    public void occurrenceAtBeginning_MatchAtBeginning() throws Exception {
        List<Long> findings = KmpHelper.findAll("baaaaa", "baa");
        Assert.assertTrue(findings.contains((long) 0));
    }

    @Test
    public void occurrenceAtEnd_MatchAtBeginning() throws Exception {
        List<Long> findings = KmpHelper.findAll("aaabaa", "baa");
        Assert.assertTrue(findings.contains((long) 3));
    }

    @Test
    public void occurrenceAtBeginning_MatchAtEnd() throws Exception {
        List<Long> findings = KmpHelper.findAll("aabaaa", "aab");
        Assert.assertTrue(findings.contains((long) 0));
    }

    @Test
    public void occurrenceAtEnd_MatchAtEnd() throws Exception {
        List<Long> findings = KmpHelper.findAll("aaaaab", "aab");
        Assert.assertTrue(findings.contains((long) 3));
    }
}
