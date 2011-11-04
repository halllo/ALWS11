package de.alws11.fileio;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This class provides the same functionality of the java.io.BufferedWriter by encapsulating an instance of it.
 */
public class FileBufferedWriter implements IFileWriteAccess {
    private BufferedWriter _writer;

    /**
     * This constructor initializes the buffer.
     *
     * @param filePath This parameter is the path to a file that is read.
     * @throws IOException An error might occur during file access.
     */
    public FileBufferedWriter(String filePath) throws IOException {
        _writer = new BufferedWriter(new FileWriter(filePath, false));
    }

    /**
     * This method writes a line with text to the end of the file.
     *
     * @param line This parameter is the line that is written.
     * @throws IOException An error might occur during file access.
     */
    public void writeLine(String line) throws IOException {
        write(line);
        _writer.newLine();
    }

    /**
     * This method writes a text to the end of the file.
     *
     * @param text This parameter is the text that is written.
     * @throws IOException An error might occur during file access.
     */
    public void write(String text) throws IOException {
        _writer.write(text);
    }

    /**
     * This method flushes the current buffer of the writer so that its content can be read from the file.
     *
     * @throws IOException An error might occur during file access.
     */
    public void prepareForRead() throws IOException {
        _writer.flush();
    }

    /**
     * This method closes the open file.
     *
     * @throws IOException An error might occur during file access.
     */
    public void close() throws IOException {
        _writer.flush();
        _writer.close();
    }
}
