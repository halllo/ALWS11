package de.alws11.fileio;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileBufferedReader implements IFileReadAccess {
    private BufferedReader _reader;
    private String _filePath;

    public FileBufferedReader(String filePath) throws IOException {
        _filePath = filePath;
        reset();
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

    public void reset() throws IOException {
        if (_reader != null) _reader.close();
        _reader = new BufferedReader(new FileReader(_filePath));
    }
}
