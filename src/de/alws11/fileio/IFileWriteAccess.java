package de.alws11.fileio;

import java.io.IOException;

public interface IFileWriteAccess {
    void writeLine(String line) throws IOException;

    void prepareForRead() throws IOException;

    void close() throws IOException;
}
