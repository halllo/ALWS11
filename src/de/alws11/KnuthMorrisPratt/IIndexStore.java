package de.alws11.KnuthMorrisPratt;

public interface IIndexStore {
    void pushIndex(long index);

    long getIndex(long metaIndex);

    void requiredSize(long size);

    IReadOnlyIndexStore asReadOnly();
}
