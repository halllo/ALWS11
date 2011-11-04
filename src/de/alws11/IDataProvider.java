package de.alws11;

/**
 * This interface abstracts over every type of indexable data structure.
 */
public interface IDataProvider {
    /**
     * This method provides a character at a requested index.
     *
     * @param index The zero based position from where the character is requested.
     * @return The character at the requested position is returned.
     */
    char getPosition(long index);

    /**
     * This method provides the size of the data structure.
     *
     * @return The amount of characters in the underlying data structure.
     */
    long size();

    /**
     * This method notifies the underlying data structure that it is not used anymore.
     */
    void close();
}
