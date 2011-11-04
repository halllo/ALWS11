package de.alws11.data;

import de.alws11.IIndexStore;
import de.alws11.IReadOnlyIndexStore;

/**
 * This class provides an in memory data structure of indices.
 */
public class IndexData implements IIndexStore, IReadOnlyIndexStore {
    private long[] _indices;
    private int _currentMetaIndex;

    /**
     * This constructor initializes the data structure.
     */
    public IndexData() {
        _currentMetaIndex = 0;
    }

    /**
     * This method stores an index at a running position in an array. The array is integer accessible and therefore limited.
     *
     * @param index This parameter is the index that is stored.
     */
    public void pushIndex(long index) {
        _indices[_currentMetaIndex++] = index;
    }

    /**
     * This method returns an index at the given position.
     *
     * @param metaIndex This parameter defines the position at which an index is requested from.
     * @return The method returns the index at the metaIndex position which is treated as an integer.
     */
    public long getIndex(long metaIndex) {
        return _indices[(int) metaIndex];
    }

    /**
     * This method initializes the array in which indices are stored.
     *
     * @param size This parameter defines the amount of indices that are going to be stored.
     */
    public void setRequiredSize(long size) {
        _indices = new long[(int) size];
    }

    /**
     * This method does not induce any action since there is nothing that needs to be closed.
     */
    public void close() {

    }

    /**
     * This method returns the data structure as a read only type.
     *
     * @return The same instance is returned as read only.
     */
    public IReadOnlyIndexStore asReadOnly() {
        return this;
    }

    /**
     * This method resets the current position for new indices.
     */
    public void doneCreating() {
        _currentMetaIndex = 0;
    }

    /**
     * This method returns the array in which all indices are stored.
     *
     * @return The array in which all indices are stored.
     */
    public long[] getRaw() {
        return _indices;
    }
}
