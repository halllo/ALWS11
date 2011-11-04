package de.alws11;

/**
 * This interface abstracts over every type of indexable indices storage.
 */
public interface IIndexStore {
    /**
     * This method notifies the underlying storage data structure about how big it is going to become.
     *
     * @param size This parameter defines the amount of indices that are going to be stored.
     */
    void setRequiredSize(long size);

    /**
     * This method stores an index in the underlying storage.
     *
     * @param index This parameter is the index that is stored.
     */
    void pushIndex(long index);

    /**
     * This method notifies the underlying data structure that it is done storing.
     */
    void doneCreating();

    /**
     * This method returns the index at a given index in the underlying index data structure.
     *
     * @param metaIndex This parameter defines the position at which an index is requested from.
     * @return The method returns the index at the given index in the underlying data structure.
     */
    long getIndex(long metaIndex);

    /**
     * This method notifies the underlying data structure that is is done storing.
     */
    void close();

    /**
     * This method provides the underlying storage data structure in a read only fashion.
     *
     * @return The method returns the data structure that can not be used to store indices anymore.
     */
    IReadOnlyIndexStore asReadOnly();
}