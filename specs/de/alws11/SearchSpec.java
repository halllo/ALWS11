package de.alws11;

import de.alws11.data.StringData;
import junit.framework.Assert;
import org.junit.Test;

import java.util.List;

public class SearchSpec {
    @Test
    public void wikipediaSample1_singleMatch() throws Exception {
        List<Long> findings = SearchHelper.findAll("abababcbababcababcab", "ababcabab");
        Assert.assertTrue(findings.contains((long) 8));
    }

    @Test
    public void wikipediaSample1_singleMatch_naive() throws Exception {
        ISearch naiveSearch = SearchHelper.getNaiveSearcher(true);
        List<Long> findings = naiveSearch.forPattern(new StringData("ababcabab")).inSource(new StringData("abababcbababcababcab"));
        Assert.assertTrue(findings.contains((long) 8));
    }

    @Test
    public void nullPattern_noMatch() throws Exception {
        ISearch naiveSearch = SearchHelper.getSearchProvider(true);
        List<Long> findings = naiveSearch.forPattern((IDataProvider) null).inSource(new StringData("abababcbababcababcab"));
        Assert.assertEquals(0, findings.size());
    }

    @Test
    public void nullPattern_noMatch_naive() throws Exception {
        ISearch naiveSearch = SearchHelper.getNaiveSearcher(true);
        List<Long> findings = naiveSearch.forPattern((IDataProvider) null).inSource(new StringData("abababcbababcababcab"));
        Assert.assertEquals(0, findings.size());
    }

    @Test
    public void emptyPattern_noMatch_naive() throws Exception {
        ISearch naiveSearch = SearchHelper.getNaiveSearcher(true);
        List<Long> findings = naiveSearch.forPattern(new StringData("")).inSource(new StringData("abababcbababcababcab"));
        Assert.assertEquals(0, findings.size());
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

    @Test
    public void noSearchPattern_noMatches() throws Exception {
        ISearch search = SearchHelper.getSearchProvider(true);
        List<Long> findings = search.inSource(new StringData("abc"));
        Assert.assertTrue(findings.size() == 0);
    }
}
