package de.alws11;

public interface IIndexStore {
    void SetIndex(long metaIndex, long index);
    long GetIndex(long metaIndex);
    void RequiredSize(long size);
}
