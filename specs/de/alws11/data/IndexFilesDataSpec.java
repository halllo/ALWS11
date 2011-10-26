package de.alws11.data;

import de.alws11.IIndexStore;
import de.alws11.fileio.fake.FileAccessStub;
import de.alws11.fileio.fake.FilesControllerMock;
import junit.framework.Assert;
import org.junit.Test;

public class IndexFilesDataSpec {
    @Test
    public void noIndexWritten_noIndexReadable() throws Exception {
        FilesControllerMock filesControl = new FilesControllerMock();
        IIndexStore indices = new IndexFilesData(filesControl, 2);
        Assert.assertEquals(-1, indices.getIndex(0));
    }

    @Test
    public void oneIndexWrittenBufferSize2_oneIndexReadable() throws Exception {
        FilesControllerMock filesControl = new FilesControllerMock();
        IIndexStore indices = new IndexFilesData(filesControl, 2);
        indices.pushIndex(1);
        Assert.assertEquals(1, indices.getIndex(0));
    }

    @Test
    public void twoIndicesWrittenBufferSize2_bothIndicesReadableNoFileIo() throws Exception {
        FilesControllerMock filesControl = new FilesControllerMock();
        IIndexStore indices = new IndexFilesData(filesControl, 2);
        DataHelper.pushIndices(indices, 1, 2);
        Assert.assertEquals(1, indices.getIndex(0));
        Assert.assertEquals(2, indices.getIndex(1));
        Assert.assertEquals(0, filesControl.calls_to_createFile.size());
        Assert.assertEquals(0, filesControl.calls_to_getReaderForFile.size());
    }

    @Test
    public void threeIndicesWrittenBufferSize2_fileIsCreatedNamedWithStartIndex() throws Exception {
        FilesControllerMock filesControl = new FilesControllerMock();
        IIndexStore indices = new IndexFilesData(filesControl, 2);
        DataHelper.pushIndices(indices, 1, 2, 3);
        Assert.assertEquals(1, filesControl.calls_to_createFile.size());
        Assert.assertEquals("0.indices", filesControl.calls_to_createFile.get(0));
    }

    @Test
    public void threeIndicesWrittenBufferSize2_firstTwoIndicesAreWritten() throws Exception {
        FilesControllerMock filesControl = new FilesControllerMock();
        FileAccessStub writer = new FileAccessStub();
        filesControl.writers.put("0.indices", writer);
        IIndexStore indices = new IndexFilesData(filesControl, 2);
        DataHelper.pushIndices(indices, 1, 2, 3);
        Assert.assertEquals(2, writer.lines.size());
        Assert.assertEquals("1", writer.lines.get(0));
        Assert.assertEquals("2", writer.lines.get(1));
    }

    @Test
    public void fiveIndicesWrittenBufferSize2_twoFilesAreCreatedNamedWithIndices() throws Exception {
        FilesControllerMock filesControl = new FilesControllerMock();
        IIndexStore indices = new IndexFilesData(filesControl, 2);
        DataHelper.pushIndices(indices, 1, 2, 3, 4, 5);
        Assert.assertEquals(2, filesControl.calls_to_createFile.size());
        Assert.assertEquals("0.indices", filesControl.calls_to_createFile.get(0));
        Assert.assertEquals("2.indices", filesControl.calls_to_createFile.get(1));
    }

    @Test
    public void fiveIndicesWrittenBufferSize2_secondTwoIndicesAreWritten() throws Exception {
        FilesControllerMock filesControl = new FilesControllerMock();
        FileAccessStub writer2 = new FileAccessStub();
        filesControl.writers.put("2.indices", writer2);
        IIndexStore indices = new IndexFilesData(filesControl, 2);
        DataHelper.pushIndices(indices, 1, 2, 3, 4, 5);
        Assert.assertEquals(2, writer2.lines.size());
        Assert.assertEquals("3", writer2.lines.get(0));
        Assert.assertEquals("4", writer2.lines.get(1));
    }

    @Test
    public void fiveIndicesWrittenBufferSize2_lastIndexReadNoFileReading() throws Exception {
        FilesControllerMock filesControl = new FilesControllerMock();
        IIndexStore indices = new IndexFilesData(filesControl, 2);
        DataHelper.pushIndices(indices, 1, 2, 3, 4, 5);
        Assert.assertEquals(5, indices.getIndex(4));
        Assert.assertEquals(0, filesControl.calls_to_getReaderForFile.size());
    }

    @Test
    public void fiveIndicesWrittenBufferSize2_firstReadable() throws Exception {
        FilesControllerMock filesControl = new FilesControllerMock();
        filesControl.newFileAccessStub("0.indices");
        IIndexStore indices = new IndexFilesData(filesControl, 2);
        DataHelper.pushIndices(indices, 1, 2, 3, 4, 5);
        Assert.assertEquals(1, indices.getIndex(0));
    }

    @Test
    public void fiveIndicesWrittenBufferSize1_firstReadable() throws Exception {
        FilesControllerMock filesControl = new FilesControllerMock();
        filesControl.newFileAccessStub("0.indices");
        IIndexStore indices = new IndexFilesData(filesControl, 1);
        DataHelper.pushIndices(indices, 1, 2, 3, 4, 5);
        Assert.assertEquals(1, indices.getIndex(0));
    }

    @Test
    public void fiveIndicesWrittenBufferSize1_firstReadableTwice() throws Exception {
        FilesControllerMock filesControl = new FilesControllerMock();
        filesControl.newFileAccessStub("0.indices");
        IIndexStore indices = new IndexFilesData(filesControl, 1);
        DataHelper.pushIndices(indices, 1, 2, 3, 4, 5);
        Assert.assertEquals(1, indices.getIndex(0));
        Assert.assertEquals(1, indices.getIndex(0));
    }

    @Test
    public void fiveIndicesWrittenBufferSize0_exception() throws Exception {
        FilesControllerMock filesControl = new FilesControllerMock();
        filesControl.newFileAccessStub("0.indices");
        try {
            new IndexFilesData(filesControl, 0);
            Assert.fail();
        } catch (Exception ignored) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void fiveIndicesWrittenBufferSize2_oneBeforeFirstNotReadable() throws Exception {
        FilesControllerMock filesControl = new FilesControllerMock();
        filesControl.newFileAccessStub("0.indices");
        IIndexStore indices = new IndexFilesData(filesControl, 2);
        DataHelper.pushIndices(indices, 1, 2, 3, 4, 5);
        Assert.assertEquals(-1, indices.getIndex(-1));
    }

    @Test
    public void fiveIndicesWrittenBufferSize2_sixthNoReadable() throws Exception {
        FilesControllerMock filesControl = new FilesControllerMock();
        IIndexStore indices = new IndexFilesData(filesControl, 2);
        DataHelper.pushIndices(indices, 1, 2, 3, 4, 5);
        Assert.assertEquals(-1, indices.getIndex(6));
    }

    @Test
    public void sixIndicesWrittenBufferSize2_readableRegardlessOfWritingOperations() throws Exception {
        FilesControllerMock filesControl = new FilesControllerMock();
        filesControl.newFileAccessStub("0.indices");
        IIndexStore indices = new IndexFilesData(filesControl, 2);
        DataHelper.pushIndices(indices, 1);
        Assert.assertEquals(-1, indices.getIndex(1));
        DataHelper.pushIndices(indices, 2, 3, 4, 5);
        Assert.assertEquals(5, indices.getIndex(4));
        Assert.assertEquals(2, indices.getIndex(1));
        DataHelper.pushIndices(indices, 6);
        Assert.assertEquals(6, indices.getIndex(5));
    }

    @Test
    public void fiveIndicesWrittenBufferSize2_secondReadableTwiceOnSingleFileReading() throws Exception {
        FilesControllerMock filesControl = new FilesControllerMock();
        filesControl.newFileAccessStub("0.indices");
        IIndexStore indices = new IndexFilesData(filesControl, 2);
        DataHelper.pushIndices(indices, 1, 2, 3, 4, 5);
        Assert.assertEquals(2, indices.getIndex(1));
        Assert.assertEquals(2, indices.getIndex(1));
        Assert.assertEquals(1, filesControl.calls_to_getReaderForFile.size());
    }

    @Test
    public void fiveIndicesWrittenBufferSize2_thirdReadable() throws Exception {
        FilesControllerMock filesControl = new FilesControllerMock();
        filesControl.newFileAccessStub("2.indices");
        IIndexStore indices = new IndexFilesData(filesControl, 2);
        DataHelper.pushIndices(indices, 1, 2, 3, 4, 5);
        Assert.assertEquals(3, indices.getIndex(2));
        Assert.assertEquals("2.indices", filesControl.calls_to_getReaderForFile.get(0));
    }
}
