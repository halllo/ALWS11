package de.alws11.fileio;

import java.io.IOException;

public class FileReadingSource implements IFileAccess {
    private String[] _lines;
    private int _currentLineIndex;

    public FileReadingSource() {

    }

    public void returnLines(String... lines) {
        _lines = lines;
        _currentLineIndex = 0;
    }

    public int read(char[] buffer, int offset, int length) throws IOException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String readLine() throws IOException {
        if (_currentLineIndex < _lines.length) {
            return _lines[_currentLineIndex++];
        } else {
            return null;
        }
    }

    public void close() throws IOException {

    }
}
