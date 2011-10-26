package de.alws11.fileio.fake;

import de.alws11.fileio.IFileReadAccess;
import de.alws11.fileio.IFileWriteAccess;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileAccessStub implements IFileReadAccess, IFileWriteAccess {
    public List<String> lines;
    private int _currentLineIndex;
    private int _currentCharIndex;

    public FileAccessStub() {
        lines = new ArrayList<String>();
    }

    public void returnLines(String... lines) {
        Collections.addAll(this.lines, lines);
        reset();
    }

    public int read(char[] buffer, int offset, int length) {
        int i = 0;
        for (; i < length && _currentCharIndex < lines.get(0).length(); i++, _currentCharIndex++) {
            buffer[i] = lines.get(0).charAt(_currentCharIndex);
        }
        return i;
    }

    public String readLine() {
        if (_currentLineIndex < lines.size()) {
            return lines.get(_currentLineIndex++);
        } else {
            return null;
        }
    }

    public long getFileSize() {
        return 0;
    }

    public void writeLine(String line) {
        lines.add(line);
    }

    public void prepareForRead() {
        //ignored
    }

    public void close() {
        reset();
    }

    public void reset() {
        _currentCharIndex = 0;
        _currentLineIndex = 0;
    }
}
