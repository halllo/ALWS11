package de.alws11;

public interface IIndexStore {
    void setRequiredSize(long size);

    void pushIndex(long index);

    void doneCreating();

    long getIndex(long metaIndex);

    void close();

    IReadOnlyIndexStore asReadOnly();
}