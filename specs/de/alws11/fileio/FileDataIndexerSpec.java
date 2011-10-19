package de.alws11.fileio;

import de.alws11.IDataProvider;
import junit.framework.Assert;
import org.junit.Test;

public class FileDataIndexerSpec {
    @Test
    public void twoCharsBufferSize2_bothIndexable() throws Exception {
        IFileAccess content = FileHelper.getLineProvider("01");
        IDataProvider fileIndexer = new FileDataIndexer(content, 2);
        Assert.assertEquals(fileIndexer.getPosition(0), '0');
        Assert.assertEquals(fileIndexer.getPosition(1), '1');
    }

    @Test
    public void threeCharsBufferSize2_allThreeIndexable() throws Exception {
        IFileAccess content = FileHelper.getLineProvider("012");
        IDataProvider fileIndexer = new FileDataIndexer(content, 2);
        Assert.assertEquals(fileIndexer.getPosition(0), '0');
        Assert.assertEquals(fileIndexer.getPosition(1), '1');
        Assert.assertEquals(fileIndexer.getPosition(2), '2');
    }

    @Test
    public void threeCharsBufferSize1_allThreeIndexable() throws Exception {
        IFileAccess content = FileHelper.getLineProvider("012");
        IDataProvider fileIndexer = new FileDataIndexer(content, 1);
        Assert.assertEquals(fileIndexer.getPosition(0), '0');
        Assert.assertEquals(fileIndexer.getPosition(1), '1');
        Assert.assertEquals(fileIndexer.getPosition(2), '2');
    }

    @Test
    public void threeCharsBufferSize1_lastIndexable() throws Exception {
        IFileAccess content = FileHelper.getLineProvider("012");
        IDataProvider fileIndexer = new FileDataIndexer(content, 1);
        Assert.assertEquals(fileIndexer.getPosition(2), '2');
    }

    @Test
    public void threeCharsBufferSize1_lastFirstSecondIndexable() throws Exception {
        IFileAccess content = FileHelper.getLineProvider("012");
        IDataProvider fileIndexer = new FileDataIndexer(content, 1);
        Assert.assertEquals(fileIndexer.getPosition(2), '2');
        Assert.assertEquals(fileIndexer.getPosition(0), '0');
        Assert.assertEquals(fileIndexer.getPosition(1), '1');
    }

    @Test
    public void threeCharsBufferSize3_lastThreeIndexable() throws Exception {
        IFileAccess content = FileHelper.getLineProvider("0123456789");
        IDataProvider fileIndexer = new FileDataIndexer(content, 3);
        Assert.assertEquals(fileIndexer.getPosition(7), '7');
        Assert.assertEquals(fileIndexer.getPosition(8), '8');
        Assert.assertEquals(fileIndexer.getPosition(9), '9');
    }

    @Test
    public void tenCharsBufferSize3_oneAfterLastNotIndexable() throws Exception {
        IFileAccess content = FileHelper.getLineProvider("0123456789");
        IDataProvider fileIndexer = new FileDataIndexer(content, 3);
        Assert.assertEquals(fileIndexer.getPosition(10), '\u0000');
    }

    @Test
    public void tenCharsBufferSize3_manyAfterLastNotIndexable() throws Exception {
        IFileAccess content = FileHelper.getLineProvider("0123456789");
        IDataProvider fileIndexer = new FileDataIndexer(content, 3);
        Assert.assertEquals(fileIndexer.getPosition((long) Integer.MAX_VALUE + 10), '\u0000');
    }

    @Test
    public void tenCharsBufferSize3_oneBeforeFirstNotIndexable() throws Exception {
        IFileAccess content = FileHelper.getLineProvider("0123456789");
        IDataProvider fileIndexer = new FileDataIndexer(content, 3);
        Assert.assertEquals(fileIndexer.getPosition(-1), '\u0000');
    }

    @Test
    public void tenCharsBufferSize3_manyBeforeFirstNotIndexable() throws Exception {
        IFileAccess content = FileHelper.getLineProvider("0123456789");
        IDataProvider fileIndexer = new FileDataIndexer(content, 3);
        Assert.assertEquals(fileIndexer.getPosition((long) Integer.MIN_VALUE - 10), '\u0000');
    }

    @Test
    public void threeCharsBufferSize0_cannotBeCreated() throws Exception {
        IFileAccess content = FileHelper.getLineProvider("012");
        try {
            new FileDataIndexer(content, 0);
            Assert.fail();
        } catch (Exception ignored) {
            Assert.assertTrue(true);
        }
    }
}
