package de.alws11.fileio;

import de.alws11.IDataProvider;

public class FileDataIndexer implements IDataProvider {
    private char[] _currentBuffer;
    private FileCharArrayEnumerable _fileCharReader;
    private FileCharArrayEnumerable.CharArrayIterator _fileCharIterator;
    private long _currentEnd;

    public FileDataIndexer(IFileAccess fileAccess, int bufferSize) throws Exception {
        _fileCharReader = new FileCharArrayEnumerable(fileAccess, bufferSize);
        initIterator();
    }

    private void initIterator() {
        _fileCharReader.reset();
        _fileCharIterator = (FileCharArrayEnumerable.CharArrayIterator) _fileCharReader.iterator();
        _currentEnd = 0;
    }

    public char getPosition(long index) {
        resetIteratorIfJumpRequired(index);
        iterateUntilRequestPositionInBuffer(index);
        return getCharFromBuffer(calculateIndexOffset(index));
    }

    private char getCharFromBuffer(int indexOffset) {
        if (_currentBuffer != null && indexOffset >= 0 && _fileCharIterator.GetRealBufferSize() > indexOffset) {
            return _currentBuffer[indexOffset];
        } else
            return '\u0000';
    }

    private int calculateIndexOffset(long index) {
        return (int) (index - (_currentEnd - _fileCharIterator.GetRealBufferSize()));
    }

    private void iterateUntilRequestPositionInBuffer(long index) {
        while (index >= _currentEnd) {
            if (!_fileCharIterator.hasNext()) break;
            _currentBuffer = _fileCharIterator.next();
            _currentEnd = _currentEnd + _fileCharIterator.GetRealBufferSize();
        }
    }

    private void resetIteratorIfJumpRequired(long index) {
        if (index < _currentEnd - _fileCharIterator.GetRealBufferSize()) {
            initIterator();
        }
    }

    public long size() {
        return 0;
    }
}
