package de.alws11;

import de.alws11.data.DynamicData;
import de.alws11.fileio.FileBufferedWriter;
import junit.framework.Assert;
import org.junit.Test;

public class DynamicDataTest {
    private final String TEST_FILE = "E:\\test.txt";

    @Test
    public void dynamicDataOf5K_fileSize5K() throws Exception {
        DynamicData as = DynamicData.startWith(5000l, "a");
        FileBufferedWriter writer = new FileBufferedWriter(TEST_FILE);
        as.toFile(writer);
        writer.close();
        Assert.assertEquals(5000l, FileAccessHelper.getSize(TEST_FILE));
        FileAccessHelper.delete(TEST_FILE);
    }
}
