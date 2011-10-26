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
    private long[] _lastReadIndices;
    private String _lastReadFile;

    public IndexFilesData(IFilesController files, int bufferSize) throws Exception {
        _files = files;
        _bufferedIndices = new long[bufferSize];
        _currentBufferIndex = 0;
        _currentMetaIndex = 0;
        _lastReadIndices = new long[bufferSize];
        _lastReadFile = null;
        if (bufferSize < 1) throw new Exception("buffer too small");
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

    public void doneCreating() {
        //ignored
    }

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

    public void close() {
        //ignored
    }

    public IReadOnlyIndexStore asReadOnly() {
        return this;
    }
}
