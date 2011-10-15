package de.alws11.fileio;

import java.util.Iterator;

public class FileLineEnumerable implements Iterable<String> {
    IFileAccess _reader;

    public long IoReads = 0;

    public FileLineEnumerable(IFileAccess reader) throws Exception {
        _reader = reader;
    }

    public void Close() {
        try {
            _reader.close();
        } catch (Exception ex) {
        }
    }

    public Iterator<String> iterator() {
        return new StringIterator();
    }

    class StringIterator implements Iterator<String> {
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
