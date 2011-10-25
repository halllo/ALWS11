package de.alws11.data;

import de.alws11.IIndexStore;
import de.alws11.IReadOnlyIndexStore;
import de.alws11.fileio.FileLineEnumerable;
import de.alws11.fileio.IFileReadAccess;
import de.alws11.fileio.IFileWriteAccess;

import java.io.IOException;

public class IndexFileData implements IIndexStore, IReadOnlyIndexStore {
    private IFileWriteAccess _fileWrite;
    private FileLineEnumerable _fileLineReader;
    private FileLineEnumerable.StringIterator _fileLineIterator;
    private long _currentMetaIndex;

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

    public void setRequiredSize(long size) {
        //ignored
    }

    public void pushIndex(long index) {
        try {
            _fileWrite.writeLine(String.valueOf(index));
        } catch (IOException ignored) {
        }
    }

    public long getIndex(long metaIndex) {
        prepareWriterForRead();
        resetIteratorIfJumpRequired(metaIndex);
        boolean hasIndex = iterateToRequestedIndex(metaIndex);
        return getCurrentIndexFromIterator(metaIndex, hasIndex);
    }

    private void prepareWriterForRead() {
        try {
            _fileWrite.prepareForRead();
        } catch (IOException ignored) {
        }
    }

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

    public void doneCreating() {
        try {
            _fileWrite.close();
        } catch (IOException ignored) {
        }
    }

    public IReadOnlyIndexStore asReadOnly() {
        return this;
    }
}
