package de.alws11.kmp;

import de.alws11.Helper;
import de.alws11.IIndexable;
import de.alws11.data.StringData;
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
    private long[] PrefixesFor(String pattern) {
        IIndexable patternData = new StringData(pattern);
        return PrefixAnalysis.ForPattern(patternData);
    }

    @Test
    public void wikipedia_sample_1() throws Exception {
        long[] actualPrefixes = PrefixesFor("ababcabab");
        long[] expeactedPrefixes = Helper.ArrayOfLongs(-1, 0, 0, 1, 2, 0, 1, 2, 3, 4);
        Assert.assertTrue(Helper.AreSame(actualPrefixes, expeactedPrefixes));
    }

    @Test
    public void prefixes_length3_3() throws Exception {
        long[] expeactedPrefixes = Helper.ArrayOfLongs(-1, 0, 0, 0);
        long[] actualPrefixes = PrefixesFor("abc");
        Assert.assertTrue(Helper.AreSame(actualPrefixes, expeactedPrefixes));
    }

    @Test
    public void prefixes_length2_2() throws Exception {
        long[] actualPrefixes = PrefixesFor("ab");
        long[] expeactedPrefixes = Helper.ArrayOfLongs(-1, 0, 0);
        Assert.assertTrue(Helper.AreSame(actualPrefixes, expeactedPrefixes));
    }

    @Test
    public void prefixes_length1_1() throws Exception {
        long[] actualPrefixes = PrefixesFor("a");
        long[] expeactedPrefixes = Helper.ArrayOfLongs(-1, 0);
        Assert.assertTrue(Helper.AreSame(actualPrefixes, expeactedPrefixes));
    }

    @Test
    public void prefixes_length0_0() throws Exception {
        long[] actualPrefixes = PrefixesFor("");
        long[] expeactedPrefixes = Helper.ArrayOfLongs(-1);
        Assert.assertTrue(Helper.AreSame(actualPrefixes, expeactedPrefixes));
    }
}
