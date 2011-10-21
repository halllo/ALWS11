package de.alws11.data;

import de.alws11.KnuthMorrisPratt.IIndexStore;
import de.alws11.KnuthMorrisPratt.IReadOnlyIndexStore;

public class PrefixData implements IIndexStore, IReadOnlyIndexStore {
    private long[] _indices;
    private int _currentMetaIndex;

    public PrefixData() {
        _currentMetaIndex = 0;
    }

    public void pushIndex(long index) {
        _indices[_currentMetaIndex++] = index;
    }

    public long getIndex(long metaIndex) {
        return _indices[(int) metaIndex];
    }

    public void requiredSize(long size) {
        _indices = new long[(int) size];
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
