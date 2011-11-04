package de.alws11.fileio;

import java.io.IOException;

/**
 * This interface provides methods to create, read and write files.
 */
public interface IFilesController {
    /**
     * This method is used to create a file.
     *
     * @param fileName This parameter is the name of the file that should be created.
     * @throws IOException An error might occur at creating files, that already exist or that cannot be created.
     */
    void createFile(String fileName) throws IOException;

    /**
     * This method provides abstract read access to a requested file.
     *
     * @param fileName This parameter is the name of the file that read access is requested for.
     * @return The method returns abstract read access for the given file.
     * @throws IOException An error might occur, if the file does not exist.
     */
    IFileReadAccess getReaderForFile(String fileName) throws IOException;

    /**
     * This method provides abstract write access to a requested file.
     *
     * @param fileName This parameter is the name of the file that write access is requested for.
     * @return The method returns abstract write access for the given file.
     * @throws IOException An error might occur, if the file does not exist.
     */
    IFileWriteAccess getWriterForFile(String fileName) throws IOException;
}
