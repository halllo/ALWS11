package de.alws11.kmp;

import de.alws11.Helper;
import de.alws11.IDataProvider;
import de.alws11.data.PrefixData;
import de.alws11.data.StringData;
import junit.framework.Assert;
import org.junit.Test;

public class PrefixAnalysisSpec {
    private long[] PrefixesFor(String pattern) {
        IDataProvider patternData = new StringData(pattern);
        PrefixData prefixes = new PrefixData();
        KmpPrefixAnalysis.ForPattern(patternData, prefixes);
        return prefixes.GetRaw();
    }

    @Test
    public void wikipedia_sample1() throws Exception {
        long[] actualPrefixes = PrefixesFor("ababcabab");
        long[] expectedPrefixes = Helper.ArrayOfLongs(-1, 0, 0, 1, 2, 0, 1, 2, 3, 4);
        Assert.assertTrue(Helper.AreSame(actualPrefixes, expectedPrefixes));
    }

    @Test
    public void length6_onePrefix() throws Exception {
        long[] actualPrefixes = PrefixesFor("abcacb");
        long[] expectedPrefixes = Helper.ArrayOfLongs(-1, 0, 0, 0, 1, 0, 0);
        Assert.assertTrue(Helper.AreSame(actualPrefixes, expectedPrefixes));
    }

    @Test
    public void length6_twoPrefixes() throws Exception {
        long[] actualPrefixes = PrefixesFor("abccab");
        long[] expectedPrefixes = Helper.ArrayOfLongs(-1, 0, 0, 0, 0, 1, 2);
        Assert.assertTrue(Helper.AreSame(actualPrefixes, expectedPrefixes));
    }

    @Test
    public void length6_threePrefixes() throws Exception {
        long[] actualPrefixes = PrefixesFor("abcabc");
        long[] expectedPrefixes = Helper.ArrayOfLongs(-1, 0, 0, 0, 1, 2, 3);
        Assert.assertTrue(Helper.AreSame(actualPrefixes, expectedPrefixes));
    }

    @Test
    public void length5_twoPrefixes() throws Exception {
        long[] actualPrefixes = PrefixesFor("abcab");
        long[] expectedPrefixes = Helper.ArrayOfLongs(-1, 0, 0, 0, 1, 2);
        Assert.assertTrue(Helper.AreSame(actualPrefixes, expectedPrefixes));
    }

    @Test
    public void length4_onePrefix() throws Exception {
        long[] actualPrefixes = PrefixesFor("abca");
        long[] expectedPrefixes = Helper.ArrayOfLongs(-1, 0, 0, 0, 1);
        Assert.assertTrue(Helper.AreSame(actualPrefixes, expectedPrefixes));
    }

    @Test
    public void length3_noPrefix() throws Exception {
        long[] actualPrefixes = PrefixesFor("abc");
        long[] expectedPrefixes = Helper.ArrayOfLongs(-1, 0, 0, 0);
        Assert.assertTrue(Helper.AreSame(actualPrefixes, expectedPrefixes));
    }

    @Test
    public void length2_noPrefix() throws Exception {
        long[] actualPrefixes = PrefixesFor("ab");
        long[] expectedPrefixes = Helper.ArrayOfLongs(-1, 0, 0);
        Assert.assertTrue(Helper.AreSame(actualPrefixes, expectedPrefixes));
    }

    @Test
    public void length1_noPrefix() throws Exception {
        long[] actualPrefixes = PrefixesFor("a");
        long[] expectedPrefixes = Helper.ArrayOfLongs(-1, 0);
        Assert.assertTrue(Helper.AreSame(actualPrefixes, expectedPrefixes));
    }

    @Test
    public void length0_noPrefix() throws Exception {
        long[] actualPrefixes = PrefixesFor("");
        long[] expectedPrefixes = Helper.ArrayOfLongs(-1);
        Assert.assertTrue(Helper.AreSame(actualPrefixes, expectedPrefixes));
    }
}
