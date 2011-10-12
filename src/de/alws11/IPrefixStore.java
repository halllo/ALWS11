package de.alws11;

public interface IPrefixStore {
    void SetPosition(long index, long value);
    long GetPosition(long index);
    void RequestSize(long size);
}
