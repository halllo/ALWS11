package de.alws11;

import junit.framework.Assert;
import org.junit.Test;

public class FileAccessPerfTest {
    private final String TEST_FILE = "E:\\test.txt";
    private final long COUNT_OF_INDICES = 2000000;

    @Test
    public void manyIndicesWritten_firstTwoReadable() throws Exception {
        FileAccessHelper.delete(TEST_FILE);
        IIndexStore indices = FileAccessHelper.getIndexStore(TEST_FILE);
        for (long i = 1; i <= COUNT_OF_INDICES; i++) {
            indices.pushIndex(i);
        }
        Assert.assertEquals(1, indices.getIndex(0));
        Assert.assertEquals(2, indices.getIndex(1));
        indices.close();
        FileAccessHelper.delete(TEST_FILE);
    }

    @Test
    public void manyIndicesWritten_lastTwoReadable() throws Exception {
        FileAccessHelper.delete(TEST_FILE);
        IIndexStore indices = FileAccessHelper.getIndexStore(TEST_FILE);
        for (long i = 1; i <= COUNT_OF_INDICES; i++) {
            indices.pushIndex(i);
        }
        Assert.assertEquals(COUNT_OF_INDICES - 1, indices.getIndex(COUNT_OF_INDICES - 2));
        Assert.assertEquals(COUNT_OF_INDICES, indices.getIndex(COUNT_OF_INDICES - 1));
        indices.close();
        FileAccessHelper.delete(TEST_FILE);
    }
}
