package de.alws11.data;

import de.alws11.IDataProvider;
import junit.framework.Assert;
import org.junit.Test;

public class FileDataSpec {
    @Test
    public void twoCharsBufferSize2_bothIndexable() throws Exception {
        IDataProvider dataIndexer = DataHelper.getDataProvider("01", 2);
        Assert.assertEquals('0', dataIndexer.getPosition(0));
        Assert.assertEquals('1', dataIndexer.getPosition(1));
    }

    @Test
    public void oneCharsBufferSize2_firstIndexable() throws Exception {
        IDataProvider dataIndexer = DataHelper.getDataProvider("0", 2);
        Assert.assertEquals('0', dataIndexer.getPosition(0));
        Assert.assertEquals('\u0000', dataIndexer.getPosition(1));
    }

    @Test
    public void threeCharsBufferSize2_allThreeIndexable() throws Exception {
        IDataProvider dataIndexer = DataHelper.getDataProvider("012", 2);
        Assert.assertEquals('0', dataIndexer.getPosition(0));
        Assert.assertEquals('1', dataIndexer.getPosition(1));
        Assert.assertEquals('2', dataIndexer.getPosition(2));
    }

    @Test
    public void threeCharsBufferSize1_allThreeIndexable() throws Exception {
        IDataProvider dataIndexer = DataHelper.getDataProvider("012", 1);
        Assert.assertEquals('0', dataIndexer.getPosition(0));
        Assert.assertEquals('1', dataIndexer.getPosition(1));
        Assert.assertEquals('2', dataIndexer.getPosition(2));
    }

    @Test
    public void threeCharsBufferSize1_lastIndexable() throws Exception {
        IDataProvider dataIndexer = DataHelper.getDataProvider("012", 1);
        Assert.assertEquals('2', dataIndexer.getPosition(2));
    }

    @Test
    public void threeCharsBufferSize1_lastFirstSecondIndexable() throws Exception {
        IDataProvider dataIndexer = DataHelper.getDataProvider("012", 1);
        Assert.assertEquals('2', dataIndexer.getPosition(2));
        Assert.assertEquals('0', dataIndexer.getPosition(0));
        Assert.assertEquals('1', dataIndexer.getPosition(1));
    }

    @Test
    public void tenCharsBufferSize3_lastThreeIndexable() throws Exception {
        IDataProvider dataIndexer = DataHelper.getDataProvider("0123456789", 3);
        Assert.assertEquals('7', dataIndexer.getPosition(7));
        Assert.assertEquals('8', dataIndexer.getPosition(8));
        Assert.assertEquals('9', dataIndexer.getPosition(9));
    }

    @Test
    public void tenCharsBufferSize3_oneAfterLastNotIndexable() throws Exception {
        IDataProvider dataIndexer = DataHelper.getDataProvider("0123456789", 3);
        Assert.assertEquals('\u0000', dataIndexer.getPosition(10));
    }

    @Test
    public void tenCharsBufferSize3_manyAfterLastNotIndexable() throws Exception {
        IDataProvider dataIndexer = DataHelper.getDataProvider("0123456789", 3);
        Assert.assertEquals('\u0000', dataIndexer.getPosition((long) Integer.MAX_VALUE + 10));
    }

    @Test
    public void tenCharsBufferSize3_oneBeforeFirstNotIndexable() throws Exception {
        IDataProvider dataIndexer = DataHelper.getDataProvider("0123456789", 3);
        Assert.assertEquals('\u0000', dataIndexer.getPosition(-1));
    }

    @Test
    public void tenCharsBufferSize3_manyBeforeFirstNotIndexable() throws Exception {
        IDataProvider dataIndexer = DataHelper.getDataProvider("0123456789", 3);
        Assert.assertEquals('\u0000', dataIndexer.getPosition((long) Integer.MIN_VALUE - 10));
    }

    @Test
    public void threeCharsBufferSize0_cannotBeCreated() throws Exception {
        try {
            DataHelper.getDataProvider("012", 0);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertTrue(e.getMessage().contains("buffer too small"));
        }
    }

    @Test
    public void noCharsBufferSize1_noneIndexable() throws Exception {
        IDataProvider dataIndexer = DataHelper.getDataProvider("", 1);
        Assert.assertEquals('\u0000', dataIndexer.getPosition(0));
    }
}
