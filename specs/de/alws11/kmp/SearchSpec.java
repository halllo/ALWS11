package de.alws11.kmp;

import de.alws11.Helper;
import de.alws11.IIndexable;
import de.alws11.data.StringData;
import junit.framework.Assert;
import org.junit.Test;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Manuel
 * Date: 09.10.11
 * Time: 17:51
 * To change this template use File | Settings | File Templates.
 */
public class SearchSpec {
    private List<Long> Search(String rawText, String rawPatter) {
        IIndexable text = new StringData(rawText);
        IIndexable pattern = new StringData(rawPatter);
        long[] prefixes = PrefixAnalysis.ForPattern(pattern);
        return Search.InText(text, pattern, prefixes);
    }

    @Test
    public void wikipedia_sample_1() throws Exception {
        List<Long> findings = Search("abababcbababcababcab", "ababcabab");
        Assert.assertTrue(findings.contains((long)8));
    }

    @Test
    public void search_notThere_Empty() throws Exception {
        List<Long> findings = Search("abababcbababcababcab", "ababdabab");
        Assert.assertTrue(findings.size() == 0);
    }

    @Test
    public void search_thereTwice_bothFound() throws Exception {
        List<Long> findings = Search("abb", "b");
        Assert.assertTrue(findings.contains((long) 1) && findings.contains((long)2));
    }
}
