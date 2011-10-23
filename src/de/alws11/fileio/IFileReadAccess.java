package de.alws11.fileio;

import java.io.IOException;

public interface IFileReadAccess {
    int read(char[] buffer, int offset, int length) throws IOException;

    String readLine() throws IOException;

    long getFileSize();

    void close() throws IOException;

    void reset() throws IOException;
}
