package de.alws11.fileio;

import java.util.Iterator;

/**
 * This class provides functionality of reading chars of a file in a lazy sequence. The chars are still buffered and read in junks in order to cascade file access.
 */
public class FileCharEnumerable implements Iterable<Character> {
    FileCharArrayEnumerable _charArrays;

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
    public FileCharEnumerable(IFileReadAccess reader, int bufferSize) throws Exception {
        _charArrays = new FileCharArrayEnumerable(reader, bufferSize);
    }

    /**
     * This method closes the open file.
     */
    public void close() {
        try {
            _charArrays.close();
        } catch (Exception ignored) {
        }
    }

    /**
     * This method provides the lazy sequence of characters in the file.
     *
     * @return The method returns an iterator that can iterate over characters in the file.
     */
    public Iterator<Character> iterator() {
        return new CharIterator();
    }

    /**
     * This class is used to retrieve characters from the sequence of characters in a file.
     */
    public class CharIterator implements Iterator<Character> {
        long _gindex;
        FileCharArrayEnumerable.CharArrayIterator _charArrayIter;

        /**
         * This constructor initializes the the sequence before anything has been requested.
         */
        public CharIterator() {
            _gindex = -1;
            _charArrayIter = (FileCharArrayEnumerable.CharArrayIterator) _charArrays
                    .iterator();
        }

        /**
         * This method moves the sequence to the next character in the sequence of junks of characters of the file.
         *
         * @return The method returns TRUE if there are more characters in the sequence and FALSE, if there are no more characters.
         */
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

        /**
         * This method provides the current element of the sequence of characters.
         *
         * @return The method returns the current character in the sequence of junks of characters in the file.
         */
        public Character next() {
            int bufferIndex = (int) (_gindex % _charArrays.GetBufferSize());
            return _charArrayIter.next()[bufferIndex];
        }

        /**
         * This method does not do anything.
         */
        public void remove() {
        }
    }
}

