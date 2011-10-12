package de.alws11.data;

import de.alws11.IPrefixStore;

public class PrefixData implements IPrefixStore {
    private long[] _longs;

    public PrefixData() {

   }

    public void SetPosition(long index, long value) {
        _longs[(int) index] = value;
    }

    public long GetPosition(long index) {
        return _longs[(int)index];
    }

    public void RequestSize(long size) {
        _longs = new long[(int)size];
    }

    public long[] GetRaw() {
        return _longs;
    }
}
