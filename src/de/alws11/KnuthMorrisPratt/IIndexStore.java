package de.alws11.KnuthMorrisPratt;

public interface IIndexStore {
    void requiredSize(long size);

    void pushIndex(long index);

    void doneCreating();

    long getIndex(long metaIndex);

    void close();

    IReadOnlyIndexStore asReadOnly();
}