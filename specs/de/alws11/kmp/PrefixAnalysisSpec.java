package de.alws11.kmp;

import de.alws11.Helper;
import junit.framework.Assert;
import org.junit.Test;

public class PrefixAnalysisSpec {
    @Test
    public void wikipedia_sample1() throws Exception {
        long[] actualPrefixes = KmpHelper.prefixesFor("ababcabab");
        long[] expectedPrefixes = Helper.arrayOfLongs(-1, 0, 0, 1, 2, 0, 1, 2, 3, 4);
        Assert.assertTrue(Helper.areSame(actualPrefixes, expectedPrefixes));
    }

    @Test
    public void length6_onePrefix() throws Exception {
        long[] actualPrefixes = KmpHelper.prefixesFor("abcacb");
        long[] expectedPrefixes = Helper.arrayOfLongs(-1, 0, 0, 0, 1, 0, 0);
        Assert.assertTrue(Helper.areSame(actualPrefixes, expectedPrefixes));
    }

    @Test
    public void length6_twoPrefixes() throws Exception {
        long[] actualPrefixes = KmpHelper.prefixesFor("abccab");
        long[] expectedPrefixes = Helper.arrayOfLongs(-1, 0, 0, 0, 0, 1, 2);
        Assert.assertTrue(Helper.areSame(actualPrefixes, expectedPrefixes));
    }

    @Test
    public void length6_threePrefixes() throws Exception {
        long[] actualPrefixes = KmpHelper.prefixesFor("abcabc");
        long[] expectedPrefixes = Helper.arrayOfLongs(-1, 0, 0, 0, 1, 2, 3);
        Assert.assertTrue(Helper.areSame(actualPrefixes, expectedPrefixes));
    }

    @Test
    public void length5_twoPrefixes() throws Exception {
        long[] actualPrefixes = KmpHelper.prefixesFor("abcab");
        long[] expectedPrefixes = Helper.arrayOfLongs(-1, 0, 0, 0, 1, 2);
        Assert.assertTrue(Helper.areSame(actualPrefixes, expectedPrefixes));
    }

    @Test
    public void length4_onePrefix() throws Exception {
        long[] actualPrefixes = KmpHelper.prefixesFor("abca");
        long[] expectedPrefixes = Helper.arrayOfLongs(-1, 0, 0, 0, 1);
        Assert.assertTrue(Helper.areSame(actualPrefixes, expectedPrefixes));
    }

    @Test
    public void length3_noPrefix() throws Exception {
        long[] actualPrefixes = KmpHelper.prefixesFor("abc");
        long[] expectedPrefixes = Helper.arrayOfLongs(-1, 0, 0, 0);
        Assert.assertTrue(Helper.areSame(actualPrefixes, expectedPrefixes));
    }

    @Test
    public void length2_noPrefix() throws Exception {
        long[] actualPrefixes = KmpHelper.prefixesFor("ab");
        long[] expectedPrefixes = Helper.arrayOfLongs(-1, 0, 0);
        Assert.assertTrue(Helper.areSame(actualPrefixes, expectedPrefixes));
    }

    @Test
    public void length1_noPrefix() throws Exception {
        long[] actualPrefixes = KmpHelper.prefixesFor("a");
        long[] expectedPrefixes = Helper.arrayOfLongs(-1, 0);
        Assert.assertTrue(Helper.areSame(actualPrefixes, expectedPrefixes));
    }

    @Test
    public void length0_noPrefix() throws Exception {
        long[] actualPrefixes = KmpHelper.prefixesFor("");
        long[] expectedPrefixes = Helper.arrayOfLongs(-1);
        Assert.assertTrue(Helper.areSame(actualPrefixes, expectedPrefixes));
    }
}
