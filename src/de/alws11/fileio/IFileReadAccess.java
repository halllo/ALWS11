package de.alws11.fileio;

import java.io.IOException;

/**
 * This interface provides methods that support file based reading operations.
 */
public interface IFileReadAccess {
    /**
     * This method reads characters into a buffer and moves the reading windows further.
     *
     * @param buffer This parameter is the buffer into which characters are read.
     * @param offset This parameter indicates at which position characters storage shall start in the buffer.
     * @param length This parameter indicates how many characters are read into the buffer.
     * @return The method returns the amount of characters that have been read.
     * @throws IOException An error might occur during file access.
     */
    int read(char[] buffer, int offset, int length) throws IOException;

    /**
     * This method reads a line and moves the reading windows further.
     *
     * @return The method returns the read line.
     * @throws IOException An error might occur during file access.
     */
    String readLine() throws IOException;

    /**
     * This method provides the size of the file.
     *
     * @return The methode returns the amount of characters in the file.
     */
    long getFileSize();

    /**
     * This method closes the open file.
     *
     * @throws IOException An error during file access might occur.
     */
    void close() throws IOException;

    /**
     * This method resets the reading window so that read and readline start from the file beginning again.
     *
     * @throws IOException An error during file access might occur.
     */
    void reset() throws IOException;
}
