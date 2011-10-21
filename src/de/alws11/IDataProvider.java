package de.alws11;

public interface IDataProvider {
    char getPosition(long index);

    long size();

    void close();
}
