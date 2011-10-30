package de.alws11;

import de.alws11.data.FileData;
import de.alws11.data.IndexFileData;
import de.alws11.data.IndexFilesData;
import de.alws11.fileio.FileBufferedReader;
import de.alws11.fileio.FileBufferedWriter;
import de.alws11.fileio.FilesController;

public class SearchIntegrationHelper {
    public static void close(IIndexStore indices, IDataProvider... dataProviders) {
        indices.close();
        for (IDataProvider data : dataProviders) {
            data.close();
        }
    }

    public static IDataProvider getFile(String file, int bufferSize) throws Exception {
        return new FileData(new FileBufferedReader(file), bufferSize);
    }

    public static AsymmetricDataProvider getFileAsym(String file, int bufferSize) throws Exception {
        return new AsymmetricDataProvider(
                new FileData(new FileBufferedReader(file), bufferSize),
                new FileData(new FileBufferedReader(file), bufferSize));
    }

    public static IIndexStore getIndexStoreFile(String file) throws Exception {
        return new IndexFileData(new FileBufferedWriter(file), new FileBufferedReader(file));
    }

    public static IIndexStore getIndexStoreFiles(String path, int bufferSize) throws Exception {
        return new IndexFilesData(new FilesController(path), bufferSize);
    }
}
