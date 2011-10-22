package de.alws11.fileio.fake;

import de.alws11.fileio.IFileReadAccess;
import de.alws11.fileio.IFileWriteAccess;

import java.util.ArrayList;
import java.util.Collections;

public class FileAccess implements IFileReadAccess, IFileWriteAccess {
    private ArrayList<String> _lines;
    private int _currentLineIndex;
    private int _currentCharIndex;

    public FileAccess() {
        _lines = new ArrayList<String>();
    }

    public void returnLines(String... lines) {
        Collections.addAll(_lines, lines);
        reset();
    }

    public int read(char[] buffer, int offset, int length) {
        int i = 0;
        for (; i < length && _currentCharIndex < _lines.get(0).length(); i++, _currentCharIndex++) {
            buffer[i] = _lines.get(0).charAt(_currentCharIndex);
        }
        return i;
    }

    public String readLine() {
        if (_currentLineIndex < _lines.size()) {
            return _lines.get(_currentLineIndex++);
        } else {
            return null;
        }
    }

    public void writeNumber(long number) {
        _lines.add(String.valueOf(number));
    }

    public void prepareForRead() {
        //ignored
    }

    public void close() {
        //ignored
    }

    public void reset() {
        _currentCharIndex = 0;
        _currentLineIndex = 0;
    }
}
