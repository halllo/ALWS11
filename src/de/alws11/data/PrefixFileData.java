package de.alws11.data;

import de.alws11.KnuthMorrisPratt.IIndexStore;
import de.alws11.KnuthMorrisPratt.IReadOnlyIndexStore;
import de.alws11.fileio.FileLineEnumerable;
import de.alws11.fileio.IFileReadAccess;
import de.alws11.fileio.IFileWriteAccess;

import java.io.IOException;

public class PrefixFileData implements IIndexStore {
    private IFileWriteAccess _fileWrite;
    private FileLineEnumerable _fileLineReader;
    private FileLineEnumerable.StringIterator _fileLineIterator;
    private long _currentMetaIndex;

    public PrefixFileData(IFileReadAccess fileRead, IFileWriteAccess fileWrite, int bufferSize) throws Exception {
        _fileWrite = fileWrite;
        _fileLineReader = new FileLineEnumerable(fileRead);
        initIterator();
    }

    private void initIterator() {
        _fileLineReader.reset();
        _fileLineIterator = (FileLineEnumerable.StringIterator) _fileLineReader.iterator();
        _currentMetaIndex = 0;
    }

    public void requiredSize(long size) {
        //ignore
    }

    public void pushIndex(long index) {
        try {
            _fileWrite.writeNumber(index);
        } catch (IOException ignored) {
        }
    }

    public long getIndex(long metaIndex) {
        //todo return line of metaIndex
        // _currentMetaIndex != metaIndex ?
        return _currentMetaIndex++;
    }

    public void doneCreating() {
        try {
            _fileWrite.close();
        } catch (IOException ignored) {
        }
    }

    public IReadOnlyIndexStore asReadOnly() {
        return null;
    }
}
