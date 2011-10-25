package de.alws11.fileio;

import java.io.IOException;

public interface IFilesController {
    void createFile(String fileName) throws IOException;

    IFileReadAccess getReaderForFile(String fileName) throws IOException;

    IFileWriteAccess getWriterForFile(String fileName) throws IOException;
}
