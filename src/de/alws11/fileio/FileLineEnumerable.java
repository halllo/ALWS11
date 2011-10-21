package de.alws11.fileio;

import java.util.Iterator;

public class FileLineEnumerable implements Iterable<String> {
    IFileReadAccess _reader;

    public long IoReads = 0;

    public FileLineEnumerable(IFileReadAccess reader) throws Exception {
        _reader = reader;
    }

    public void close() {
        try {
            _reader.close();
        } catch (Exception ignored) {
        }
    }

    public void reset() {
        try {
            _reader.reset();
        } catch (Exception ignored) {
        }
    }

    public Iterator<String> iterator() {
        return new StringIterator();
    }

    public class StringIterator implements Iterator<String> {
        String _currentLine;

        public StringIterator() {
        }

        public boolean hasNext() {
            try {
                _currentLine = _reader.readLine();
                IoReads++;
            } catch (Exception ex) {
                _currentLine = null;
                ex.printStackTrace();
            }

            return _currentLine != null;
        }

        public String next() {
            return _currentLine;
        }

        public void remove() {
        }
    }
}
