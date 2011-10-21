package de.alws11.fileio.fake;

import de.alws11.fileio.IFileReadAccess;

import java.io.IOException;

public class FileAccess implements IFileReadAccess {
    private String[] _lines;
    private int _currentLineIndex;
    private int _currentCharIndex;

    public FileAccess() {

    }

    public void returnLines(String... lines) {
        _lines = lines;
        _currentLineIndex = 0;
        _currentCharIndex = 0;
    }

    public int read(char[] buffer, int offset, int length) throws IOException {
        int i = 0;
        for (; i < length && _currentCharIndex < _lines[0].length(); i++, _currentCharIndex++) {
            buffer[i] = _lines[0].charAt(_currentCharIndex);
        }
        return i;
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

    public void reset() throws IOException {
        _currentCharIndex = 0;
    }
}
