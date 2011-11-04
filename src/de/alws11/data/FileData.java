package de.alws11.data;

import de.alws11.IDataProvider;
import de.alws11.fileio.FileCharArrayEnumerable;
import de.alws11.fileio.IFileReadAccess;

/**
 * This class provides basic file data in an indexable way.
 */
public class FileData implements IDataProvider {
    private char[] _currentBuffer;
    private FileCharArrayEnumerable _fileCharReader;
    private FileCharArrayEnumerable.CharArrayIterator _fileCharIterator;
    private IFileReadAccess _fileAccess;
    private long _currentEnd;

    /**
     * This constructor initializes the necessary buffers in order for file access.
     *
     * @param fileAccess The abstract file from which data is read and stored in a buffer.
     * @param bufferSize This parameter defines how many characters are going to fit into the buffer.
     * @throws Exception File accessing errors might occur.
     */
    public FileData(IFileReadAccess fileAccess, int bufferSize) throws Exception {
        _fileAccess = fileAccess;
        _fileCharReader = new FileCharArrayEnumerable(_fileAccess, bufferSize);
        initIterator();
    }

    private void initIterator() {
        _fileCharReader.reset();
        _fileCharIterator = (FileCharArrayEnumerable.CharArrayIterator) _fileCharReader.iterator();
        _currentEnd = 0;
    }

    /**
     * This method looks up whether the requested character is already in the buffer and resets it when now.
     *
     * @param index The zero based position from where the character is requested.
     * @return The method returns the character at the position in the file. If there is no such index, '\u0000' is returned.
     */
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

    /**
     * This method returns the size of the abstract file.
     *
     * @return The method returns the amount of characters in the file.
     */
    public long size() {
        return _fileAccess.getFileSize();
    }

    /**
     * This method closes the buffer and the file.
     */
    public void close() {
        _fileCharReader.close();
    }
}
