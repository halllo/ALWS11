package de.alws11.data;

import de.alws11.IIndexStore;
import de.alws11.IReadOnlyIndexStore;
import de.alws11.fileio.FileLineEnumerable;
import de.alws11.fileio.IFileReadAccess;
import de.alws11.fileio.IFileWriteAccess;
import de.alws11.fileio.IFilesController;

import java.io.IOException;
import java.util.Iterator;

public class IndexFilesData implements IIndexStore, IReadOnlyIndexStore {
    private IFilesController _files;
    private long[] _bufferedIndices;
    private int _currentBufferIndex;
    private long _currentMetaIndex;

    public IndexFilesData(IFilesController files, int bufferSize) throws Exception {
        _files = files;
        _bufferedIndices = new long[bufferSize];
        _currentBufferIndex = 0;
        _currentMetaIndex = 0;
        if (bufferSize < 1)
            throw new Exception("buffer too small");
    }

    public void setRequiredSize(long size) {
        //ignored
    }

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
        String newFile = getNewFileName(getStartMetaIndexOfBuffer());
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

    private String getNewFileName(long metaIndex) {
        return metaIndex + ".indices";
    }

    private long getStartMetaIndexOfBuffer() {
        return _currentMetaIndex - _bufferedIndices.length;
    }

    public void doneCreating() {
        //ignored
    }

    public long getIndex(long metaIndex) {
        if (metaIndex >= _currentMetaIndex || metaIndex < 0) return -1;
        if (metaIndex >= getStartMetaIndexOfBuffer())
            return _bufferedIndices[(int) getOffsetIndex(metaIndex)];
        else
            return readIndexFromFile(metaIndex);
    }

    private long readIndexFromFile(long metaIndex) {
        long result = -1;
        try {
            result = getOffsetIndexFromFile(metaIndex, getReaderForMetaIndex(metaIndex));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private IFileReadAccess getReaderForMetaIndex(long metaIndex) throws IOException {
        String fileWithRequestedIndex = getNewFileName(metaIndex / _bufferedIndices.length);
        return _files.getReaderForFile(fileWithRequestedIndex);
    }

    private long getOffsetIndexFromFile(long metaIndex, IFileReadAccess reader) throws Exception {
        FileLineEnumerable lines = new FileLineEnumerable(reader);
        long requestedIndex = getOffsetIndexFromEnumerable(metaIndex, lines);
        lines.close();
        return requestedIndex;
    }

    private long getOffsetIndexFromEnumerable(long metaIndex, FileLineEnumerable lines) {
        Iterator<String> lineIterator = lines.iterator();
        return getOffsetIndexFromIterator(metaIndex, lineIterator);
    }

    private long getOffsetIndexFromIterator(long metaIndex, Iterator<String> lineIterator) {
        long offsetIndex = getOffsetIndex(metaIndex);
        long i = 0;
        for (; i <= offsetIndex; i++) lineIterator.hasNext();
        return Long.parseLong(lineIterator.next());
    }

    private long getOffsetIndex(long metaIndex) {
        return metaIndex % _bufferedIndices.length;
    }

    public void close() {
        //ignored
    }

    public IReadOnlyIndexStore asReadOnly() {
        return this;
    }
}
