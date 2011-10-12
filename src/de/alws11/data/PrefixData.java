package de.alws11.data;

import de.alws11.IPrefixStore;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Manuel
 * Date: 12.10.11
 * Time: 10:55
 * To change this template use File | Settings | File Templates.
 */
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
