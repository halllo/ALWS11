package de.alws11.fileio;

import java.io.IOException;

/**
 * This interface provides methods that support file based writing operations.
 */
public interface IFileWriteAccess {
    /**
     * This method writes a string with appending newline characters to the end of the file.
     *
     * @param line This parameter is the line that is written.
     * @throws IOException An error during file access might occur.
     */
    void writeLine(String line) throws IOException;

    /**
     * This method writes a string to the end of the file.
     *
     * @param text This parameter is the text that is written.
     * @throws IOException An error during file access might occur.
     */
    void write(String text) throws IOException;

    /**
     * This method prepares the file for reading of what just has been written.
     *
     * @throws IOException An error during file access might occur.
     */
    void prepareForRead() throws IOException;

    /**
     * This method closes the open file.
     *
     * @throws IOException An error during file access might occur.
     */
    void close() throws IOException;
}
