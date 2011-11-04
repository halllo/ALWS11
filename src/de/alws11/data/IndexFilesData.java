package de.alws11.data;

import de.alws11.IIndexStore;
import de.alws11.IReadOnlyIndexStore;
import de.alws11.fileio.FileLineEnumerable;
import de.alws11.fileio.IFileReadAccess;
import de.alws11.fileio.IFileWriteAccess;
import de.alws11.fileio.IFilesController;

import java.io.IOException;
import java.util.Iterator;

/**
 * This class supports an index storing data structure that uses multiple files.
 */
public class IndexFilesData implements IIndexStore, IReadOnlyIndexStore {
    private IFilesController _files;
    private long[] _bufferedIndices;
    private int _currentBufferIndex;
    private long _currentMetaIndex;
    private long[] _lastReadIndices;
    private String _lastReadFile;

    /**
     * This constructor initializes the necessary read and write buffers.
     *
     * @param files      This parameter is the file factory which allows to create, read and write files.
     * @param bufferSize This parameter indicates how many indices can be stored in the buffers and in a single file. The name of the file is the global meta index of its first index.
     * @throws Exception An error might occur if the buffer size is set to a size smaller 1.
     */
    public IndexFilesData(IFilesController files, int bufferSize) throws Exception {
        _files = files;
        _bufferedIndices = new long[bufferSize];
        _currentBufferIndex = 0;
        _currentMetaIndex = 0;
        _lastReadIndices = new long[bufferSize];
        _lastReadFile = null;
        if (bufferSize < 1) throw new Exception("buffer too small");
    }

    /**
     * This method does not do anything since the required size can be figured out durring adding indices.
     *
     * @param size This parameter is ignored.
     */
    public void setRequiredSize(long size) {
        //ignored
    }

    /**
     * This method stores an index in the underlying buffers and might create a storage file, if the buffer is full.
     *
     * @param index This parameter is the index that is stored.
     */
    public void pushIndex(long index) {
        writeBufferToFileIfFull();
        _bufferedIndices[_currentBufferIndex++] = index;
        _currentMetaIndex++;
    }

    private void writeBufferToFileIfFull() {
        if (_currentBufferIndex == _bufferedIndices.length) writeBufferToFile();
    }

    private void writeBufferToFile() {
        _currentBufferIndex = 0;
        String newFile = getFileName(getStartMetaIndexOfBuffer());
        writeBufferToFile(newFile);
    }

    private void writeBufferToFile(String newFile) {
        try {
            _files.createFile(newFile);
            IFileWriteAccess writer = _files.getWriterForFile(newFile);
            for (long index : _bufferedIndices) writer.writeLine(String.valueOf(index));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getFileName(long metaIndex) {
        return metaIndex + ".indices";
    }

    private long getStartMetaIndexOfBuffer() {
        return _currentMetaIndex - _bufferedIndices.length;
    }

    /**
     * This method does not do anything since the written files were already closed and the indices that are currently in the buffer do not need to be stored in a file.
     */
    public void doneCreating() {
        //ignored
    }

    /**
     * This method returns the index at a given position by looking in the read buffer. If it is not in the read buffer, the corresponding file is loaded to the read buffer and in there a line offset number is used to lookup the requested index in that file.
     *
     * @param metaIndex This parameter defines the position at which an index is requested from.
     * @return The method returns the index at the given position if it could be found, -1 in any other case.
     */
    public long getIndex(long metaIndex) {
        if (metaIndex >= _currentMetaIndex || metaIndex < 0) return -1;
        if (metaIndex >= getStartMetaIndexOfBuffer())
            return _bufferedIndices[getOffsetIndex(metaIndex)];
        else
            return readIndexFromFile(metaIndex);
    }

    private long readIndexFromFile(long metaIndex) {
        long result = -1;
        try {
            result = getIndexForMetaIndexFromFiles(metaIndex);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private long getIndexForMetaIndexFromFiles(long metaIndex) throws Exception {
        String fileWithRequestedIndex = getFileNameForMetaIndex(metaIndex);
        if (fileWithRequestedIndex.equals(_lastReadFile)) {
            return getIndexForMetaIndexFromLastRead(metaIndex);
        } else {
            _lastReadFile = fileWithRequestedIndex;
            return getIndexForMetaIndexFromFile(metaIndex, getReaderForFile(fileWithRequestedIndex));
        }
    }

    private String getFileNameForMetaIndex(long metaIndex) {
        return getFileName((metaIndex / _bufferedIndices.length) * _bufferedIndices.length);
    }

    private IFileReadAccess getReaderForFile(String fileWithRequestedIndex) throws IOException {
        return _files.getReaderForFile(fileWithRequestedIndex);
    }

    private long getIndexForMetaIndexFromLastRead(long metaIndex) {
        return _lastReadIndices[getOffsetIndex(metaIndex)];
    }

    private long getIndexForMetaIndexFromFile(long metaIndex, IFileReadAccess reader) throws Exception {
        FileLineEnumerable lines = new FileLineEnumerable(reader);
        long requestedIndex = getIndexForMetaIndexFromEnumerable(metaIndex, lines);
        lines.close();
        return requestedIndex;
    }

    private long getIndexForMetaIndexFromEnumerable(long metaIndex, FileLineEnumerable lines) {
        Iterator<String> lineIterator = lines.iterator();
        readIteratorIntoLastReadIndices(lineIterator);
        return _lastReadIndices[getOffsetIndex(metaIndex)];
    }

    private void readIteratorIntoLastReadIndices(Iterator<String> lineIterator) {
        for (int i = 0; i < _lastReadIndices.length; i++) {
            lineIterator.hasNext();
            String line = lineIterator.next();
            _lastReadIndices[i] = line != null ? Long.parseLong(line) : -1;
        }
    }

    private int getOffsetIndex(long metaIndex) {
        return (int) metaIndex % _bufferedIndices.length;
    }

    /**
     * This method does not do anything, since all files were already closed after writing and reading.
     */
    public void close() {
        //ignored
    }

    /**
     * This method returns the storing data structure in a readonly way.
     *
     * @return The method returns the same instance but of a type that allows only readonly operations..
     */
    public IReadOnlyIndexStore asReadOnly() {
        return this;
    }
}
