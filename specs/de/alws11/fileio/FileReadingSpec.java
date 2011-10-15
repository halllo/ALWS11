package de.alws11.fileio;

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
}
