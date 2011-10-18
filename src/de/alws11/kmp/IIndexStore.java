package de.alws11.kmp;

public interface IIndexStore {
    void setIndex(long metaIndex, long index);

    long getIndex(long metaIndex);

    void requiredSize(long size);
}
