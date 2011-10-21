package de.alws11;

import de.alws11.fileio.FileBufferedReader;
import de.alws11.fileio.FileHelper;
import junit.framework.Assert;
import org.junit.Test;

public class FileAccessTest {
    private final String TEST_FILE = "E:\\test.txt";

    @Test
    public void fileBuffersize2_IndexableForwards() throws Exception {
        FileAccessHelper.create(TEST_FILE, "abcdefghijklmnopqrstuvwxyz");
        IDataProvider dataIndexer = FileAccessHelper.getDataProvider(TEST_FILE, 2);
        Assert.assertEquals(dataIndexer.getPosition(-1), '\u0000');
        Assert.assertEquals(dataIndexer.getPosition(0), 'a');
        Assert.assertEquals(dataIndexer.getPosition(1), 'b');
        Assert.assertEquals(dataIndexer.getPosition(2), 'c');
        Assert.assertEquals(dataIndexer.getPosition(25), 'z');
        Assert.assertEquals(dataIndexer.getPosition(26), '\u0000');
    }

    @Test
    public void fileBuffersize2_IndexableForwardsBackwards() throws Exception {
        FileAccessHelper.create(TEST_FILE, "abcdefghijklmnopqrstuvwxyz");
        IDataProvider dataIndexer = FileAccessHelper.getDataProvider(TEST_FILE, 2);
        Assert.assertEquals(dataIndexer.getPosition(-1), '\u0000');
        Assert.assertEquals(dataIndexer.getPosition(0), 'a');
        Assert.assertEquals(dataIndexer.getPosition(25), 'z');
        Assert.assertEquals(dataIndexer.getPosition(1), 'b');
        Assert.assertEquals(dataIndexer.getPosition(2), 'c');
        Assert.assertEquals(dataIndexer.getPosition(26), '\u0000');
    }

    @Test
    public void fileWriteTwoNumbers_twoLines() throws Exception {
        FileAccessHelper.delete(TEST_FILE);
        FileAccessHelper.writeNumbers(TEST_FILE, 1, 2);
        String[] lines = FileHelper.getLines(new FileBufferedReader(TEST_FILE), 3);
        Assert.assertEquals("1", lines[0]);
        Assert.assertEquals("2", lines[1]);
        Assert.assertEquals(null, lines[2]);
    }
}
