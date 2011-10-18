package de.alws11.data;

import de.alws11.AssertHelper;
import junit.framework.Assert;
import org.junit.Test;

public class DynamicDataSpec {
    @Test
    public void emptyData_size0() throws Exception {
        DynamicData dd = DynamicData.startWith(1, "");
        Assert.assertEquals(0, dd.size());
    }

    @Test
    public void emptyDataChainedWithEmptyData_emptyData() throws Exception {
        DynamicData dd = DynamicData.startWith(1, "").then(1, "");
        Assert.assertTrue(AssertHelper.areSame("", dd));
    }

    @Test
    public void emptyDataChainedWithOneChar_oneChar() throws Exception {
        DynamicData dd = DynamicData.startWith(1, "").then(1, "a");
        Assert.assertTrue(AssertHelper.areSame("a", dd));
    }

    @Test
    public void oneCharChainedWithEmptyDataChainedWithOneChar_twoChars() throws Exception {
        DynamicData dd = DynamicData.startWith(1, "a").then(1, "").then(1, "c");
        Assert.assertTrue(AssertHelper.areSame("ac", dd));
    }

    @Test
    public void oneCharNever_size0() throws Exception {
        DynamicData dd = DynamicData.startWith(0, "a");
        Assert.assertEquals(0, dd.size());
    }

    @Test
    public void oneCharChainedWithOneCharNever_oneChar() throws Exception {
        DynamicData dd = DynamicData.startWith(1, "a").then(0, "b");
        Assert.assertTrue(AssertHelper.areSame("a", dd));
    }

    @Test
    public void oneCharTwice_size2() throws Exception {
        DynamicData dd = DynamicData.startWith(2, "a");
        Assert.assertEquals(2, dd.size());
    }

    @Test
    public void oneCharThenOther_size2() throws Exception {
        DynamicData dd = DynamicData.startWith(1, "a").then(1, "b");
        Assert.assertEquals(2, dd.size());
    }

    @Test
    public void oneCharThenOther_accessibleByIndex() throws Exception {
        DynamicData dd = DynamicData.startWith(1, "a").then(1, "b");
        Assert.assertTrue(AssertHelper.areSame("ab", dd));
    }

    @Test
    public void oneCharTwiceThenOther_accessibleByIndex() throws Exception {
        DynamicData dd = DynamicData.startWith(2, "a").then(1, "b");
        Assert.assertTrue(AssertHelper.areSame("aab", dd));
    }

    @Test
    public void threeCharsThreeTimesThenOther_accessibleByIndex() throws Exception {
        DynamicData dd = DynamicData.startWith(3, "abc").then(1, "d");
        Assert.assertTrue(AssertHelper.areSame("abcabcabcd", dd));
    }

    @Test
    public void threePartsChained_accessibleByIndex() throws Exception {
        DynamicData dd = DynamicData.startWith(3, "a").then(1, "b").then(2, "c");
        Assert.assertTrue(AssertHelper.areSame("aaabcc", dd));
    }

    @Test
    public void largePartChainedWithTwoChars_accessibleByIndex() throws Exception {
        DynamicData dd = DynamicData.startWith((long) Integer.MAX_VALUE + 1, "a").then(1, "bc");
        Assert.assertEquals(dd.getPosition((long) Integer.MAX_VALUE), 'a');
        Assert.assertEquals(dd.getPosition((long) Integer.MAX_VALUE + 1), 'b');
        Assert.assertEquals(dd.getPosition((long) Integer.MAX_VALUE + 2), 'c');
    }
}
