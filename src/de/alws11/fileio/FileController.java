package de.alws11.fileio;

import java.io.File;
import java.io.IOException;

public class FileController implements IFilesController {
    private String _folder;

    public FileController(String folder) {
        _folder = folder;
    }

    public void createFile(String fileName) throws IOException {
        if (!new File(getFullFileName(fileName)).createNewFile())
            throw new IOException("file could not be created");
    }

    public IFileReadAccess getReaderForFile(String fileName) throws IOException {
        return new FileBufferedReader(getFullFileName(fileName));
    }

    public IFileWriteAccess getWriterForFile(String fileName) throws IOException {
        return new FileBufferedWriter(getFullFileName(fileName));
    }

    private String getFullFileName(String fileName) {
        return _folder + "\\" + fileName;
    }
}
