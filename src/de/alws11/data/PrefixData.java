package de.alws11.data;

import de.alws11.KnuthMorrisPratt.IIndexStore;

public class PrefixData implements IIndexStore {
    private long[] _indices;

    public PrefixData() {

    }

    public void setIndex(long metaIndex, long index) {
        _indices[(int) metaIndex] = index;
    }

    public long getIndex(long metaIndex) {
        return _indices[(int) metaIndex];
    }

    public void requiredSize(long size) {
        _indices = new long[(int) size];
    }

    public long[] getRaw() {
        return _indices;
    }
}
