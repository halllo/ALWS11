package de.alws11.data;

import de.alws11.IDataProvider;
import junit.framework.Assert;
import org.junit.Test;

public class FileDataSpec {
    private IDataProvider getDataProvider(String content, int bufferSize) throws Exception {
        return new FileData(de.alws11.fileio.FileHelper.getLineProvider(content), bufferSize);
    }

    @Test
    public void twoCharsBufferSize2_bothIndexable() throws Exception {
        IDataProvider dataIndexer = getDataProvider("01", 2);
        Assert.assertEquals(dataIndexer.getPosition(0), '0');
        Assert.assertEquals(dataIndexer.getPosition(1), '1');
    }

    @Test
    public void oneCharsBufferSize2_firstIndexable() throws Exception {
        IDataProvider dataIndexer = getDataProvider("0", 2);
        Assert.assertEquals(dataIndexer.getPosition(0), '0');
        Assert.assertEquals(dataIndexer.getPosition(1), '\u0000');
    }

    @Test
    public void threeCharsBufferSize2_allThreeIndexable() throws Exception {
        IDataProvider dataIndexer = getDataProvider("012", 2);
        Assert.assertEquals(dataIndexer.getPosition(0), '0');
        Assert.assertEquals(dataIndexer.getPosition(1), '1');
        Assert.assertEquals(dataIndexer.getPosition(2), '2');
    }

    @Test
    public void threeCharsBufferSize1_allThreeIndexable() throws Exception {
        IDataProvider dataIndexer = getDataProvider("012", 1);
        Assert.assertEquals(dataIndexer.getPosition(0), '0');
        Assert.assertEquals(dataIndexer.getPosition(1), '1');
        Assert.assertEquals(dataIndexer.getPosition(2), '2');
    }

    @Test
    public void threeCharsBufferSize1_lastIndexable() throws Exception {
        IDataProvider dataIndexer = getDataProvider("012", 1);
        Assert.assertEquals(dataIndexer.getPosition(2), '2');
    }

    @Test
    public void threeCharsBufferSize1_lastFirstSecondIndexable() throws Exception {
        IDataProvider dataIndexer = getDataProvider("012", 1);
        Assert.assertEquals(dataIndexer.getPosition(2), '2');
        Assert.assertEquals(dataIndexer.getPosition(0), '0');
        Assert.assertEquals(dataIndexer.getPosition(1), '1');
    }

    @Test
    public void tenCharsBufferSize3_lastThreeIndexable() throws Exception {
        IDataProvider dataIndexer = getDataProvider("0123456789", 3);
        Assert.assertEquals(dataIndexer.getPosition(7), '7');
        Assert.assertEquals(dataIndexer.getPosition(8), '8');
        Assert.assertEquals(dataIndexer.getPosition(9), '9');
    }

    @Test
    public void tenCharsBufferSize3_oneAfterLastNotIndexable() throws Exception {
        IDataProvider dataIndexer = getDataProvider("0123456789", 3);
        Assert.assertEquals(dataIndexer.getPosition(10), '\u0000');
    }

    @Test
    public void tenCharsBufferSize3_manyAfterLastNotIndexable() throws Exception {
        IDataProvider dataIndexer = getDataProvider("0123456789", 3);
        Assert.assertEquals(dataIndexer.getPosition((long) Integer.MAX_VALUE + 10), '\u0000');
    }

    @Test
    public void tenCharsBufferSize3_oneBeforeFirstNotIndexable() throws Exception {
        IDataProvider dataIndexer = getDataProvider("0123456789", 3);
        Assert.assertEquals(dataIndexer.getPosition(-1), '\u0000');
    }

    @Test
    public void tenCharsBufferSize3_manyBeforeFirstNotIndexable() throws Exception {
        IDataProvider dataIndexer = getDataProvider("0123456789", 3);
        Assert.assertEquals(dataIndexer.getPosition((long) Integer.MIN_VALUE - 10), '\u0000');
    }

    @Test
    public void threeCharsBufferSize0_cannotBeCreated() throws Exception {
        try {
            getDataProvider("012", 0);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertTrue(e.getMessage().contains("buffer too small"));
        }
    }

    @Test
    public void noCharsBufferSize1_noneIndexable() throws Exception {
        IDataProvider dataIndexer = getDataProvider("", 1);
        Assert.assertEquals(dataIndexer.getPosition(0), '\u0000');
    }
}
