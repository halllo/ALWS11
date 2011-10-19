package de.alws11.data;

import de.alws11.IDataProvider;
import de.alws11.data.FileData;
import de.alws11.fileio.FileHelper;
import de.alws11.fileio.IFileAccess;
import junit.framework.Assert;
import org.junit.Test;

public class FileDataSpec {
    @Test
    public void twoCharsBufferSize2_bothIndexable() throws Exception {
        IFileAccess content = FileHelper.getLineProvider("01");
        IDataProvider fileIndexer = new FileData(content, 2);
        Assert.assertEquals(fileIndexer.getPosition(0), '0');
        Assert.assertEquals(fileIndexer.getPosition(1), '1');
    }

    @Test
    public void oneCharsBufferSize2_firstIndexable() throws Exception {
        IFileAccess content = FileHelper.getLineProvider("0");
        IDataProvider fileIndexer = new FileData(content, 2);
        Assert.assertEquals(fileIndexer.getPosition(0), '0');
        Assert.assertEquals(fileIndexer.getPosition(1), '\u0000');
    }

    @Test
    public void threeCharsBufferSize2_allThreeIndexable() throws Exception {
        IFileAccess content = FileHelper.getLineProvider("012");
        IDataProvider fileIndexer = new FileData(content, 2);
        Assert.assertEquals(fileIndexer.getPosition(0), '0');
        Assert.assertEquals(fileIndexer.getPosition(1), '1');
        Assert.assertEquals(fileIndexer.getPosition(2), '2');
    }

    @Test
    public void threeCharsBufferSize1_allThreeIndexable() throws Exception {
        IFileAccess content = FileHelper.getLineProvider("012");
        IDataProvider fileIndexer = new FileData(content, 1);
        Assert.assertEquals(fileIndexer.getPosition(0), '0');
        Assert.assertEquals(fileIndexer.getPosition(1), '1');
        Assert.assertEquals(fileIndexer.getPosition(2), '2');
    }

    @Test
    public void threeCharsBufferSize1_lastIndexable() throws Exception {
        IFileAccess content = FileHelper.getLineProvider("012");
        IDataProvider fileIndexer = new FileData(content, 1);
        Assert.assertEquals(fileIndexer.getPosition(2), '2');
    }

    @Test
    public void threeCharsBufferSize1_lastFirstSecondIndexable() throws Exception {
        IFileAccess content = FileHelper.getLineProvider("012");
        IDataProvider fileIndexer = new FileData(content, 1);
        Assert.assertEquals(fileIndexer.getPosition(2), '2');
        Assert.assertEquals(fileIndexer.getPosition(0), '0');
        Assert.assertEquals(fileIndexer.getPosition(1), '1');
    }

    @Test
    public void threeCharsBufferSize3_lastThreeIndexable() throws Exception {
        IFileAccess content = FileHelper.getLineProvider("0123456789");
        IDataProvider fileIndexer = new FileData(content, 3);
        Assert.assertEquals(fileIndexer.getPosition(7), '7');
        Assert.assertEquals(fileIndexer.getPosition(8), '8');
        Assert.assertEquals(fileIndexer.getPosition(9), '9');
    }

    @Test
    public void tenCharsBufferSize3_oneAfterLastNotIndexable() throws Exception {
        IFileAccess content = FileHelper.getLineProvider("0123456789");
        IDataProvider fileIndexer = new FileData(content, 3);
        Assert.assertEquals(fileIndexer.getPosition(10), '\u0000');
    }

    @Test
    public void tenCharsBufferSize3_manyAfterLastNotIndexable() throws Exception {
        IFileAccess content = FileHelper.getLineProvider("0123456789");
        IDataProvider fileIndexer = new FileData(content, 3);
        Assert.assertEquals(fileIndexer.getPosition((long) Integer.MAX_VALUE + 10), '\u0000');
    }

    @Test
    public void tenCharsBufferSize3_oneBeforeFirstNotIndexable() throws Exception {
        IFileAccess content = FileHelper.getLineProvider("0123456789");
        IDataProvider fileIndexer = new FileData(content, 3);
        Assert.assertEquals(fileIndexer.getPosition(-1), '\u0000');
    }

    @Test
    public void tenCharsBufferSize3_manyBeforeFirstNotIndexable() throws Exception {
        IFileAccess content = FileHelper.getLineProvider("0123456789");
        IDataProvider fileIndexer = new FileData(content, 3);
        Assert.assertEquals(fileIndexer.getPosition((long) Integer.MIN_VALUE - 10), '\u0000');
    }

    @Test
    public void threeCharsBufferSize0_cannotBeCreated() throws Exception {
        IFileAccess content = FileHelper.getLineProvider("012");
        try {
            new FileData(content, 0);
            Assert.fail();
        } catch (Exception ignored) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void noCharsBufferSize1_noneIndexable() throws Exception {
        IFileAccess content = FileHelper.getLineProvider("");
        IDataProvider fileIndexer = new FileData(content, 1);
        Assert.assertEquals(fileIndexer.getPosition(0), '\u0000');
    }
}
