package de.algolabws11.kmp;

import junit.framework.Assert;
import org.junit.Test;

/**
 * Created by IntelliJ IDEA.
 * User: Manuel
 * Date: 09.10.11
 * Time: 16:55
 * To change this template use File | Settings | File Templates.
 */
public class PrefixAnalysisSpec {
    private int[] PrefixesFor(String pattern) {
        String[] patternArray = Helper.ArrayOfStrings(pattern);
        return PrefixAnalysis.ForPattern(patternArray);
    }

    @Test
    public void Wikipedia_sample_1() throws Exception {
        int[] actualPrefixes = PrefixesFor("ababcabab");
        int[] expeactedPrefixes = Helper.ArrayOfInts(-1, 0, 0, 1, 2, 0, 1, 2, 3, 4);
        Assert.assertTrue(Helper.AreSame(actualPrefixes, expeactedPrefixes));
    }
}
