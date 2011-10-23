package de.alws11;

import de.alws11.fileio.FileHelper;
import de.alws11.fileio.IFileReadAccess;
import junit.framework.Assert;
import org.junit.Test;

public class FileAccessTest {
    private final String TEST_FILE = "E:\\test.txt";

    @Test
    public void fileBuffersize2_IndexableForwards() throws Exception {
        FileAccessHelper.create(TEST_FILE, "abcdefghijklmnopqrstuvwxyz");
        IDataProvider dataIndexer = FileAccessHelper.getDataProvider(TEST_FILE, 2);
        Assert.assertEquals('\u0000', dataIndexer.getPosition(-1));
        Assert.assertEquals('a', dataIndexer.getPosition(0));
        Assert.assertEquals('b', dataIndexer.getPosition(1));
        Assert.assertEquals('c', dataIndexer.getPosition(2));
        Assert.assertEquals('z', dataIndexer.getPosition(25));
        Assert.assertEquals('\u0000', dataIndexer.getPosition(26));
        dataIndexer.close();
        FileAccessHelper.delete(TEST_FILE);
    }

    @Test
    public void fileBuffersize2_IndexableForwardsBackwards() throws Exception {
        FileAccessHelper.create(TEST_FILE, "abcdefghijklmnopqrstuvwxyz");
        IDataProvider dataIndexer = FileAccessHelper.getDataProvider(TEST_FILE, 2);
        Assert.assertEquals('\u0000', dataIndexer.getPosition(-1));
        Assert.assertEquals('a', dataIndexer.getPosition(0));
        Assert.assertEquals('z', dataIndexer.getPosition(25));
        Assert.assertEquals('b', dataIndexer.getPosition(1));
        Assert.assertEquals('c', dataIndexer.getPosition(2));
        Assert.assertEquals('\u0000', dataIndexer.getPosition(26));
        dataIndexer.close();
        FileAccessHelper.delete(TEST_FILE);
    }

    @Test
    public void fileWriteTwoNumbers_twoLines() throws Exception {
        FileAccessHelper.delete(TEST_FILE);
        FileAccessHelper.writeNumbers(TEST_FILE, 1, 2);
        IFileReadAccess fileReader = FileAccessHelper.getFileAccess(TEST_FILE);
        String[] lines = FileHelper.getLines(fileReader, 3);
        Assert.assertEquals("1", lines[0]);
        Assert.assertEquals("2", lines[1]);
        Assert.assertEquals(null, lines[2]);
        fileReader.close();
        FileAccessHelper.delete(TEST_FILE);
    }

    @Test
    public void indicesReadFromEmptyFile_noIndexReadable() throws Exception {
        FileAccessHelper.delete(TEST_FILE);
        FileAccessHelper.create(TEST_FILE, "");
        IIndexStore indices = FileAccessHelper.getIndexStore(TEST_FILE);
        Assert.assertEquals(-1, indices.getIndex(0));
        indices.close();
        FileAccessHelper.delete(TEST_FILE);
    }

    @Test
    public void singleIndexWritten_firstIndexReadable() throws Exception {
        FileAccessHelper.delete(TEST_FILE);
        IIndexStore indices = FileAccessHelper.getIndexStore(TEST_FILE);
        indices.pushIndex(1);
        Assert.assertEquals(1, indices.getIndex(0));
        indices.close();
        FileAccessHelper.delete(TEST_FILE);
    }

    @Test
    public void twoIndicesWritten_secondReadableOnlyAfterWrite() throws Exception {
        FileAccessHelper.delete(TEST_FILE);
        IIndexStore indices = FileAccessHelper.getIndexStore(TEST_FILE);
        indices.pushIndex(1);
        Assert.assertEquals(1, indices.getIndex(0));
        Assert.assertEquals(-1, indices.getIndex(1));
        indices.pushIndex(2);
        Assert.assertEquals(2, indices.getIndex(1));
        indices.close();
        FileAccessHelper.delete(TEST_FILE);
    }

    @Test
    public void threeIndicesWritten_firstTwoReadableInReverseOrderThirdOnlyAfterWrite() throws Exception {
        FileAccessHelper.delete(TEST_FILE);
        IIndexStore indices = FileAccessHelper.getIndexStore(TEST_FILE);
        indices.pushIndex(1);
        indices.pushIndex(2);
        Assert.assertEquals(2, indices.getIndex(1));
        Assert.assertEquals(1, indices.getIndex(0));
        Assert.assertEquals(-1, indices.getIndex(2));
        indices.pushIndex(3);
        Assert.assertEquals(3, indices.getIndex(2));
        indices.close();
        FileAccessHelper.delete(TEST_FILE);
    }

    @Test
    public void twoBigIndicesWritten_bothReadable() throws Exception {
        FileAccessHelper.delete(TEST_FILE);
        IIndexStore indices = FileAccessHelper.getIndexStore(TEST_FILE);
        indices.pushIndex(5294967297l);
        indices.pushIndex(5294967298l);
        Assert.assertEquals(5294967297l, indices.getIndex(0));
        Assert.assertEquals(5294967298l, indices.getIndex(1));
        indices.close();
        FileAccessHelper.delete(TEST_FILE);
    }
}