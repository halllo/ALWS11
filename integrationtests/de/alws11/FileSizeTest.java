package de.alws11;

import de.alws11.fileio.FileBufferedReader;
import de.alws11.fileio.IFileReadAccess;
import junit.framework.Assert;
import org.junit.Test;

public class FileSizeTest {
    private final String TEST_FILE = "E:\\test.txt";

    @Test
    public void tenNumbers_fileSize10() throws Exception {
        FileAccessHelper.create(TEST_FILE, "0123456789");
        IFileReadAccess file = new FileBufferedReader(TEST_FILE);
        Assert.assertEquals(10, file.getFileSize());
        file.close();
        FileAccessHelper.delete(TEST_FILE);
    }

    @Test
    public void noChars_fileSize0() throws Exception {
        FileAccessHelper.create(TEST_FILE, "");
        IFileReadAccess file = new FileBufferedReader(TEST_FILE);
        Assert.assertEquals(0, file.getFileSize());
        file.close();
        FileAccessHelper.delete(TEST_FILE);
    }

    @Test
    public void twentySixChars_fileSize26() throws Exception {
        FileAccessHelper.create(TEST_FILE, "abcdefghijklmnopqrstuvwxyz");
        IFileReadAccess file = new FileBufferedReader(TEST_FILE);
        Assert.assertEquals(26, file.getFileSize());
        file.close();
        FileAccessHelper.delete(TEST_FILE);
    }
}
