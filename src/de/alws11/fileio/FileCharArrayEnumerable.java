package de.alws11.fileio;

import java.util.Iterator;

public class FileCharArrayEnumerable implements Iterable<char[]> {
    IFileAccess _reader;
    int _bufferSize;

    public int GetBufferSize() {
        return _bufferSize;
    }

    public long IoReads = 0;

    public FileCharArrayEnumerable(IFileAccess reader, int bufferSize)
            throws Exception {
        _reader = reader;
        _bufferSize = bufferSize;
        if (_bufferSize < 1)
            throw new Exception("buffer too small");
    }

    public void Close() {
        try {
            _reader.close();
        } catch (Exception ex) {
        }
    }

    public Iterator<char[]> iterator() {
        return new CharArrayIterator(_bufferSize);
    }

    class CharArrayIterator implements Iterator<char[]> {
        char[] _buffer;
        int _lastRead;

        public int GetRealBufferSize() {
            return _lastRead;
        }

        public CharArrayIterator(int bufferSize) {
            _buffer = new char[bufferSize];
            _lastRead = bufferSize;
        }

        public boolean hasNext() {
            try {
                if (_lastRead == _buffer.length)
                    _lastRead = _reader.read(_buffer, 0, _buffer.length);
                else
                    _buffer = null;
                IoReads++;
            } catch (Exception ex) {
                _buffer = null;
                ex.printStackTrace();
            }

            return _buffer != null;
        }

        public char[] next() {
            return _buffer;
        }

        public void remove() {
        }
    }
}
