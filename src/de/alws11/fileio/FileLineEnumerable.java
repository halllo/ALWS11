package de.alws11.fileio;

import java.util.Iterator;

/**
 * This class provides functionality of reading lines of a file in a lazy sequence.
 */
public class FileLineEnumerable implements Iterable<String> {
    IFileReadAccess _reader;

    /**
     * This field indicates the number of reading operations that were executed on the file.
     */
    public long IoReads = 0;

    /**
     * This constructor initializes the sequence.
     *
     * @param reader This parameter is the file that is used to read lines from.
     * @throws Exception An error might occur during file access.
     */
    public FileLineEnumerable(IFileReadAccess reader) throws Exception {
        _reader = reader;
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
     * This method provides the lazy sequence of all lines in the file.
     *
     * @return The method returns an iterator that can iterate over all the lines in the file.
     */
    public Iterator<String> iterator() {
        return new StringIterator();
    }

    /**
     * This class is used to retrieve lines from the sequence of lines in a file.
     */
    public class StringIterator implements Iterator<String> {
        String _currentLine;

        /**
         * This constructor does nothing.
         */
        public StringIterator() {
        }

        /**
         * This method moves the sequence to the next line in the file.
         *
         * @return The method returns TRUE if there are more lines in the file and FALSE, if there are no more lines.
         */
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

        /**
         * This method provides the current element of the sequence of lines of the file.
         *
         * @return The method returns the current line of the file.
         */
        public String next() {
            return _currentLine;
        }

        /**
         * This method does not do anything.
         */
        public void remove() {
        }
    }
}
