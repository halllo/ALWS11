package de.alws11.data;

import de.alws11.IDataProvider;
import de.alws11.KnuthMorrisPratt.IIndexStore;
import de.alws11.fileio.FileHelper;
import de.alws11.fileio.fake.FileAccess;

public class DataHelper {
    public static IDataProvider getDataProvider(String content, int bufferSize) throws Exception {
        return new FileData(FileHelper.getLineProvider(content), bufferSize);
    }

    public static IIndexStore getIndexStore() throws Exception {
        FileAccess fileAccess = new FileAccess();
        return new PrefixFileData(fileAccess, fileAccess);
    }
}
