package de.alws11.fileio;

import java.util.Iterator;

public class FileCharEnumerable implements Iterable<Character> {
    FileCharArrayEnumerable _charArrays;

    public long IoReads = 0;

    public FileCharEnumerable(IFileAccess reader, int bufferSize) throws Exception {
        _charArrays = new FileCharArrayEnumerable(reader, bufferSize);
    }

    public void close() {
        try {
            _charArrays.close();
        } catch (Exception ignored) {
        }
    }

    public Iterator<Character> iterator() {
        return new CharIterator();
    }

    class CharIterator implements Iterator<Character> {
        long _gindex;
        FileCharArrayEnumerable.CharArrayIterator _charArrayIter;

        public CharIterator() {
            _gindex = -1;
            _charArrayIter = (FileCharArrayEnumerable.CharArrayIterator) _charArrays
                    .iterator();
        }

        public boolean hasNext() {
            _gindex++;
            if (_gindex % _charArrays.GetBufferSize() == 0) {
                if (_charArrayIter.GetRealBufferSize() == _charArrays.GetBufferSize()) {
                    _charArrayIter.hasNext();
                    IoReads++;
                }
            }
            int caBufferSize = (int) (_gindex % _charArrays.GetBufferSize());
            return caBufferSize < _charArrayIter.GetRealBufferSize();
        }

        public Character next() {
            int bufferIndex = (int) (_gindex % _charArrays.GetBufferSize());
            return _charArrayIter.next()[bufferIndex];
        }

        public void remove() {
        }
    }
}

