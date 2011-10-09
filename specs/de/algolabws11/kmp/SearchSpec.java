package de.algolabws11.kmp;

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
    private List<Integer> Search(String rawText, String rawPatter) {
        String[] text = Helper.ArrayOfStrings(rawText);
        String[] pattern = Helper.ArrayOfStrings(rawPatter);
        int[] prefixes = PrefixAnalysis.ForPattern(pattern);
        return Search.InText(text, pattern, prefixes);
    }

    @Test
    public void testText() throws Exception {
        List<Integer> findings = Search("abababcbababcababcab", "ababcabab");
        Assert.assertTrue(findings.contains(8));
    }
}
