package de.alws11;

import java.util.List;

/**
 * This interface abstracts over every type of search algorithm that searches for a pattern in a source.
 */
public interface ISearch {
    /**
     * This method registers a pattern with the search algorithm.
     *
     * @param pattern This pattern is used to search for occurrences.
     * @return The method returns the instance of the same search algorithm object.
     */
    ISearch forPattern(IDataProvider pattern);

    /**
     * This method registers an pattern that uses two buffers with the search algorithm.
     *
     * @param pattern This pattern is used to search for occurrences with two buffers.
     * @return The method returns the instance of the same search algorithm object.
     */
    ISearch forPattern(AsymmetricDataProvider pattern);

    /**
     * This method searches for the registered pattern in the given source.
     *
     * @param source This parameter is the source in which the pattern is searched.
     * @return The method returns a list that contains the starting positions of all occurrences of the pattern in the source.
     */
    List<Long> inSource(IDataProvider source);
}
