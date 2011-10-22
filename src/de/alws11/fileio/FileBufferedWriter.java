package de.alws11.fileio;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileBufferedWriter implements IFileWriteAccess {
    private BufferedWriter _writer;

    public FileBufferedWriter(String filePath) throws IOException {
        _writer = new BufferedWriter(new FileWriter(filePath, false));
    }

    public void writeNumber(long number) throws IOException {
        _writer.write(String.valueOf(number));
        _writer.newLine();
    }

    public void prepareForRead() throws IOException {
        _writer.flush();
    }

    public void close() throws IOException {
        _writer.flush();
        _writer.close();
    }
}
