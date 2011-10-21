package de.alws11.fileio;

import java.io.IOException;

public interface IFileWriteAccess {
    void writeNumber(long number) throws IOException;

    void close() throws IOException;
}