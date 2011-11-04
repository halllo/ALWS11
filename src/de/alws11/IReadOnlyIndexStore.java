package de.alws11;

/**
 * This interface abstracts over every type of read only indexable index provider.
 */
public interface IReadOnlyIndexStore {
    /**
     * This method returns the index at a given index in the underlying index data structure.
     *
     * @param metaIndex This parameter defines the position at which an index is requested from.
     * @return The method returns the index at the given index in the underlying data structure.
     */
    long getIndex(long metaIndex);
}
