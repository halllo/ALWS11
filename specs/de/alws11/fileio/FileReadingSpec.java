package de.alws11.fileio;

import de.alws11.AssertHelper;
import junit.framework.Assert;
import org.junit.Test;

public class FileReadingSpec {
    @Test
    public void twoLines_bothRead() throws Exception {
        IFileAccess content = FileHelper.getLineProvider("hallo", "welt");
        String[] lines = FileHelper.getLines(content, 2);
        Assert.assertEquals("hallo", lines[0]);
        Assert.assertEquals("welt", lines[1]);
    }

    @Test
    public void noLines_noneRead() throws Exception {
        IFileAccess content = FileHelper.getLineProvider();
        String[] lines = FileHelper.getLines(content, 1);
        Assert.assertEquals(null, lines[0]);
    }

    @Test
    public void length4buffer2_twoReads() throws Exception {
        IFileAccess content = FileHelper.getLineProvider("0123");
        char[][] chunks = FileHelper.getChunks(content, 2, 2);
        Assert.assertTrue(AssertHelper.areSame("01", chunks[0], true));
        Assert.assertTrue(AssertHelper.areSame("23", chunks[1], true));
    }

    @Test
    public void length3buffer2_twoReads() throws Exception {
        IFileAccess content = FileHelper.getLineProvider("012");
        char[][] chunks = FileHelper.getChunks(content, 2, 2);
        Assert.assertTrue(AssertHelper.areSame("01", chunks[0], true));
        Assert.assertTrue(AssertHelper.areSame("2", chunks[1], false));
    }

    @Test
    public void length3buffer4_oneReads() throws Exception {
        IFileAccess content = FileHelper.getLineProvider("012");
        char[][] chunks = FileHelper.getChunks(content, 4, 1);
        Assert.assertTrue(AssertHelper.areSame("012\u0000", chunks[0], true));
    }

    @Test
    public void length3buffer1_threeReads() throws Exception {
        IFileAccess content = FileHelper.getLineProvider("012");
        char[][] chunks = FileHelper.getChunks(content, 1, 3);
        Assert.assertTrue(AssertHelper.areSame("0", chunks[0], true));
        Assert.assertTrue(AssertHelper.areSame("1", chunks[1], true));
        Assert.assertTrue(AssertHelper.areSame("2", chunks[2], true));
    }

    @Test
    public void length3buffer0_noReads() throws Exception {
        IFileAccess content = FileHelper.getLineProvider("012");
        try {
            FileHelper.getChunks(content, 0, 3);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertTrue(e.getMessage().contains("buffer too small"));
        }
    }

    @Test
    public void length4buffer2_fourChars() throws Exception {
        IFileAccess content = FileHelper.getLineProvider("0123");
        char[] chars = FileHelper.getChars(content, 2, 4);
        Assert.assertTrue(AssertHelper.areSame("0123", chars, true));
    }

    @Test
    public void length3buffer2_threeChars() throws Exception {
        IFileAccess content = FileHelper.getLineProvider("012");
        char[] chars = FileHelper.getChars(content, 2, 3);
        Assert.assertTrue(AssertHelper.areSame("012", chars, true));
    }

    @Test
    public void length3buffer4_threeChars() throws Exception {
        IFileAccess content = FileHelper.getLineProvider("012");
        char[] chars = FileHelper.getChars(content, 4, 3);
        Assert.assertTrue(AssertHelper.areSame("012", chars, true));
    }

    @Test
    public void length3buffer1_threeChars() throws Exception {
        IFileAccess content = FileHelper.getLineProvider("012");
        char[] chars = FileHelper.getChars(content, 1, 3);
        Assert.assertTrue(AssertHelper.areSame("012", chars, true));
    }

    @Test
    public void length0buffer2_noChars() throws Exception {
        IFileAccess content = FileHelper.getLineProvider("");
        char[] chars = FileHelper.getChars(content, 2, 1);
        Assert.assertTrue(AssertHelper.areSame("\u0000", chars, true));
    }

    @Test
    public void length3buffer0_exception() throws Exception {
        IFileAccess content = FileHelper.getLineProvider("012");
        try {
            FileHelper.getChars(content, 0, 3);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertTrue(e.getMessage().contains("buffer too small"));
        }
    }
}
