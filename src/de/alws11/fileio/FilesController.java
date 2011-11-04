package de.alws11.fileio;

import java.io.File;
import java.io.IOException;

/**
 * This class provides mechanisms of creating files and opening files for read and write access.
 */
public class FilesController implements IFilesController {
    private String _folder;

    /**
     * This constructor sets up the root directory at where files can be created, read and written.
     *
     * @param folder This parameter is the root folder for the file accessing.
     */
    public FilesController(String folder) {
        _folder = folder;
    }

    /**
     * This method creates a file in the specifies root folder.
     *
     * @param fileName This parameter is the name of the file that should be created.
     * @throws IOException An error might occur, if the file already exists or if it can not be created.
     */
    public void createFile(String fileName) throws IOException {
        if (!new File(getFullFileName(fileName)).createNewFile())
            throw new IOException("file could not be created");
    }

    /**
     * This method opens a file up for reading.
     *
     * @param fileName This parameter is the name of the file that read access is requested for.
     * @return The method returns abstract read access to the given file.
     * @throws IOException An error might occur, if the file does not exist.
     */
    public IFileReadAccess getReaderForFile(String fileName) throws IOException {
        return new FileBufferedReader(getFullFileName(fileName));
    }

    /**
     * This method opens a file up for writing.
     *
     * @param fileName This parameter is the name of the file that write access is requested for.
     * @return The method returns abstract write access to the given file.
     * @throws IOException An error might occur, if the file does not exist.
     */
    public IFileWriteAccess getWriterForFile(String fileName) throws IOException {
        return new FileBufferedWriter(getFullFileName(fileName));
    }

    private String getFullFileName(String fileName) {
        return _folder + "\\" + fileName;
    }
}
