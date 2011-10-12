package de.alws11;

/**
 * Created by IntelliJ IDEA.
 * User: Manuel
 * Date: 12.10.11
 * Time: 10:47
 * To change this template use File | Settings | File Templates.
 */
public interface IPrefixStore {
    void SetPosition(long index, long value);
    long GetPosition(long index);
    void RequestSize(long size);
}
