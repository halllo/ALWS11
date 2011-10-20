package de.alws11;

import de.alws11.data.FileData;
import de.alws11.fileio.FileBufferedReader;
import junit.framework.Assert;
import org.junit.Test;

public class FileAccessTest {
    private IDataProvider getDataProvider(String filePath, int bufferSize) throws Exception {
        return new FileData(new FileBufferedReader(filePath), bufferSize);
    }

    @Test
    public void fileBuffersize2_IndexableForwards() throws Exception {
        IDataProvider dataIndexer = getDataProvider("E:\\test.txt", 2);
        Assert.assertEquals(dataIndexer.getPosition(-1), '\u0000');
        Assert.assertEquals(dataIndexer.getPosition(0), 'a');
        Assert.assertEquals(dataIndexer.getPosition(1), 'b');
        Assert.assertEquals(dataIndexer.getPosition(2), 'c');
        Assert.assertEquals(dataIndexer.getPosition(25), 'z');
        Assert.assertEquals(dataIndexer.getPosition(26), '\u0000');
    }

    @Test
    public void fileBuffersize2_IndexableForwardsBackwards() throws Exception {
        IDataProvider dataIndexer = getDataProvider("E:\\test.txt", 2);
        Assert.assertEquals(dataIndexer.getPosition(-1), '\u0000');
        Assert.assertEquals(dataIndexer.getPosition(0), 'a');
        Assert.assertEquals(dataIndexer.getPosition(25), 'z');
        Assert.assertEquals(dataIndexer.getPosition(1), 'b');
        Assert.assertEquals(dataIndexer.getPosition(2), 'c');
        Assert.assertEquals(dataIndexer.getPosition(26), '\u0000');
    }
}
