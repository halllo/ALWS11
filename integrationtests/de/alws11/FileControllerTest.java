package de.alws11;

import de.alws11.fileio.FilesController;
import de.alws11.fileio.IFileReadAccess;
import de.alws11.fileio.IFileWriteAccess;
import junit.framework.Assert;
import org.junit.Test;

import java.io.File;

public class FileControllerTest {
    @Test
    public void fileCreation_fileExistsAfterwards() throws Exception {
        FileAccessHelper.delete("E:\\test.txt");
        FilesController files = new FilesController("E:");
        files.createFile("test.txt");
        Assert.assertTrue(new File("E:\\test.txt").exists());
        FileAccessHelper.delete("E:\\test.txt");
    }

    @Test
    public void fileReading_contentRead() throws Exception {
        FileAccessHelper.create("E:\\test.txt", "abc");
        FilesController files = new FilesController("E:");
        IFileReadAccess reader = files.getReaderForFile("test.txt");
        Assert.assertEquals("abc", reader.readLine());
        reader.close();
        FileAccessHelper.delete("E:\\test.txt");
    }

    @Test
    public void fileWriting_fileExistsAfterwards() throws Exception {
        FileAccessHelper.delete("E:\\test.txt");
        FilesController files = new FilesController("E:");
        IFileWriteAccess writer = files.getWriterForFile("test.txt");
        writer.writeLine("abc");
        writer.close();
        IFileReadAccess reader = files.getReaderForFile("test.txt");
        Assert.assertEquals("abc", reader.readLine());
        reader.close();
        FileAccessHelper.delete("E:\\test.txt");
    }
}
