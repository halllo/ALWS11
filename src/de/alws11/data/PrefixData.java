package de.alws11.data;

import de.alws11.IIndexStore;

public class PrefixData implements IIndexStore {
    private long[] _indices;

    public PrefixData() {

   }

    public void SetIndex(long metaIndex, long index) {
        _indices[(int) metaIndex] = index;
    }

    public long GetIndex(long metaIndex) {
        return _indices[(int)metaIndex];
    }

    public void RequiredSize(long size) {
        _indices = new long[(int)size];
    }

    public long[] GetRaw() {
        return _indices;
    }
}
