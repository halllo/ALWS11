package de.alws11.fileio;

import java.util.Iterator;

/**
 * This class provides functionality of reading junks of characters of a file in a lazy sequence.
 */
public class FileCharArrayEnumerable implements Iterable<char[]> {
    IFileReadAccess _reader;
    int _bufferSize;

    /**
     * This method provides the size of a junk of characters that can be read.
     *
     * @return The method returns the amount of characters in a junk.
     */
    public int GetBufferSize() {
        return _bufferSize;
    }

    /**
     * This field indicates the number of reading operations that were executed on the file.
     */
    public long IoReads = 0;

    /**
     * This constructor initializes the sequence.
     *
     * @param reader     This parameter is the file that is used to read lines from.
     * @param bufferSize This parameter is the amount of characters that can be read during a single file access.
     * @throws Exception An error occurs, if the buffer size is smaller than 1.
     */
    public FileCharArrayEnumerable(IFileReadAccess reader, int bufferSize)
            throws Exception {
        _reader = reader;
        _bufferSize = bufferSize;
        if (_bufferSize < 1)
            throw new Exception("buffer too small");
    }

    /**
     * This method closes the open file.
     */
    public void close() {
        try {
            _reader.close();
        } catch (Exception ignored) {
        }
    }

    /**
     * This method resets the reading window of the reader.
     */
    public void reset() {
        try {
            _reader.reset();
        } catch (Exception ignored) {
        }
    }

    /**
     * This method provides the lazy sequence of junks of characters in the file.
     *
     * @return The method returns an iterator that can iterate over junks of characters in the file.
     */
    public Iterator<char[]> iterator() {
        return new CharArrayIterator(_bufferSize);
    }

    /**
     * This class is used to retrieve junks of characters from the sequence of junks of characters in a file.
     */
    public class CharArrayIterator implements Iterator<char[]> {
        char[] _buffer;
        int _lastRead;

        /**
         * This method provides the size of the last read junk of characters.
         *
         * @return The method returns the amount of characters in the last junk.
         */
        public int GetRealBufferSize() {
            return _lastRead;
        }

        /**
         * This constructor initializes the buffer for the junks of characters.
         *
         * @param bufferSize This parameter is the amount of characters in a single junk of characters.
         */
        public CharArrayIterator(int bufferSize) {
            _buffer = new char[bufferSize];
            _lastRead = bufferSize;
        }

        /**
         * This method moves the sequence to the next junk of characters in the file.
         *
         * @return The method returns TRUE if there are more junks of characters in the file and FALSE, if there are no more junks of characters.
         */
        public boolean hasNext() {
            try {
                if (_lastRead == _buffer.length) {
                    _lastRead = _reader.read(_buffer, 0, _buffer.length);
                    if (_lastRead == 0)
                        _buffer = null;
                } else
                    _buffer = null;
                IoReads++;
            } catch (Exception ex) {
                _buffer = null;
                ex.printStackTrace();
            }

            return _buffer != null;
        }

        /**
         * This method provides the current element of the sequence of junks of characters of the file.
         *
         * @return The method returns the current junk of characters of the file.
         */
        public char[] next() {
            return _buffer;
        }

        /**
         * This method does not do anything.
         */
        public void remove() {
        }
    }
}
