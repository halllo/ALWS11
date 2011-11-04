package de.alws11.data;

import de.alws11.IIndexStore;
import de.alws11.IReadOnlyIndexStore;
import de.alws11.fileio.FileLineEnumerable;
import de.alws11.fileio.IFileReadAccess;
import de.alws11.fileio.IFileWriteAccess;

import java.io.IOException;

/**
 * This class provides a file based storage structure for indices.
 */
public class IndexFileData implements IIndexStore, IReadOnlyIndexStore {
    private IFileWriteAccess _fileWrite;
    private FileLineEnumerable _fileLineReader;
    private FileLineEnumerable.StringIterator _fileLineIterator;
    private long _currentMetaIndex;

    /**
     * This constructor initializes the necessary buffers for read and write access to file io.
     *
     * @param fileWrite This parameter provides the file access for writing indices.
     * @param fileRead  This parameter provides the file access for reading indices.
     * @throws Exception The constructor might fail due to file io errors.
     */
    public IndexFileData(IFileWriteAccess fileWrite, IFileReadAccess fileRead) throws Exception {
        _fileWrite = fileWrite;
        _fileLineReader = new FileLineEnumerable(fileRead);
        initIterator();
    }

    private void initIterator() {
        _fileLineReader.reset();
        _fileLineIterator = (FileLineEnumerable.StringIterator) _fileLineReader.iterator();
        _currentMetaIndex = 0;
    }

    /**
     * This method does not do anything since writing indices figures out the required size automatically.
     *
     * @param size This parameter is ignored.
     */
    public void setRequiredSize(long size) {
        //ignored
    }

    /**
     * This method writes a given index as a string to the file write buffer.
     *
     * @param index This parameter is the index that is stored.
     */
    public void pushIndex(long index) {
        try {
            _fileWrite.writeLine(String.valueOf(index));
        } catch (IOException ignored) {
        }
    }

    /**
     * This method provides the index which was stored at a given position by reading the corresponding line from the underlying buffer.
     *
     * @param metaIndex This parameter defines the line at which an index is requested from.
     * @return The method returns the index at the requested meta index.
     */
    public long getIndex(long metaIndex) {
        prepareWriterForRead();
        resetIteratorIfJumpRequired(metaIndex);
        boolean hasIndex = iterateToRequestedIndex(metaIndex);
        return getCurrentIndexFromIterator(metaIndex, hasIndex);
    }

    /**
     * This method causes the underlying write buffer to prepare for reading.
     */
    private void prepareWriterForRead() {
        try {
            _fileWrite.prepareForRead();
        } catch (IOException ignored) {
        }
    }

    /**
     * This method closes the underlying write and read buffers.
     */
    public void close() {
        _fileLineReader.close();
        try {
            _fileWrite.close();
        } catch (IOException ignored) {
        }
    }

    private void resetIteratorIfJumpRequired(long metaIndex) {
        if (metaIndex < _currentMetaIndex) {
            initIterator();
        }
    }

    private boolean iterateToRequestedIndex(long metaIndex) {
        boolean hasMore = true;
        while (hasMore && metaIndex >= _currentMetaIndex) {
            hasMore = _fileLineIterator.hasNext();
            _currentMetaIndex++;
        }
        return hasMore;
    }

    private long getCurrentIndexFromIterator(long metaIndex, boolean hasMore) {
        if (hasMore && metaIndex >= 0) {
            return Long.parseLong(_fileLineIterator.next());
        } else return -1;
    }

    /**
     * This method notifies the underlying write buffer that writing is done.
     */
    public void doneCreating() {
        try {
            _fileWrite.close();
        } catch (IOException ignored) {
        }
    }

    /**
     * This method returns an object of a type that only supports readonly operations.
     *
     * @return The method returns the same instance but as a readonly type.
     */
    public IReadOnlyIndexStore asReadOnly() {
        return this;
    }
}
