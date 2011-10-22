package de.alws11;

import de.alws11.KnuthMorrisPratt.IIndexStore;
import de.alws11.data.FileData;
import de.alws11.data.PrefixFileData;
import de.alws11.fileio.FileBufferedReader;
import de.alws11.fileio.FileBufferedWriter;
import de.alws11.fileio.IFileReadAccess;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileAccessHelper {
    public static IDataProvider getDataProvider(String filePath, int bufferSize) throws Exception {
        return new FileData(new FileBufferedReader(filePath), bufferSize);
    }

    public static IIndexStore getIndexStore(String filePath) throws Exception {
        return new PrefixFileData(
                new FileBufferedWriter(filePath), new FileBufferedReader(filePath)
        );
    }

    public static IFileReadAccess getFileAccess(String filePath) throws IOException {
        return new FileBufferedReader(filePath);
    }

    public static void writeNumbers(String fileToWrite, long... numbers) throws IOException {
        FileBufferedWriter fbw = new FileBufferedWriter(fileToWrite);
        for (long number : numbers) {
            fbw.writeNumber(number);
        }
        fbw.close();
    }

    public static void delete(String file) throws Exception {
        File fileToDelete = new File(file);
        if (fileToDelete.exists()) {
            boolean deleted = fileToDelete.delete();
            if (!deleted) {
                throw new Exception("\"" + file + "\" could not be deleted");
            }
        }
    }

    public static void create(String file, String content) throws IOException {
        FileWriter fw = new FileWriter(file, false);
        fw.write(content);
        fw.flush();
        fw.close();
    }
}
