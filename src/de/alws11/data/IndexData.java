package de.alws11.data;

import de.alws11.IIndexStore;
import de.alws11.IReadOnlyIndexStore;

public class IndexData implements IIndexStore, IReadOnlyIndexStore {
    private long[] _indices;
    private int _currentMetaIndex;

    public IndexData() {
        _currentMetaIndex = 0;
    }

    public void pushIndex(long index) {
        _indices[_currentMetaIndex++] = index;
    }

    public long getIndex(long metaIndex) {
        return _indices[(int) metaIndex];
    }

    public void setRequiredSize(long size) {
        _indices = new long[(int) size];
    }

    public void close() {

    }

    public IReadOnlyIndexStore asReadOnly() {
        return this;
    }

    public void doneCreating() {
        _currentMetaIndex = 0;
    }

    public long[] getRaw() {
        return _indices;
    }
}
