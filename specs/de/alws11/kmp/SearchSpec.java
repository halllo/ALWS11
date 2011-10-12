package de.alws11.kmp;

import de.alws11.IDataProvider;
import de.alws11.IPrefixStore;
import de.alws11.data.PrefixData;
import de.alws11.data.StringData;
import junit.framework.Assert;
import org.junit.Test;

import java.util.List;

public class SearchSpec {
    private List<Long> SearchAll(String rawText, String rawPattern) {
        return Search(rawText, rawPattern, true);
    }

    private List<Long> Search(String rawText, String rawPattern, boolean all) {
        IDataProvider text = new StringData(rawText);
        IDataProvider pattern = new StringData(rawPattern);
        KmpSearcher search = new KmpSearcher(new PrefixData());
        search.FindAllMatches = all;
        return search.Search(text, pattern);
    }

    @Test
    public void wikipedia_sample_1() throws Exception {
        List<Long> findings = SearchAll("abababcbababcababcab", "ababcabab");
        Assert.assertTrue(findings.contains((long) 8));
    }

    @Test
    public void search_notThere_Empty() throws Exception {
        List<Long> findings = SearchAll("abababcbababcababcab", "ababdabab");
        Assert.assertTrue(findings.size() == 0);
    }

    @Test
    public void search_thereTwice_bothFound() throws Exception {
        List<Long> findings = SearchAll("abb", "b");
        Assert.assertTrue(findings.contains((long) 1) && findings.contains((long) 2));
    }

    @Test
    public void search_thereTwiceInterlocked_bothFound() throws Exception {
        List<Long> findings = SearchAll("abbb", "bb");
        Assert.assertTrue(findings.contains((long) 1) && findings.contains((long) 2));
    }

    @Test
    public void search_thereTwice_onlyFirstFound() throws Exception {
        List<Long> findings = Search("abb", "b", false);
        Assert.assertTrue(findings.contains((long) 1) && !findings.contains((long) 2));
    }

    @Test
    public void search_patternLonger_notFound() throws Exception {
        List<Long> findings = SearchAll("abb", "abbb");
        Assert.assertTrue(findings.size() == 0);
    }

    @Test
    public void search_patternSameLength_found() throws Exception {
        List<Long> findings = SearchAll("abb", "abb");
        Assert.assertTrue(findings.contains((long) 0));
    }

    @Test
    public void search_patternSameLength_notFound() throws Exception {
        List<Long> findings = SearchAll("abb", "abc");
        Assert.assertTrue(findings.size() == 0);
    }
}
