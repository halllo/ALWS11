package de.alws11;

import de.alws11.data.DynamicData;
import de.alws11.fileio.FileBufferedWriter;
import de.alws11.fileio.IFileWriteAccess;

public class TimeMeasuresHelper {
    public static void create(String filePath, DynamicData dataToWrite) throws Exception {
        FileAccessHelper.delete(filePath);
        IFileWriteAccess patternWriter = new FileBufferedWriter(filePath);
        dataToWrite.toFile(patternWriter);
        patternWriter.close();
    }
}
