package de.alws11;

/**
 * Created by IntelliJ IDEA.
 * User: Manuel
 * Date: 12.10.11
 * Time: 09:55
 * To change this template use File | Settings | File Templates.
 */
public interface IDataProvider {
    String GetPosition(long index);
    long Size();
}