package de.alws11.data;

import de.alws11.IDataProvider;
import de.alws11.IIndexStore;
import de.alws11.fileio.FileHelper;
import de.alws11.fileio.fake.FileAccessStub;

public class DataHelper {
    public static IDataProvider getDataProvider(String content, int bufferSize) throws Exception {
        return new FileData(FileHelper.getLineProvider(content), bufferSize);
    }

    public static IIndexStore getIndexStore() throws Exception {
        FileAccessStub fileAccess = new FileAccessStub();
        return new IndexFileData(fileAccess, fileAccess);
    }

    public static void pushIndices(IIndexStore iStore, long... indices) {
        for (long index : indices) {
            iStore.pushIndex(index);
        }
    }
}
