package de.alws11.fileio;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * This class provides the same functionality of the java.io.BufferedReader by encapsulating an instance of it.
 */
public class FileBufferedReader implements IFileReadAccess {
    private BufferedReader _reader;
    private String _filePath;
    private long _fileSize;

    /**
     * This constructor initializes the buffer.
     *
     * @param filePath This parameter is the path to a file that is read.
     * @throws IOException An error might occur during file access.
     */
    public FileBufferedReader(String filePath) throws IOException {
        _filePath = filePath;
        _fileSize = getCurrentFileSize();
        reset();
    }

    private long getCurrentFileSize() {
        File f = new File(_filePath);
        return f.length();
    }

    /**
     * This method provides the size of the file.
     *
     * @return The method returns the amount of characters in the file.
     */
    public long getFileSize() {
        return _fileSize;
    }

    /**
     * This method reads characters into the buffer and moves the reading window further.
     *
     * @param buffer This parameter is the buffer into which characters are read.
     * @param offset This parameter indicates at which position characters storage shall start in the buffer.
     * @param length This parameter indicates how many characters are read into the buffer.
     * @return The method returns the amount of characters that have been read.
     * @throws IOException An error might occur during file access.
     */
    public int read(char[] buffer, int offset, int length) throws IOException {
        return _reader.read(buffer, offset, length);
    }

    /**
     * This method reads a line and moves the reading windows further.
     *
     * @return The method returns the next line of the file.
     * @throws IOException An error might occur during file access.
     */
    public String readLine() throws IOException {
        return _reader.readLine();
    }

    /**
     * This method closes the open file.
     *
     * @throws IOException An error might occur during file access.
     */
    public void close() throws IOException {
        _reader.close();
    }

    /**
     * THis method resets the reading window so that read and readLine can read from the beginning of the file again.
     *
     * @throws IOException An error might occur during file access.
     */
    public void reset() throws IOException {
        if (_reader != null) _reader.close();
        _reader = new BufferedReader(new FileReader(_filePath));
    }
}
