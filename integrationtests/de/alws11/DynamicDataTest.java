package de.alws11;

import de.alws11.data.DynamicData;
import junit.framework.Assert;
import org.junit.Test;

public class DynamicDataTest {
    private final String TEST_FILE = "E:\\test.txt";

    @Test
    public void dynamicDataOf5K_fileSize5K() throws Exception {
        DynamicDataHelper.create(TEST_FILE, DynamicData.startWith(5000l, "a"));
        Assert.assertEquals(5000l, FileAccessHelper.getSize(TEST_FILE));
        FileAccessHelper.delete(TEST_FILE);
    }
}
