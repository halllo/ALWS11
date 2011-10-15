package de.alws11.fileio;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileBufferedReader implements IFileAccess {
    BufferedReader _reader;

    public FileBufferedReader(String filePath) throws FileNotFoundException {
        _reader = new BufferedReader(new FileReader(filePath));
    }

    public int read(char[] buffer, int offset, int length) throws IOException {
        return _reader.read(buffer, offset, length);
    }

    public String readLine() throws IOException {
        return _reader.readLine();
    }

    public void close() throws IOException {
        _reader.close();
    }
}
