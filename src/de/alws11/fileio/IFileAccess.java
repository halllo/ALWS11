package de.alws11.fileio;

import java.io.IOException;

public interface IFileAccess {
    int read(char[] buffer, int offset, int length) throws IOException;
    String readLine() throws IOException;
    void close() throws IOException;
}
