package de.alws11.KnuthMorrisPratt;

public interface IIndexStore {
    void setIndex(long metaIndex, long index);

    long getIndex(long metaIndex);

    void requiredSize(long size);
}
