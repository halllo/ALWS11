package de.alws11.fileio;

import de.alws11.fileio.fake.FileAccess;

public class FileHelper {
    public static IFileAccess getLineProvider(String... expectedLines) throws Exception {
        FileAccess readSource = new FileAccess();
        readSource.returnLines(expectedLines);
        return readSource;
    }

    public static String[] getLines(IFileAccess fileAccess, int expectedLines) throws Exception {
        int lineIndex = 0;
        String[] lineList = new String[expectedLines];
        for (String line : new FileLineEnumerable(fileAccess)) {
            lineList[lineIndex++] = line;
        }
        return lineList;
    }

    public static char[][] getChunks(IFileAccess fileAccess, int bufferSize, int expectedReads) throws Exception {
        int chunkIndex = 0;
        char[][] chunkList = new char[expectedReads][];
        for (char[] chunk : new FileCharArrayEnumerable(fileAccess, bufferSize)) {
            chunkList[chunkIndex++] = chunk.clone();
        }
        return chunkList;
    }

    public static char[] getChars(IFileAccess fileAccess, int bufferSize, int expectedReads) throws Exception {
        int charIndex = 0;
        char[] charList = new char[expectedReads];
        for (char currentChar : new FileCharEnumerable(fileAccess, bufferSize)) {
            charList[charIndex++] = currentChar;
        }
        return charList;
    }
}
