package de.alws11.fileio;

public class FileHelper {
    public static String[] getLines(IFileAccess fileAccess, int expectedLines) throws Exception {
        int lineIndex = 0;
        String[] lineList = new String[expectedLines];
        for (String line : new FileLineEnumerable(fileAccess)) {
            lineList[lineIndex++] = line;
        }
        return lineList;
    }

    public static IFileAccess getLineProvider(String... expectedLines) throws Exception {
        FileReadingSource readSource = new FileReadingSource();
        readSource.returnLines(expectedLines);
        return readSource;
    }
}
